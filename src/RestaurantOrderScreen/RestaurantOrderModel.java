package RestaurantOrderScreen;

import java.util.ArrayList;
import java.util.List;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import services.MenuItemService.MenuItemAccessor;

/**
 *
 * @author Imanuel
 */
public class RestaurantOrderModel {

    private MenuItemAccessor menuItemAccessor;
    private MenuItem menuItem;
    private List<MenuItem> menuItems;
    private String[] foodArray;
    private String[] beverageArray;

    public RestaurantOrderModel() {
        this.menuItemAccessor = new MenuItemAccessor();
        this.menuItem = new MenuItem();
        menuItems = menuItemAccessor.readMenuItems();
    }

    public int getFoodSize() {
        int count = 0;
        for (MenuItem s : menuItemAccessor.readMenuItems()) {
            if (s.getCategory() == MenuItemCategory.FOOD) {
                count++;
            }
        }
        return count;
    }

    public int getBeverageSize() {
        int count = 0;
        for (MenuItem s : menuItemAccessor.readMenuItems()) {
            if (s.getCategory() == MenuItemCategory.BEVERAGE) {
                count++;
            }
        }
        return count;
    }

    public List<MenuItem> getMenuWithType(MenuItemType type) {
        List<MenuItem> foodsWithType = new ArrayList<>();

        menuItems.forEach(item -> {
            if (item.getType() == type) {
                foodsWithType.add(item);
            }
        });

        return foodsWithType;
    }

    public List<MenuItem> getMenuWithCategory(MenuItemCategory category) {
        List<MenuItem> foodsWithCategory = new ArrayList<>();

        menuItems.forEach(item -> {
            if (item.getCategory() == category) {
                foodsWithCategory.add(item);
            }
        });

        return foodsWithCategory;
    }

    public void setupTable() {

    }
}
