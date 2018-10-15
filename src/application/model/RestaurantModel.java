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

public class RestaurantModel implements ObservableRestaurantModel {
    private List<RestaurantModelObserver> observers;

    private List<Order> orders;
    private List<MenuItem> menuItems;

    public RestaurantModel() {
        observers = new ArrayList<>();
        orders = new ArrayList<>();
        menuItems = new MenuItemAccessor().readMenuItems();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrder(int tableNumber) {
        List<Order> filteredOrders =  orders.stream().filter(order -> order.getTableNumber() == tableNumber).collect(Collectors.toList());
        if (filteredOrders.isEmpty()) {
            return null;
        } else {
            return filteredOrders.get(0);
        }
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<MenuItem> getMenuItems(MenuItemType type, MenuItemCategory category) {
        return menuItems.stream().filter(item -> item.getType() == type && item.getCategory() == category)
                .collect(Collectors.toList());
    }

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

    private void notifyOrdersUpdated() {
        for (RestaurantModelObserver observer : observers) {
            observer.ordersUpdated(orders);
        }
    }

    @Override
    public void addRestaurantModelObserver(RestaurantModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeRestaurantModelObserver(RestaurantModelObserver observer) {
        observers.remove(observer);
    }
}
