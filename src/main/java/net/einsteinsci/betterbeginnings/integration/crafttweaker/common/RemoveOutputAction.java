package net.einsteinsci.betterbeginnings.integration.crafttweaker.common;

public abstract class RemoveOutputAction extends BaseAction {
    public RemoveOutputAction(String crafterName) {
        super(crafterName);
    }

    @Override
    public String describe() {
        return "Removing output " + recipeToString() + " from " + crafterName;
    }
}
