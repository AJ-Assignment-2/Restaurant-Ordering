package application.view;

import model.MenuItem.MenuItemType;

public interface RestaurantViewObserver {
    void enterDataButtonPressed();
    void displayChoicesButtonPressed();
    void displayOrderButtonPressed();
    void prepareButtonPressed();
    void billButtonPressed();
    void clearDisplayButtonPressed();
    void quitButtonPressed();

    void mealTimeSelected(MenuItemType mealTime);
}
