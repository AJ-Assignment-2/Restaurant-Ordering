package application.view;

import model.MenuItem.MenuItemType;

public interface RestaurantViewObserver {
    void enterDataButtonPressed();
    void displayChoicesButtonPressed();
    void displayOderButtonPressed();
    void prepareButtonPressed();
    void billButtonPressed();
    void clearDisplayButtonPressed();
    void quitButtonPressed();

    void mealTimeSelected(MenuItemType mealTime);
}
