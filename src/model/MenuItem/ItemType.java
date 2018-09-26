package model.MenuItem;

public enum ItemType {
    BREAKFAST,
    LUNCH,
    DINNER;

    @Override
    public String toString() {
        switch(this) {
            case BREAKFAST:
                return "Breakfast";
            case LUNCH:
                return "Lunch";
            case DINNER:
                return "Dinner";
            default:
                return "undefined";
        }
    }
}
