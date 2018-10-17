package application.view;


import application.model.menuitem.MenuItemType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerDetailsPanel extends JPanel implements ObservableRestaurantView {
    private List<RestaurantViewObserver> observers;

    private JPanel customerNamePanel;
    private JPanel tablePanel;
    private JPanel mealPanel;

    private JLabel customerNameLabel;
    private JTextArea customerNameTextArea;
    private JLabel tableNumberLabel;
    private JTextArea tableNumberTextArea;
    private JLabel mealType;
    private JRadioButton breakfastRadioButton;
    private JRadioButton lunchRadioButton;
    private JRadioButton dinnerRadioButton;
    private ButtonGroup radioButtonGroup;


    public CustomerDetailsPanel() {
        observers = new ArrayList<>();

        setLayout(new GridLayout(1, 2));
        setBorder(BorderFactory.createTitledBorder("Customer Details"));
        customerNamePanel = new JPanel();
        tablePanel = new JPanel();
        mealPanel = new JPanel();

        customerNameLabel = new JLabel("Customer Name: ");
        customerNameTextArea = new JTextArea(1, 10);
        tableNumberLabel = new JLabel("Table Number: ");
        tableNumberTextArea = new JTextArea(1, 5);
        mealType = new JLabel("Meal Type: ");
        breakfastRadioButton = new JRadioButton("Breakfast");
        lunchRadioButton = new JRadioButton("Lunch");
        dinnerRadioButton = new JRadioButton("Dinner");
        radioButtonGroup = new ButtonGroup();

        breakfastRadioButton.addActionListener(new MealTypeSelectedListener(MenuItemType.BREAKFAST));
        lunchRadioButton.addActionListener(new MealTypeSelectedListener(MenuItemType.LUNCH));
        dinnerRadioButton.addActionListener(new MealTypeSelectedListener(MenuItemType.DINNER));

        customerNamePanel.add(customerNameLabel);
        customerNamePanel.add(customerNameTextArea);
        tablePanel.add(tableNumberLabel);
        tablePanel.add(tableNumberTextArea);
        radioButtonGroup.add(breakfastRadioButton);
        radioButtonGroup.add(lunchRadioButton);
        radioButtonGroup.add(dinnerRadioButton);
        mealPanel.add(mealType);
        mealPanel.add(breakfastRadioButton);
        mealPanel.add(lunchRadioButton);
        mealPanel.add(dinnerRadioButton);

        add(customerNamePanel);
        add(tablePanel);
        add(mealPanel);
    }

    public JTextArea getCustomerNameTextArea() {
        return customerNameTextArea;
    }

    public JTextArea getTableNumberTextArea() {
        return tableNumberTextArea;
    }
    
    public ButtonGroup getButtonGroup() {
        return radioButtonGroup;
    }
    
    @Override
    public void addRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.remove(observer);
    }

    private class MealTypeSelectedListener implements ActionListener {
        private final MenuItemType type;

        MealTypeSelectedListener(MenuItemType type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            observers.stream().forEach(observer -> observer.mealTimeSelected(type));
        }
    }

}
