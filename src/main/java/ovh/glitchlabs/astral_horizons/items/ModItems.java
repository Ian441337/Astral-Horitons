package ovh.glitchlabs.astral_horizons.items;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ovh.glitchlabs.astral_horizons.astral_horizons;
import ovh.glitchlabs.astral_horizons.items.custom.ChiselItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(astral_horizons.MODID);

    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ELECTROMAGNET = ITEMS.register("electromagnet",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OXYGEN_FILTER = ITEMS.register("oxygen_filter",
            () -> new Item(new Item.Properties()));


    public static final DeferredItem<Item> EMPTY_CAN = ITEMS.register("empty_can",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> STEEL_CHISEL = ITEMS.register("steel_chisel",
            () -> new ChiselItem(new Item.Properties().durability(32)));



//Food

    public static final DeferredItem<Item> CANNED_MELON = ITEMS.register("canned_melon",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CANNED_MELON)));

    public static final DeferredItem<Item> CANNED_CARROT = ITEMS.register("canned_carrot",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CANNED_CARROT)));

    public static final DeferredItem<Item> CANNED_STEAK = ITEMS.register("canned_steak",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CANNED_STEAK)));

//

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
