package net.einsteinsci.betterbeginnings.util;

import java.util.List;
import java.util.Objects;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;

import net.einsteinsci.betterbeginnings.register.recipe.DummyRecipe;
import net.einsteinsci.betterbeginnings.event.DamageSourceDiffusion;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

// General-purpose Util class
public class Util {
	public static final DamageSourceDiffusion DIFFUSION_DAMAGE = new DamageSourceDiffusion();
	public static final Converter<String, String> CASE_CONVERTER = CaseFormat.LOWER_CAMEL
			.converterTo(CaseFormat.LOWER_UNDERSCORE);

	public static boolean areItemStacksEqualIgnoreSize(ItemStack template, ItemStack tested) {
		if (template.isEmpty()) {
			return tested.isEmpty();
		} else if (tested.isEmpty()) {
			return false;
		}

		return template.getItem() == tested.getItem() && (template.getMetadata() == tested.getMetadata()
				|| template.getMetadata() == OreDictionary.WILDCARD_VALUE);
	}

	public static boolean listContainsItemStackIgnoreSize(List<ItemStack> list, ItemStack stack) {
		for (ItemStack s : list) {
			if (areItemStacksEqualIgnoreSize(s, stack)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isEmptyFluidContainer(ItemStack stack) {
		// If the stack has a fluid cap, but no contained fluid, it's probably empty
		if (!stack.isEmpty() && CapUtils.hasFluidHandler(stack) && FluidUtil.getFluidContained(stack) == null)
			return true;
		else
			return false;
	}

	public static void dropInventory(World world, BlockPos pos, IItemHandler itemHandler) {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			if (!itemHandler.getStackInSlot(i).isEmpty()) {
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(),
						itemHandler.getStackInSlot(i));
			}
		}
	}

	public static void dumpItemHandler(IItemHandler handler) {
		for (int s = 0; s < handler.getSlots(); s++) {
			System.out.println(handler.getStackInSlot(s));
		}
		System.out.println("--------------------");
	}
	
	// Adds a basic fake recipe for recipes nerfed in game
    public static void addFakeRecipe(IRecipe recipe) {
		final ResourceLocation registryName = Objects.requireNonNull(recipe.getRegistryName());
		final IRecipe replacement = new DummyRecipe().setRegistryName(registryName);
		ForgeRegistries.RECIPES.register(replacement);
    }
}
