package application.services.MenuItemService;

import application.model.menuitem.MenuItem;
import java.util.List;

public interface MenuItemDao {
    List<MenuItem> readMenuItems();
    void createMenuItem(MenuItem itemToAdd);
}
