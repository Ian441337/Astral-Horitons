package ovh.glitchlabs.astral_horizons.datagen;

import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import ovh.glitchlabs.astral_horizons.astral_horizons;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import ovh.glitchlabs.astral_horizons.blocks.ModBlocks;

public class ModBlockStateProvidor extends BlockStateProvider {
    public ModBlockStateProvidor(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, astral_horizons.MODID, exFileHelper);
    }


    @Override
    protected void registerStatesAndModels() {
        BlockWithItem(ModBlocks.STEEL_BLOCK);

        BlockWithItem(ModBlocks.TIN_ORE);
        BlockWithItem(ModBlocks.DEEPSLATE_TIN_ORE);
    }

    private void BlockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
