package ovh.glitchlabs.astral_horizons.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import ovh.glitchlabs.astral_horizons.astral_horizons;
import ovh.glitchlabs.astral_horizons.client.renderer.RocketRenderer;
import ovh.glitchlabs.astral_horizons.entity.ModEntities;

@EventBusSubscriber(modid = astral_horizons.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(ModEntities.ROCKET.get(), RocketRenderer::new);
        });
    }
}