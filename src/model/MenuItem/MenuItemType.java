package model.MenuItem;

public class MenuItemType {
    private int id;
    private ItemType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public MenuItemType(int id, ItemType type) {
        this.id = id;
        this.type = type;
    }

    public MenuItemType() {

    }
}
