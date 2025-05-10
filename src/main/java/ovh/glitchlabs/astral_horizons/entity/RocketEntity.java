package ovh.glitchlabs.astral_horizons.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RocketEntity extends Entity {

    private boolean launched = false;

    public RocketEntity(EntityType<? extends RocketEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public void tick() {
        super.tick();

        if (launched) {
            setDeltaMovement(0, 0.5, 0);
            move(MoverType.SELF, getDeltaMovement());

            if (!getPassengers().isEmpty()) {
                getPassengers().get(0).setPos(position());
            }

            if (!level().isClientSide && getY() >= 300) {
                teleportTo(100, 100, 100);
            }
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        System.out.println("RocketEntity: Player interacted!");
        if (!level().isClientSide) {
            player.startRiding(this);
        }
        return InteractionResult.SUCCESS;
    }



    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!launched) {
            launched = true;
            return true;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        Vec3 pos = this.position().add(0, 1.5, 0);
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
}

