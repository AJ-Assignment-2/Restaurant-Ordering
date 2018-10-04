package model.MenuItem;

/**
 * Represents the different types of menu items.
 */
public enum MenuItemType {
    BREAKFAST,
    LUNCH,
    DINNER;

    @Override
    public String toString() {
        switch (this) {
            case BREAKFAST:
                return "Breakfast";
            case LUNCH:
                return "Lunch";
            case DINNER:
                return "Dinner";
            default:
                return "Undefined";

        }
    }

    public boolean compareTo(int typeMenu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
