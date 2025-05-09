package ovh.gliitchlabs.items;

import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties CANNED_MELON = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.6f)
            .usingConvertsTo(ModItems.EMPTY_CAN)
            .build();

    public static final FoodProperties CANNED_STEAK = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.25f)
            .usingConvertsTo(ModItems.EMPTY_CAN)
            .build();

    public static final FoodProperties CANNED_CARROT = new FoodProperties.Builder()
            .nutrition(8)
            .saturationModifier(1.6f)
            .usingConvertsTo(ModItems.EMPTY_CAN)
            .build();
}
