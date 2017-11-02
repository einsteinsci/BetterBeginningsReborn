package net.einsteinsci.betterbeginnings.register;

import com.google.common.base.Equivalence;
import com.google.common.base.Equivalence.Wrapper;
import com.google.common.base.Objects;

import gnu.trove.map.hash.TObjectIntHashMap;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

class FuelMap
{
    	private static final Equivalence<ItemStack> FUEL_EQUIVALENCE = new Equivalence<ItemStack>()
	    {
		@Override
		protected boolean doEquivalent(ItemStack a, ItemStack b)
		{
		    return Util.areItemStacksEqualIgnoreSize(b, a);
		}

		@Override
		protected int doHash(ItemStack t)
		{
		    return Objects.hashCode(t.getItem(), t.getItemDamage(), t.getTagCompound());
		}
	    }; 
    	private final TObjectIntHashMap<Equivalence.Wrapper<ItemStack>> overrides = new TObjectIntHashMap<Equivalence.Wrapper<ItemStack>>();
    	private final IFuelHandler defaults;
    	
    	public FuelMap(IFuelHandler defaults)
	{
    	    this.defaults = defaults;
	}

	void put(ItemStack stack, int fuelValue)
	{
	    overrides.put(FUEL_EQUIVALENCE.wrap(stack), fuelValue);
	}
	
	void remove(ItemStack stack)
	{
	    overrides.remove(FUEL_EQUIVALENCE.wrap(stack));
	}
	
	int getBurnTime(ItemStack stack)
	{
	    Wrapper<ItemStack> wrapper = FUEL_EQUIVALENCE.wrap(stack);
	    if(overrides.containsKey(wrapper)) return overrides.get(wrapper);
	    else return defaults.getBurnTime(stack);
	}
}
