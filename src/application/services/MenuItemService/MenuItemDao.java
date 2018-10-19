package application.services.MenuItemService;

import application.model.menuitem.MenuItem;
import java.util.List;

public interface MenuItemDao {
    /**
     * A method read of menu items
     *
     * @return List<MenuItem> list of menu item
     */
    List<MenuItem> readMenuItems();
    
    /**
     * A method to create menu item
     *
     * @param itemToAdd contains a menu item
     */
    void createMenuItem(MenuItem itemToAdd);
}
