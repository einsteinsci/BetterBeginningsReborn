package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.*;

import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class InfusionRepairRecipeHandler {
	private static final InfusionRepairRecipeHandler INSTANCE = new InfusionRepairRecipeHandler();
	private Map<ResourceLocation, RecipeElement> enchantmentMapping = new HashMap<>();

	public static InfusionRepairRecipeHandler instance() {
		return INSTANCE;
	}

	public static void registerEnchantment(Enchantment enchant, RecipeElement stack) {
		instance().enchantmentMapping.put(enchant.getRegistryName(), stack);
	}

	public static void registerEnchantment(ResourceLocation enchID, RecipeElement stack) {
		instance().enchantmentMapping.put(enchID, stack);
	}

	public ArrayList<RecipeElement> getRequiredStacks(ItemStack repaired) {
		ArrayList<RecipeElement> requiredItems = new ArrayList<>();

		if (repaired.isEmpty()) {
			return requiredItems;
		}

		if (!repaired.isItemDamaged()) {
			return requiredItems;
		}

		if (repaired.getItem() instanceof ItemBow) {
			requiredItems.add(new OreRecipeElement("stickWood", 2));
			requiredItems.add(new OreRecipeElement("itemString", 2));
		}

		if (repaired.getItem() == RegisterItems.noobWoodSword) {
			requiredItems.add(new OreRecipeElement("plankWood"));
		} else if (repaired.getItem() == RegisterItems.flintKnife || repaired.getItem() == RegisterItems.flintHatchet) {
			requiredItems.add(new StackRecipeElement(Items.FLINT));
		} else if (repaired.getItem() == RegisterItems.boneKnife) {
			requiredItems.add(new StackRecipeElement(Items.BONE));
		} else if (repaired.getItem() == RegisterItems.bonePickaxe) {
			requiredItems.add(new StackRecipeElement(RegisterItems.boneShard));
		} else if (repaired.getItem() instanceof ItemTool) {
			ItemTool tool = (ItemTool) repaired.getItem();
			Item.ToolMaterial material = Item.ToolMaterial.valueOf(tool.getToolMaterialName());

			switch (material) {
			case WOOD:
				requiredItems.add(new OreRecipeElement("plankWood"));
				break;
			case STONE:
				requiredItems.add(new OreRecipeElement("stone"));
				break;
			case IRON:
				requiredItems.add(new OreRecipeElement("ingotIron"));
				break;
			case GOLD:
				requiredItems.add(new OreRecipeElement("nuggetGold", 2));
				break;
			case DIAMOND:
				requiredItems.add(new OreRecipeElement("dustRedstone", 24));
				break;
			default:
				requiredItems.add(new OreRecipeElement("gemEmerald", 4));
				break;
			}
		} else if (repaired.getItem() instanceof ItemSword) {
			ItemSword sword = (ItemSword) repaired.getItem();
			Item.ToolMaterial material = Item.ToolMaterial.valueOf(sword.getToolMaterialName());

			switch (material) {
			case WOOD:
				requiredItems.add(new OreRecipeElement("plankWood"));
				break;
			case STONE:
				requiredItems.add(new OreRecipeElement("stone"));
			case IRON:
				requiredItems.add(new OreRecipeElement("ingotIron"));
				break;
			case GOLD:
				requiredItems.add(new OreRecipeElement("nuggetGold", 2));
				break;
			case DIAMOND:
				requiredItems.add(new OreRecipeElement("dustRedstone", 16));
				break;
			default:
				if (material == RegisterItems.NOOBWOOD) {
					requiredItems.add(new OreRecipeElement("plankWood"));
				} else {
					requiredItems.add(new OreRecipeElement("gemEmerald", 4));
				}
				break;
			}
		} else if (repaired.getItem() instanceof ItemArmor) {
			ItemArmor armor = (ItemArmor) repaired.getItem();

			switch (armor.getArmorMaterial()) {
			case LEATHER:
				requiredItems.add(new StackRecipeElement(Items.LEATHER));
				break;
			case CHAIN:
				requiredItems.add(new OreRecipeElement("nuggetIron", 6));
				break;
			case IRON:
				requiredItems.add(new OreRecipeElement("ingotIron", 3));
				break;
			case GOLD:
				requiredItems.add(new OreRecipeElement("nuggetGold", 2));
				break;
			case DIAMOND:
				requiredItems.add(new OreRecipeElement("dustRedstone", 24));
				break;
			default:
				requiredItems.add(new OreRecipeElement("gemEmerald", 4));
				break;
			}
		} else if (repaired.getItem() == Items.SHEARS) {
			requiredItems.add(new OreRecipeElement("ingotIron"));
		} else if (repaired.getItem() == Items.FISHING_ROD) {
			requiredItems.add(new OreRecipeElement("itemString", 4));
		} else if (repaired.getItem() == Items.FLINT_AND_STEEL) {
			requiredItems.add(new StackRecipeElement(Items.FLINT));
		}

		if (repaired.isItemEnchanted()) {
			if (repaired.getTagCompound() != null) {
				if (repaired.getTagCompound().getTag("ench") != null) {
					NBTTagList enchants = (NBTTagList) repaired.getTagCompound().getTag("ench");
					requiredItems.addAll(getEnchantmentItems(enchants));
				}
			}
		}
		return requiredItems;
	}

	public List<RecipeElement> getEnchantmentItems(NBTTagList enchants) {
		List<RecipeElement> requiredItems = new ArrayList<>();

		for (int i = 0; i < enchants.tagCount(); i++) {
			NBTTagCompound enchant = enchants.getCompoundTagAt(i);
			int enchIdInt = enchant.getInteger("id");
			int level = enchant.getInteger("lvl");
			RecipeElement associated;
			if (enchIdInt != 0) {
				associated = enchantmentMapping.get(Enchantment.REGISTRY.getObjectById(enchIdInt).getRegistryName());
			} else {
				String enchIdString = enchant.getString("id");
				associated = enchantmentMapping.get(new ResourceLocation(enchIdString));
			}

			if (associated != null) {
				RecipeElement req = associated.copy();
				req.setStackSize(req.getStackSize() * level);
				requiredItems.add(req);
			} else {
				// LogUtil.log(Level.ERROR, "No repair infusion item associated with enchantment
				// #" + enchId); // This spams the console
			}
		}

		return requiredItems;
	}

	public int getTakenLevels(ItemStack infusedItem) {
		if (infusedItem.isEmpty()) {
			return 0;
		}

		if (infusedItem.getItem() == RegisterItems.noobWoodSword) {
			return 1;
		} else if (infusedItem.getItem() == RegisterItems.flintKnife
				|| infusedItem.getItem() == RegisterItems.flintHatchet) {
			return 1;
		} else if (infusedItem.getItem() == RegisterItems.boneKnife) {
			return 2;
		} else if (infusedItem.getItem() == RegisterItems.bonePickaxe) {
			return 1;
		} else if (infusedItem.getItem() instanceof ItemTool) {
			ItemTool tool = (ItemTool) infusedItem.getItem();
			Item.ToolMaterial material = Item.ToolMaterial.valueOf(tool.getToolMaterialName());

			switch (material) {
			case WOOD:
				return 1;
			case STONE:
				return 2;
			case IRON:
				return 5;
			case GOLD:
				return 3;
			case DIAMOND:
				return 8;
			default:
				return 8;
			}
		} else if (infusedItem.getItem() instanceof ItemSword) {
			ItemSword sword = (ItemSword) infusedItem.getItem();
			Item.ToolMaterial material = Item.ToolMaterial.valueOf(sword.getToolMaterialName());

			switch (material) {
			case WOOD:
				return 1;
			case STONE:
				return 2;
			case IRON:
				return 5;
			case GOLD:
				return 3;
			case DIAMOND:
				return 8;
			default:
				if (material == RegisterItems.NOOBWOOD) {
					return 1;
				} else {
					return 8;
				}
			}
		} else if (infusedItem.getItem() instanceof ItemArmor) {
			ItemArmor armor = (ItemArmor) infusedItem.getItem();

			switch (armor.getArmorMaterial()) {
			case LEATHER:
				return 1;
			case CHAIN:
				return 2;
			case IRON:
				return 5;
			case GOLD:
				return 3;
			case DIAMOND:
				return 8;
			default:
				return 8;
			}
		} else if (infusedItem.getItem() == Items.SHEARS) {
			return 2;
		} else if (infusedItem.getItem() == Items.FISHING_ROD) {
			return 2;
		} else if (infusedItem.getItem() == Items.FLINT_AND_STEEL) {
			return 1;
		} else if (infusedItem.getItem() == Items.BOW) {
			return 5;
		}

		return 0;
	}
}
