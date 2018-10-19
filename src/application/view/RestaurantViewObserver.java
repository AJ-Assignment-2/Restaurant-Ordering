package application.view;

import application.model.menuitem.MenuItemType;

public interface RestaurantViewObserver {
    /**
     * Enter data button when it pressed
     */
    void enterDataButtonPressed();
    /**
     * Display choices button when it pressed
     */
    void displayChoicesButtonPressed();
    /**
     * Display order button when it pressed
     */
    void displayOrderButtonPressed();
    /**
     * Display prepare button when it pressed
     */
    void prepareButtonPressed();
    /**
     * Bill button when it pressed
     */
    void billButtonPressed();
    /**
     * Clear display button when it pressed
     */
    void clearDisplayButtonPressed();
    /**
     * Quit button when it pressed
     */
    void quitButtonPressed();
    /**
     * Select meal time
     * 
     * @param mealTime type of menu item
     */
    void mealTimeSelected(MenuItemType mealTime);
}
