package application.model;

public interface ObservableRestaurantModel {
    void addRestaurantModelObserver(RestaurantModelObserver observer);
    void removeRestaurantModelObserver(RestaurantModelObserver observer);
}
