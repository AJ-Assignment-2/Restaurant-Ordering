package application.model;

import application.model.order.Order;
import java.util.List;

/**
 * The contract for observable Restaurant model's.
 */
public interface RestaurantModelObserver {
    /**
     * Updated restaurant order
     * @param orders containing list of order
     */
    void ordersUpdated(List<Order> orders);
}
