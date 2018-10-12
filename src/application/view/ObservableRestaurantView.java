package application.view;

public interface ObservableRestaurantView {
    void addRestaurantViewObserver(RestaurantViewObserver observer);
    void removeRestaurantViewObserver(RestaurantViewObserver observer);
}
