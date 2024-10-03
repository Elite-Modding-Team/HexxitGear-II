package sct.hexxitgear.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import net.minecraft.item.ItemStack;
import sct.hexxitgear.init.HexRegistry;

import java.util.Collections;

@JEIPlugin
public class HexJEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD_HILT)), Collections.singletonList(new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD_BLADE)), Collections.singletonList(new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD_INACTIVE)))), VanillaRecipeCategoryUid.ANVIL);
    }
}
