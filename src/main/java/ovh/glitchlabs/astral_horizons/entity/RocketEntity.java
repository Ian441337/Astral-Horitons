package ovh.glitchlabs.astral_horizons.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import ovh.glitchlabs.astral_horizons.astral_horizons;

public class RocketEntity extends Entity {

    private static final EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> LAUNCH_TICKS = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);

    private static final int LAUNCH_PREPARATION_TIME = 60;
    private static final double MAX_SPEED = 3.5;
    private static final int TELEPORT_HEIGHT = 500;
    private static final double FUEL_CONSUMPTION_RATE = 0.01;

    // Rocket state variables
    private double fuel = 100.0; // Full tank of fuel
    private boolean engineStarted = false;

    public RocketEntity(EntityType<? extends RocketEntity> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true; // Prevents blocks from being placed on the rocket
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(LAUNCHED, false);
        builder.define(LAUNCH_TICKS, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("Launched")) {
            entityData.set(LAUNCHED, compoundTag.getBoolean("Launched"));
        }
        if (compoundTag.contains("LaunchTicks")) {
            entityData.set(LAUNCH_TICKS, compoundTag.getInt("LaunchTicks"));
        }
        if (compoundTag.contains("Fuel")) {
            this.fuel = compoundTag.getDouble("Fuel");
        }
        if (compoundTag.contains("EngineStarted")) {
            this.engineStarted = compoundTag.getBoolean("EngineStarted");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putBoolean("Launched", entityData.get(LAUNCHED));
        compoundTag.putInt("LaunchTicks", entityData.get(LAUNCH_TICKS));
        compoundTag.putDouble("Fuel", this.fuel);
        compoundTag.putBoolean("EngineStarted", this.engineStarted);
    }

    public boolean isLaunched() {
        return entityData.get(LAUNCHED);
    }

    public int getLaunchTicks() {
        return entityData.get(LAUNCH_TICKS);
    }

    public double getFuelPercentage() {
        return fuel;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            if (engineStarted && !isLaunched()) {
                int currentTicks = getLaunchTicks();
                if (currentTicks < LAUNCH_PREPARATION_TIME) {
                    entityData.set(LAUNCH_TICKS, currentTicks + 1);

                } else {
                    entityData.set(LAUNCHED, true);
                    level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS,
                            1.0F, 0.5F);
                }
            }

            if (isLaunched()) {
                double progress = Math.min(getLaunchTicks() / 200.0, 1.0);
                double speed = MAX_SPEED * progress;

                if (fuel > 0) {
                    fuel -= FUEL_CONSUMPTION_RATE;
                    setDeltaMovement(0, speed, 0);
                    move(MoverType.SELF, getDeltaMovement());

                    for (Entity passenger : getPassengers()) {
                        passenger.setPos(position());
                    }

                    if (random.nextInt(5) == 0) {
                        level().playSound(null, this.getX(), this.getY(), this.getZ(),
                                SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS,
                                0.5F, 0.5F);
                    }

                    // Check for dimension change threshold
                    if (getY() >= TELEPORT_HEIGHT) {
                        teleportToMoon();
                    }
                } else {
                    if (!getPassengers().isEmpty() && getPassengers().get(0) instanceof ServerPlayer player) {
                        player.displayClientMessage(Component.literal("Rocket has run out of fuel!"), true);
                    }
                    setDeltaMovement(getDeltaMovement().multiply(0.9, 0.9, 0.9));
                    move(MoverType.SELF, getDeltaMovement());
                }
            }
        }
    }

    private void teleportToMoon() {
        if (!(level() instanceof ServerLevel serverLevel)) return;

        String dimensionId = astral_horizons.MODID + ":moon";

        ServerLevel targetLevel = null;
        for (ServerLevel level : serverLevel.getServer().getAllLevels()) {
            if (level.dimension().location().toString().equals(dimensionId)) {
                targetLevel = level;
                break;
            }
        }

        if (targetLevel == null) {
            return;
        }

        // Finde eine geeignete Landestelle
        int randomX = random.nextInt(200) - 100; // -100 bis 100
        int randomZ = random.nextInt(200) - 100; // -100 bis 100
        double landingY = 100; // Starthöhe für die Landung

        // Erst die neue Rakete erstellen
        RocketEntity newRocket = ModEntities.ROCKET.get().create(targetLevel);
        if (newRocket != null) {
            // Rakete positionieren
            newRocket.moveTo(randomX, landingY, randomZ);
            newRocket.fuel = this.fuel * 0.75; // Etwas Treibstoff während des Transfers verlieren

            // Rakete zur Welt hinzufügen
            targetLevel.addFreshEntity(newRocket);

            // Spieler teleportieren und direkt in die neue Rakete setzen
            Entity passenger = getFirstPassenger();
            if (passenger instanceof ServerPlayer player) {
                player.stopRiding();

                player.teleportTo(targetLevel, randomX, landingY, randomZ, player.getYRot(), player.getXRot());

                serverLevel.getServer().tell(new net.minecraft.server.TickTask(1, () -> {
                    player.startRiding(newRocket);
                }));
            }
        }

        // Die ursprüngliche Rakete entfernen
        this.discard();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) {
            if (!level().isClientSide && !engineStarted && !isLaunched()) {
                engineStarted = true;
                return InteractionResult.SUCCESS;
            }
        } else if (!isLaunched()) {
            if (!level().isClientSide && !player.isPassenger()) {
                player.startRiding(this);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isPushable() {
        return !isLaunched();
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide && !isLaunched() && source.getEntity() instanceof Player) {
            if (fuel <= 0) {
                level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS,
                        1.0F, 1.0F);
                if (source.getEntity() instanceof ServerPlayer player) {
                    player.displayClientMessage(Component.literal("The rocket has no fuel!"), true);
                }
                return false;
            }
            return super.hurt(source, amount);
        }
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        Vec3 pos = this.position().add(0, 0.5, 0);
        callback.accept(passenger, pos.x, pos.y, pos.z);
    }

    @Override
    public boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty() && passenger instanceof Player;
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    public boolean refuel(double amount) {
        if (!isLaunched() && fuel < 100.0) {
            fuel = Math.min(100.0, fuel + amount);
            return true;
        }
        return false;
    }
}