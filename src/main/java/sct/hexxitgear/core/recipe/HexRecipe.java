package sct.hexxitgear.core.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HexRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private final ItemStack output;
    private final NonNullList<ItemStack> input;
    private final int width;
    private final int height;
    private final NonNullList<ItemStack> remainingItems;

    public HexRecipe(ResourceLocation group, ItemStack output, NonNullList<ItemStack> input, int width, int height, NonNullList<ItemStack> remainingItems) {
        this.setRegistryName(group);
        this.output = output;
        this.input = input;
        this.width = width;
        this.height = height;
        this.remainingItems = remainingItems;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        for (int i = 0; i <= inv.getWidth() - width; i++) {
            for (int j = 0; j <= inv.getHeight() - height; j++) {
                if (checkMatch(inv, i, j, true)) {
                    return true;
                }
                if (checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
        for (int x = 0; x < inv.getWidth(); x++) {
            for (int y = 0; y < inv.getHeight(); y++) {
                int subX = x - startX;
                int subY = y - startY;
                ItemStack target = ItemStack.EMPTY;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
                    if (mirror) {
                        target = input.get(width - subX - 1 + subY * width);
                    } else {
                        target = input.get(subX + subY * width);
                    }
                }

                if (!ItemStack.areItemStacksEqual(inv.getStackInSlot(x + y * inv.getWidth()), target)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remains = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < remains.size(); i++) {
            if (i < remainingItems.size() && !remainingItems.get(i).isEmpty()) {
                remains.set(i, remainingItems.get(i).copy());
            }
        }

        return remains;
    }
}
