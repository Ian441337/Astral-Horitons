package ovh.glitchlabs.astral_horizons.datagen;

import ovh.glitchlabs.astral_horizons.astral_horizons;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import ovh.glitchlabs.astral_horizons.items.ModItems;

public class ModItemProvidor extends ItemModelProvider {
    public ModItemProvidor(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, astral_horizons.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.STEEL_INGOT.get());
        basicItem(ModItems.TIN_INGOT.get());
        basicItem(ModItems.CANNED_CARROT.get());
        basicItem(ModItems.CANNED_MELON.get());
        basicItem(ModItems.CANNED_STEAK.get());
        basicItem(ModItems.EMPTY_CAN.get());
        basicItem(ModItems.ELECTROMAGNET.get());
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.RAW_TITANIUM.get());
        basicItem(ModItems.OXYGEN_FILTER.get());
    }
}
