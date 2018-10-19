package application.view;

public interface ObservableRestaurantView {
     /**
     * Register a restaurant model observer.
     * @param observer restaurant view observer to register.
     */
    void addRestaurantViewObserver(RestaurantViewObserver observer);
    
    /**
     * Unregister a restaurant view observer.
     * @param observer restaurant view observer to unregister.
     */
    void removeRestaurantViewObserver(RestaurantViewObserver observer);
}
