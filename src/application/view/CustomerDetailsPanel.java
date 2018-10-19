package application.view;


import application.model.menuitem.MenuItemType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A class of customer details panel extends JPanel and implements observable restaurant view
 *
 */
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


    /**
     * A constructor of customer details panel
     * 
     */
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
    
    /**
     * A method to get button group
     * @return JTextArea text area of customer name
     * 
     */
    public JTextArea getCustomerNameTextArea() {
        return customerNameTextArea;
    }
    
    /**
     * A method to get button group
     * @return JTextArea text area of table number
     * 
     */
    public JTextArea getTableNumberTextArea() {
        return tableNumberTextArea;
    }
    
    /**
     * A method to get button group
     * @return ButtonGroup group or radio button
     */
    public ButtonGroup getButtonGroup() {
        return radioButtonGroup;
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

    /**
     * A class to listen meal type
     * 
     */
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
