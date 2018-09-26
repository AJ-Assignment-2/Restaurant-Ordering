package model.MenuItem;

public enum ItemCategory {
    FOOD,
    BEVERAGE;

    @Override
    public String toString() {
        switch(this) {
            case FOOD:
                return "Food";
            case BEVERAGE:
                return "Beverage";
            default:
                return "undefined";
        }
    }
}
