package ovh.glitchlabs.astral_horizons.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import ovh.glitchlabs.astral_horizons.astral_horizons;
import ovh.glitchlabs.astral_horizons.blocks.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, astral_horizons.MODID);

    public static final Supplier<CreativeModeTab> ASTRAL_HORIZONS_TAB = CREATIVE_MODE_TAB.register("astral_horizons_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CANNED_STEAK.get()))
                    .title(Component.translatable("creativetab.astral_horizons.tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.EMPTY_CAN);
                        output.accept(ModItems.CANNED_MELON);
                        output.accept(ModItems.CANNED_CARROT);
                        output.accept(ModItems.CANNED_STEAK);
                        output.accept(ModItems.STEEL_INGOT);
                        output.accept(ModBlocks.STEEL_BLOCK);
                        output.accept(ModItems.STEEL_CHISEL);
                        output.accept(ModBlocks.TIN_ORE);
                        output.accept(ModBlocks.DEEPSLATE_TIN_ORE);
                        output.accept(ModBlocks.TITANIUM_ORE);
                        output.accept(ModBlocks.DEEPSLATE_TITANIUM_ORE);
                        output.accept(ModItems.TIN_INGOT);
                        output.accept(ModItems.TITANIUM_INGOT);
                        output.accept(ModItems.RAW_TITANIUM);
                        output.accept(ModItems.ELECTROMAGNET);
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
