package ovh.glitchlabs.astral_horizons.client.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import ovh.glitchlabs.astral_horizons.astral_horizons;
import ovh.glitchlabs.astral_horizons.entity.RocketEntity;

public class RocketRenderer extends EntityRenderer<RocketEntity> {
    @Override
    public ResourceLocation getTextureLocation(RocketEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(astral_horizons.MODID, "textures/entity/rocket.png");
    }

    public RocketRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(RocketEntity entity, float entityYaw, float partialTicks,
                       com.mojang.blaze3d.vertex.PoseStack matrixStack,
                       net.minecraft.client.renderer.MultiBufferSource buffer,
                       int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
}