package application.model;

/**
 * The contract for observable Restaurant model's.
 */
public interface ObservableRestaurantModel {
    /**
     * Register a restaurant model observer.
     * @param observer restaurant model observer to register.
     */
    void addRestaurantModelObserver(RestaurantModelObserver observer);
    
    /**
     * Unregister a restaurant model observer.
     * @param observer restaurant model observer to register.
     */
    void removeRestaurantModelObserver(RestaurantModelObserver observer);
}
