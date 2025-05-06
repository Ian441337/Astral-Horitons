package ovh.gliitchlabs.items;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ovh.gliitchlabs.astral_horizons;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(astral_horizons.MODID);

    public static final DeferredItem<Item> TEST = ITEMS.register("test",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
