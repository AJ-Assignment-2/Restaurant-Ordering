package model.MenuItem.MenuItemService;

import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuAccessor {
    private MenuItemDao menuItemAccessor;
    private MenuItemCategoryDao menuItemCategoryAccessor;
    private MenuItemTypeDao menuItemTypeAccessor;

    private static final Logger LOGGER = Logger.getLogger( MenuAccessor.class.getName() );

    public MenuAccessor(){
        menuItemAccessor = new MenuItemAccessor();
        menuItemCategoryAccessor = new MenuItemCategoryAccessor();
        menuItemTypeAccessor  = new MenuItemTypeAccessor();

        populateMenuTables();
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemAccessor.readMenuItems();

        menuItems.stream().forEach(item -> {
            item.setCategory(menuItemCategoryAccessor.getMenuItemCategory(item.getCategory().getId()));
            item.setType(menuItemTypeAccessor.getMenuItemType(item.getType().getId()));
        });

        return menuItems;
    }

    private void populateMenuTables() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    MenuAccessor.class.getResourceAsStream("/resources/menu_item_data.csv"), "UTF-8"));

            String line;
            Boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (!firstLine) {
                    String[] itemData = line.split(",");
                    MenuItem item = new MenuItem();

                    switch(itemData[0].toLowerCase()) {
                        case "food":
                            item.setCategory(new MenuItemCategory());
                    }
                } else {
                    firstLine = false;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to access or parse menu_item_data.csv: " + e.toString(), e);
        }
    }

}
