package model.MenuItem.MenuItemService;

import model.MenuItem.MenuItemType;

import java.util.List;

public interface MenuItemTypeDao {
    List<MenuItemType> getMenuItemTypes();
    MenuItemType getMenuItemType(int id);
    void createMenuItemType(MenuItemType typeToAdd);
}
