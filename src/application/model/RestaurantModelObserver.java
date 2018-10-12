package application.model;

import model.Order.Order;

import java.util.List;

public interface RestaurantModelObserver {
    void ordersUpdated(List<Order> orders);
}
