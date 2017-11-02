package net.einsteinsci.betterbeginnings.crafttweaker.util;

import minetweaker.IUndoableAction;

public abstract class BaseAction implements IUndoableAction
{   
    protected String crafterName;

    public BaseAction(String crafterName)
    {
	this.crafterName = crafterName;
    }
    
    public abstract String recipeToString();

    @Override
    public boolean canUndo()
    {
	return true;
    }

    @Override
    public Object getOverrideKey()
    {
	return null;
    }
}
