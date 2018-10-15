package application.model;

import application.model.order.Order;
import java.util.List;

public interface RestaurantModelObserver {
    void ordersUpdated(List<Order> orders);
}
