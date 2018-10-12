package screen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import model.Order.Order;
import services.MenuItemService.MenuItemAccessor;

public class Model {

    private final MenuItemAccessor accessor;
    private final List<MenuItem> items;
    private final List<Order> orders;

    public Model() {
        this.accessor = new MenuItemAccessor();
        this.items = new ArrayList<>(accessor.readMenuItems());
        this.orders = new ArrayList<>();
    }

    public MenuItemAccessor getAccessor() {
        return this.accessor;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<MenuItem> getItems(MenuItemType type, MenuItemCategory category) {
        return getItems().stream()
                .filter(item -> item.getType() == type)
                .filter(item -> item.getCategory() == category)
                .collect(Collectors.toList());
    }

    public void addOrder(Order order) {
        for (Order o : getOrders()) {
            if (o.getTableNumber() == order.getTableNumber()) {
                o.getMenuItemSelections().putAll(order.getMenuItemSelections());
                return;
            }
        }

        getOrders().add(order);
    }

    public Order findOrder(int table) {
        for (Order order : getOrders()) {
            if (order.getTableNumber() == order.getTableNumber()) {
                return order;
            }
        }

        return null;
    }

}
