package sct.hexxitgear.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.block.BlockHexbiscus;
import sct.hexxitgear.item.*;
import shadows.placebo.item.ItemBase;

public class HexRegistry {

    public static final BlockHexbiscus HEXBISCUS = new BlockHexbiscus();

    public static final Item HEXICAL_ESSENCE = new ItemBase("hexical_essence", HexxitGear.INFO) {
    };
    public static final Item HEXICAL_DIAMOND = new ItemBase("hexical_diamond", HexxitGear.INFO) {
    };
    public static final Item REFRESHER_SHARD = new ItemRefresherShard();

    public static final ItemTribalArmor TRIBAL_HELMET = new ItemTribalArmor("tribal_helmet", EntityEquipmentSlot.HEAD);
    public static final ItemTribalArmor TRIBAL_CHEST = new ItemTribalArmor("tribal_chest", EntityEquipmentSlot.CHEST);
    public static final ItemTribalArmor TRIBAL_LEGS = new ItemTribalArmor("tribal_legs", EntityEquipmentSlot.LEGS);
    public static final ItemTribalArmor TRIBAL_BOOTS = new ItemTribalArmor("tribal_boots", EntityEquipmentSlot.FEET);

    public static final Item THIEF_HELMET = new ItemThiefArmor("thief_helmet", EntityEquipmentSlot.HEAD);
    public static final Item THIEF_CHEST = new ItemThiefArmor("thief_chest", EntityEquipmentSlot.CHEST);
    public static final Item THIEF_LEGS = new ItemThiefArmor("thief_legs", EntityEquipmentSlot.LEGS);
    public static final Item THIEF_BOOTS = new ItemThiefArmor("thief_boots", EntityEquipmentSlot.FEET);

    public static final Item SCALE_HELMET = new ItemScaleArmor("scale_helmet", EntityEquipmentSlot.HEAD);
    public static final Item SCALE_CHEST = new ItemScaleArmor("scale_chest", EntityEquipmentSlot.CHEST);
    public static final Item SCALE_LEGS = new ItemScaleArmor("scale_legs", EntityEquipmentSlot.LEGS);
    public static final Item SCALE_BOOTS = new ItemScaleArmor("scale_boots", EntityEquipmentSlot.FEET);

    public static final Item SAGE_HELMET = new ItemMagicianArmor("sage_helmet", EntityEquipmentSlot.HEAD);
    public static final Item SAGE_CHEST = new ItemMagicianArmor("sage_chest", EntityEquipmentSlot.CHEST);
    public static final Item SAGE_LEGS = new ItemMagicianArmor("sage_legs", EntityEquipmentSlot.LEGS);
    public static final Item SAGE_BOOTS = new ItemMagicianArmor("sage_boots", EntityEquipmentSlot.FEET);

    public static final Item.ToolMaterial HEXICAL = EnumHelper.addToolMaterial("HEXICAL", 100, 9001, 50.0F, 19.0F, 50).setRepairItem(new ItemStack(HEXICAL_DIAMOND));
    @GameRegistry.ObjectHolder(HexxitGear.MODID + ":hexical_master_sword")
    public static final Item HEXICAL_MASTER_SWORD = new ItemMasterSword("hexical_master_sword", HEXICAL);
    public static final Item HEXICAL_MASTER_SWORD_BLADE = new ItemBaseDesc("hexical_master_sword_blade", HexxitGear.INFO) {
    };
    public static final Item HEXICAL_MASTER_SWORD_HILT = new ItemBaseDesc("hexical_master_sword_hilt", HexxitGear.INFO) {
    };
    public static final Item HEXICAL_MASTER_SWORD_ACTIVATION = new ItemBaseNoTab("hexical_master_sword_activation", HexxitGear.INFO) {
    };
    @GameRegistry.ObjectHolder(HexxitGear.MODID + ":hexical_master_sword_inactive")
    public static final Item HEXICAL_MASTER_SWORD_INACTIVE = new ItemMasterSword("hexical_master_sword_inactive", Item.ToolMaterial.DIAMOND).setNoRepair();
    public static final SoundEvent HEXICAL_MASTER_SWORD_ACTIVATION_SOUND = new SoundEvent(new ResourceLocation(HexxitGear.MODID, "items.hexical_master_sword.activation"));

    @SubscribeEvent
    public void items(Register<Item> event) {
        event.getRegistry().registerAll(HexxitGear.INFO.getItemList().toArray(new Item[0]));
        event.getRegistry().register(HEXICAL_MASTER_SWORD_INACTIVE);
        event.getRegistry().register(HEXICAL_MASTER_SWORD);
    }

    @SubscribeEvent
    public void blocks(Register<Block> event) {
        event.getRegistry().register(HEXBISCUS);
    }

    @SubscribeEvent
    public void recipes(Register<IRecipe> e) {
        ItemStack rWool = new ItemStack(Blocks.WOOL, 1, 14);
        HexxitGear.HELPER.addShaped(HEXICAL_DIAMOND, 3, 3, null, HEXICAL_ESSENCE, null, HEXICAL_ESSENCE, Items.DIAMOND, HEXICAL_ESSENCE, null, HEXICAL_ESSENCE, null);
        HexxitGear.HELPER.addShaped(TRIBAL_HELMET, 3, 2, Items.BONE, Items.BONE, Items.BONE, Items.BONE, HEXICAL_DIAMOND, Items.BONE);
        HexxitGear.HELPER.addShaped(TRIBAL_CHEST, 3, 3, "ingotIron", null, "ingotIron", Items.LEATHER, HEXICAL_DIAMOND, Items.LEATHER, "ingotIron", Items.LEATHER, "ingotIron");
        HexxitGear.HELPER.addShaped(TRIBAL_LEGS, 3, 3, Items.LEATHER, Items.LEATHER, Items.LEATHER, "ingotIron", HEXICAL_DIAMOND, "ingotIron", Items.LEATHER, null, Items.LEATHER);
        HexxitGear.HELPER.addShaped(TRIBAL_BOOTS, 3, 2, Items.STRING, HEXICAL_DIAMOND, Items.STRING, Items.LEATHER, null, Items.LEATHER);
        HexxitGear.HELPER.addShaped(THIEF_HELMET, 3, 2, rWool, rWool, rWool, rWool, HEXICAL_DIAMOND, rWool);
        HexxitGear.HELPER.addShaped(THIEF_CHEST, 3, 3, rWool, null, rWool, Items.LEATHER, HEXICAL_DIAMOND, Items.LEATHER, Items.LEATHER, Items.LEATHER, Items.LEATHER);
        HexxitGear.HELPER.addShaped(THIEF_LEGS, 3, 3, Items.LEATHER, Items.STRING, Items.LEATHER, Items.LEATHER, HEXICAL_DIAMOND, Items.LEATHER, Items.LEATHER, null, Items.LEATHER);
        HexxitGear.HELPER.addShaped(THIEF_BOOTS, 3, 2, Items.LEATHER, HEXICAL_DIAMOND, Items.LEATHER, new ItemStack(Blocks.WOOL, 1, 7), null, new ItemStack(Blocks.WOOL, 1, 7));
        HexxitGear.HELPER.addShaped(SCALE_HELMET, 3, 2, "ingotGold", Blocks.OBSIDIAN, "ingotGold", Blocks.OBSIDIAN, HEXICAL_DIAMOND, Blocks.OBSIDIAN);
        HexxitGear.HELPER.addShaped(SCALE_CHEST, 3, 3, "ingotGold", null, "ingotGold", Blocks.OBSIDIAN, HEXICAL_DIAMOND, Blocks.OBSIDIAN, "ingotGold", Blocks.OBSIDIAN, "ingotGold");
        HexxitGear.HELPER.addShaped(SCALE_LEGS, 3, 3, Blocks.OBSIDIAN, Blocks.OBSIDIAN, Blocks.OBSIDIAN, "ingotGold", HEXICAL_DIAMOND, "ingotGold", Blocks.OBSIDIAN, null, Blocks.OBSIDIAN);
        HexxitGear.HELPER.addShaped(SCALE_BOOTS, 3, 2, Blocks.OBSIDIAN, HEXICAL_DIAMOND, Blocks.OBSIDIAN, Blocks.OBSIDIAN, null, Blocks.OBSIDIAN);
        HexxitGear.HELPER.addShaped(SAGE_HELMET, 3, 3, null, Items.BOOK, null, Blocks.OBSIDIAN, HEXICAL_DIAMOND, Blocks.OBSIDIAN, Blocks.WOOL, null, Blocks.WOOL);
        HexxitGear.HELPER.addShaped(SAGE_CHEST, 3, 3, "ingotGold", null, "ingotGold", Blocks.WOOL, HEXICAL_DIAMOND, Blocks.WOOL, "ingotGold", Items.BOOK, "ingotGold");
        HexxitGear.HELPER.addShaped(SAGE_LEGS, 3, 3, "ingotGold", Items.BOOK, "ingotGold", Blocks.WOOL, HEXICAL_DIAMOND, Blocks.WOOL, "ingotGold", null, "ingotGold");
        HexxitGear.HELPER.addShaped(SAGE_BOOTS, 3, 2, Blocks.WOOL, HEXICAL_DIAMOND, Blocks.WOOL, "ingotGold", null, "ingotGold");
        e.getRegistry().registerAll(HexxitGear.INFO.getRecipeList().toArray(new IRecipe[0]));
    }
}
