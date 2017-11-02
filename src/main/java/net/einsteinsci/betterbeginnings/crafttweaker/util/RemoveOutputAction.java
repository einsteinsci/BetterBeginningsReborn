package net.einsteinsci.betterbeginnings.crafttweaker.util;

public abstract class RemoveOutputAction extends BaseAction
{
    public RemoveOutputAction(String crafterName)
    {
	super(crafterName);
    }
    
    @Override
    public String describe()
    {
	return "Removing output " + recipeToString() + " from " + crafterName;
    }

    @Override
    public String describeUndo()
    {
	return "Readding output " + recipeToString() + " to " + crafterName;
    }
}
