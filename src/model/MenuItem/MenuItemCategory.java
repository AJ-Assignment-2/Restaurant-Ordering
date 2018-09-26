package model.MenuItem;

public class MenuItemCategory {
    private int id;
    private ItemCategory category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public MenuItemCategory(int id, ItemCategory category) {
        this.id = id;
        this.category = category;
    }

    public MenuItemCategory() {

    }
}
