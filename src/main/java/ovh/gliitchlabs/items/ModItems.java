package ovh.gliitchlabs.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ovh.gliitchlabs.astral_horizons;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(astral_horizons.MODID);

    public static final DeferredItem<Item> STEAL_INGOT = ITEMS.register("steal_ingot",
            () -> new Item(new Item.Properties()));


    public static final DeferredItem<Item> EMPTY_CAN = ITEMS.register("empty_can",
            () -> new Item(new Item.Properties()));


//Food
    public static final DeferredItem<Item> CANNED_MELON = ITEMS.register("canned_melon",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).usingConvertsTo(ModItems.EMPTY_CAN).build())));

    public static final DeferredItem<Item> CANNED_CARROT = ITEMS.register("canned_carrot",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).usingConvertsTo(ModItems.EMPTY_CAN).build())));

    public static final DeferredItem<Item> CANNED_STEAK = ITEMS.register("canned_steak",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).usingConvertsTo(ModItems.EMPTY_CAN).build())));
//

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
