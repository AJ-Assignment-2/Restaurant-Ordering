package application.model;

import application.model.menuitem.MenuItem;
import application.model.menuitem.MenuItemCategory;
import application.model.menuitem.MenuItemType;
import application.model.order.Order;
import application.services.MenuItemService.MenuItemAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * This class implements model of restaurant screen and implements ObservableRestaurantModel
 * 
 */
public class RestaurantModel implements ObservableRestaurantModel {
    private List<RestaurantModelObserver> observers;

    private List<Order> orders;
    private List<MenuItem> menuItems;

    /**
     * A constructor of restaurant model
     */
    public RestaurantModel() {
        observers = new ArrayList<>();
        orders = new ArrayList<>();
        menuItems = new MenuItemAccessor().readMenuItems();
    }

    /**
     * A method to get all orders from customer
     * 
     * @return List<Order> containing list of the order
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * A method to get a order
     * 
     * @param tableNumber number of table
     * @return Order containing an item order
     */
    public Order getOrder(int tableNumber) {
        List<Order> filteredOrders =  orders.stream().filter(order -> order.getTableNumber() == tableNumber).collect(Collectors.toList());
        if (filteredOrders.isEmpty()) {
            return null;
        } else {
            return filteredOrders.get(0);
        }
    }

    /**
     * A method to get a menu item
     * 
     * @return List<MenuItem> containing list of menu item
     */
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * A method to get menu items
     * 
     * @param type type of menu item
     * @param category category of menu item
     * @return List<MenuItem> containing list of menu item
     */
    public List<MenuItem> getMenuItems(MenuItemType type, MenuItemCategory category) {
        return menuItems.stream().filter(item -> item.getType() == type && item.getCategory() == category)
                .collect(Collectors.toList());
    }

    /**
     * A method to store an order
     * 
     * @param order containing order object
     */
    public void storeOrder(Order order) {
        try {
            Order matchedOrder = orders.stream().filter(item -> item.getTableNumber() == order.getTableNumber())
                    .findFirst().get();
            matchedOrder = order;
        } catch (NoSuchElementException e) {
            orders.add(order);
        }

        notifyOrdersUpdated();
    }

    /**
     * A method to give update for order
     * 
     */
    private void notifyOrdersUpdated() {
        for (RestaurantModelObserver observer : observers) {
            observer.ordersUpdated(orders);
        }
    }

    /**
     * A method to add model restaurant observer from interface
     * 
     * @param observer object of observer
     */
    @Override
    public void addRestaurantModelObserver(RestaurantModelObserver observer) {
        observers.add(observer);
    }

    /**
     * A method to remove model restaurant observer from interface
     * 
     * @param observer object of observer
     */
    @Override
    public void removeRestaurantModelObserver(RestaurantModelObserver observer) {
        observers.remove(observer);
    }
}
