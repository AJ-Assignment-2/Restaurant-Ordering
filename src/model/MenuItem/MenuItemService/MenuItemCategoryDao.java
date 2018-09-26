package model.MenuItem.MenuItemService;

import model.MenuItem.ItemCategory;
import model.MenuItem.MenuItemCategory;

import java.util.List;

public interface MenuItemCategoryDao {
    List<MenuItemCategory> readMenuItemCategories();
    void createMenuItemCategory(MenuItemCategory categoryToAdd);
    MenuItemCategory getMenuItemCategory(int id);
}
