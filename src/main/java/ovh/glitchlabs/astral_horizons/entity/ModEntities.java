package ovh.glitchlabs.astral_horizons.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import ovh.glitchlabs.astral_horizons.astral_horizons;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, astral_horizons.MODID);

    public static final Supplier<EntityType<RocketEntity>> ROCKET =
            ENTITY_TYPES.register("rocket", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC)
                    .passengerAttachments(1.44375F)
                    .sized(1.0f, 2.0f)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("rocket")
            );





    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
