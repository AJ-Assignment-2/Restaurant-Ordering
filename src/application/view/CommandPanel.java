package application.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class of command panel extends JPanel implements observable restaurant view
 * 
 */
public class CommandPanel extends JPanel implements ObservableRestaurantView {

    private List<RestaurantViewObserver> observers;

    private JButton enterDataButton;
    private JButton displayChoicesButton;
    private JButton displayOrderButton;
    private JButton prepareButton;
    private JButton billButton;
    private JButton clearDisplayButton;
    private JButton quitButton;

    /**
     * A constructor of command panel
     * 
     */
    public CommandPanel() {
        observers = new ArrayList<>();

        setBorder(BorderFactory.createTitledBorder("Command Buttons"));
        setLayout(new GridLayout(1, 7, 10, 10));

        enterDataButton = new JButton("Enter Data");
        displayChoicesButton = new JButton("Display Choices");
        displayOrderButton = new JButton("Display Orders");
        prepareButton = new JButton("Prepare Order");
        billButton = new JButton("Bill Order");
        clearDisplayButton = new JButton("Clear Display");
        quitButton = new JButton("Quit");

        enterDataButton.addActionListener(event
                -> observers.stream().forEach(RestaurantViewObserver::enterDataButtonPressed));

        displayChoicesButton.addActionListener(event
                -> observers.stream().forEach(RestaurantViewObserver::displayChoicesButtonPressed));

        displayOrderButton.addActionListener(event
                -> observers.stream().forEach(RestaurantViewObserver::displayOrderButtonPressed));

        prepareButton.addActionListener(event
                -> observers.stream().forEach(RestaurantViewObserver::prepareButtonPressed));

        billButton.addActionListener(event
                -> observers.stream().forEach(RestaurantViewObserver::billButtonPressed));

        clearDisplayButton.addActionListener(event
                -> observers.stream().forEach(RestaurantViewObserver::clearDisplayButtonPressed));

        quitButton.addActionListener(event
                -> observers.stream().forEach(RestaurantViewObserver::quitButtonPressed));

        add(enterDataButton);
        add(displayChoicesButton);
        add(displayOrderButton);
        add(prepareButton);
        add(billButton);
        add(clearDisplayButton);
        add(quitButton);
    }

    /**
     * A method to get submit order button
     * @return JButton submit order
     * 
     */
    public JButton getSubmitOrderButton() {
        return enterDataButton;
    }
    
    /**
     * A method to get clear display button
     * @return JButton clear display button
     * 
     */
    public JButton getClearDisplayButton() {
        return clearDisplayButton;
    }

    /**
     * A method to get prepare button
     * @return JButton prepare button
     * 
     */
    public JButton getPrepareButton() {
        return prepareButton;
    }

    /**
     * A method to get bill button
     * @return JButton bill button
     * 
     */
    public JButton getBillButton() {
        return billButton;
    }

    /**
     * A method to add restaurant view observer
     * @param observer observer of restaurant view
     * 
     */
    @Override
    public void addRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.add(observer);
    }

    /**
     * A method to remove restaurant view observer
     * @param observer observer of restaurant view
     * 
     */
    @Override
    public void removeRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.remove(observer);
    }
}
