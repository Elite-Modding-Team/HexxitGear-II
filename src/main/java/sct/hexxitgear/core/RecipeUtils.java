package sct.hexxitgear.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import sct.hexxitgear.core.recipe.HexRecipe;

public class RecipeUtils {

    public static void addShapedRecipe(IForgeRegistry<IRecipe> registry, ResourceLocation name, ItemStack output, int width, int height, String[] inputItems, String[] remainingItems) {
        NonNullList<ItemStack> input = NonNullList.withSize(width * height, ItemStack.EMPTY);
        NonNullList<ItemStack> remains = NonNullList.withSize(width * height, ItemStack.EMPTY);

        for (int i = 0; i < inputItems.length; i++) {
            if (inputItems[i] != null) {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputItems[i]));
                input.set(i, item != null ? new ItemStack(item) : ItemStack.EMPTY);
            } else {
                input.set(i, ItemStack.EMPTY);
            }

            if (remainingItems[i] != null) {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(remainingItems[i]));
                remains.set(i, item != null ? new ItemStack(item) : ItemStack.EMPTY);
            } else {
                remains.set(i, ItemStack.EMPTY);
            }
        }

        registry.register(new HexRecipe(name, output, input, width, height, remains));
    }
}
