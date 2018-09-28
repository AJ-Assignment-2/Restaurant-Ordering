package model.Order;

import model.MenuItem.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private int id;
    private String customerName;
    private HashMap<MenuItem, Integer> menuItemSelections;
    private OrderState state;
    private int tableNumber;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public HashMap<MenuItem, Integer> getMenuItemSelections() {
        return menuItemSelections;
    }

    public void setMenuItemSelections(HashMap<MenuItem, Integer> menuItemSelections) {
        this.menuItemSelections = menuItemSelections;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order(String customerName, HashMap<MenuItem, Integer> menuItemSelections) {
        this.customerName = customerName;
        this.menuItemSelections = new HashMap<>(menuItemSelections);
    }

    public void addItem(MenuItem item) {
        if (menuItemSelections.containsKey(item)) {
            menuItemSelections.put(item, menuItemSelections.get(item) + 1);
        } else {
            menuItemSelections.put(item, 1);
        }
    }

    public Order() {
        menuItemSelections = new HashMap<>();
    }

}
