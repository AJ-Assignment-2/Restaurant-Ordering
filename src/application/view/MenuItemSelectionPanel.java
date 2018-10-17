package application.view;

import java.awt.GridLayout;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemSelectionPanel extends JPanel implements ObservableRestaurantView {

    private List<RestaurantViewObserver> observers;

    private JLabel foodLabel;
    private JComboBox<String> foodComboBox;
    private JLabel beverageLabel;
    private JComboBox<String> beverageComboBox;

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

    public JComboBox<String> getBeverageComboBox() {
        return beverageComboBox;
    }

    public JComboBox<String> getFoodComboBox() {
        return foodComboBox;
    }

    @Override
    public void addRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.remove(observer);
    }
}
