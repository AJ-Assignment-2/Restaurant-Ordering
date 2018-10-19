package application.view;

import java.awt.GridLayout;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class of menu item panel extends JPanel and implements observable restaurant view
 *
 */
public class MenuItemSelectionPanel extends JPanel implements ObservableRestaurantView {

    private List<RestaurantViewObserver> observers;

    private JLabel foodLabel;
    private JComboBox<String> foodComboBox;
    private JLabel beverageLabel;
    private JComboBox<String> beverageComboBox;

    /**
     * A constructor of menu items panel
     */
    public MenuItemSelectionPanel() {
        observers = new ArrayList<>();

        setLayout(new GridLayout(1, 4));
        setBorder(BorderFactory.createTitledBorder("Choose Menu Items"));

        foodLabel = new JLabel("Food");
        foodComboBox = new JComboBox<>();
        beverageLabel = new JLabel("Beverage");
        beverageComboBox = new JComboBox<>();

        add(foodLabel);
        add(foodComboBox);
        add(beverageLabel);
        add(beverageComboBox);
    }

    /**
     * A method to get combo box of beverage
     * @return JComboBox<String> combo box of beverage
     */
    public JComboBox<String> getBeverageComboBox() {
        return beverageComboBox;
    }

    /**
     * A method to get combo box of food
     * @return JComboBox<String> combo box of food
     */
    public JComboBox<String> getFoodComboBox() {
        return foodComboBox;
    }

    /**
     * A method to add restaurant view observer
     * @param observer observer of restaurant view
     */
    @Override
    public void addRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.add(observer);
    }

    /**
     * A method to remove restaurant view observer
     * @param observer observer of restaurant view
     */
    @Override
    public void removeRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.remove(observer);
    }
}
