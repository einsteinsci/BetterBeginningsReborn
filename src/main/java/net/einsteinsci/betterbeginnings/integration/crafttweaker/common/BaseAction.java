package net.einsteinsci.betterbeginnings.crafttweaker.common;

import crafttweaker.IAction;

public abstract class BaseAction implements IAction {
    protected String crafterName;

    public BaseAction(String crafterName) {
        this.crafterName = crafterName;
    }

    public abstract String recipeToString();
}
