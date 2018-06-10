package net.einsteinsci.betterbeginnings.crafttweaker.util;

public abstract class AddRemoveAction extends BaseAction {
    private ActionType actionType;

    public AddRemoveAction(ActionType actionType, String crafterName) {
        super(crafterName);
        this.actionType = actionType;
    }

    @Override
    public String describe() {
        switch (actionType) {
            case ADD:
                return "Adding recipe " + recipeToString() + " to " + crafterName;
            case REMOVE:
                return "Removing recipe " + recipeToString() + " from " + crafterName;
            default:
                return null;
        }
    }

    public enum ActionType {
        ADD,
        REMOVE
    }
}
