package screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import model.Order.OrderState;
import model.Order.OrderTableModel;

public class Views extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuAbout;

    private Border border;
    private JPanel northPanel;
    private JPanel customerDetailsPanel;
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

    private JPanel chooseMenuItemsPanel;
    private JLabel foodLabel;
    private JComboBox<String> foodComboBox;
    private JLabel beverageLabel;
    private JComboBox<String> beverageComboBox;

    private JPanel orderStatusPanel;
    private JPanel waitingOrdersPanel;
    private JPanel servingOrdersPanel;
    private JLabel waitingOrders;
    private JTextArea waitingOrdersTextArea;
    private JScrollPane scrollableWaitingOrdersTextArea;
    private JLabel servingOrders;
    private JTextArea servingOrdersTextArea;
    private JScrollPane scrollableServingOrdersTextArea;

    private JPanel centrePanel;
    private JTable waitingTable;
    private JTable servedTable;

    private JPanel southPanel;
    private JButton enterDataButton;
    private JButton displayChoicesButton;
    private JButton displayOrderButton;
    private JButton prepareButton;
    private JButton billButton;
    private JButton clearDisplayButton;
    private JButton quitButton;

    private String[][] labels = {{"Customer Details"}, {"Choose Menu Items"}, {"Order Status"}, {"Menu Choices and Nutrition Information"}, {"Command Buttons"}};

    public Views() {
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("File");
        this.menuAbout = new JMenuItem("About");
        menuFile.add(menuAbout);
        menuBar.add(menuFile);
        this.setJMenuBar(menuBar);
        setupCustomerScreen();
    }

    public void setupCustomerScreen() {
        border = BorderFactory.createLineBorder(Color.BLACK);

        northPanel = new JPanel();
        customerDetailsPanel = new JPanel();
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder(labels[0][0]));
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

        chooseMenuItemsPanel = new JPanel();
        chooseMenuItemsPanel.setBorder(BorderFactory.createTitledBorder(labels[1][0]));
        foodLabel = new JLabel("Food");
        foodComboBox = new JComboBox<>();
        foodComboBox.addItem("-------- Select the food --------");
        beverageLabel = new JLabel("Beverage");
        beverageComboBox = new JComboBox<>();
        beverageComboBox.addItem("-------- Select the beverage --------");
        orderStatusPanel = new JPanel();
        waitingOrdersPanel = new JPanel();
        waitingOrdersPanel.setLayout(new BorderLayout());
        servingOrdersPanel = new JPanel();
        servingOrdersPanel.setLayout(new BorderLayout());
        orderStatusPanel.setLayout(new GridLayout(1, 2, 50, 10));
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder(labels[2][0]));
        waitingOrders = new JLabel("Orders with waiting state (No orders available to prepare)");
        waitingOrders.setHorizontalAlignment(JLabel.CENTER);
        waitingOrdersTextArea = new JTextArea(10, 20);
        waitingOrdersTextArea.setEditable(false);
        servingOrders = new JLabel("Orders with served state (No orders available to serve)");
        servingOrders.setHorizontalAlignment(JLabel.CENTER);
        servingOrdersTextArea = new JTextArea(10, 20);
        servingOrdersTextArea.setEditable(false);
        scrollableWaitingOrdersTextArea = new JScrollPane(waitingOrdersTextArea);
        scrollableWaitingOrdersTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableServingOrdersTextArea = new JScrollPane(servingOrdersTextArea);
        scrollableServingOrdersTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        northPanel.setLayout(new BorderLayout());
        customerDetailsPanel.setLayout(new GridLayout(1, 3));

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
        customerDetailsPanel.add(customerNamePanel);
        customerDetailsPanel.add(tablePanel);
        customerDetailsPanel.add(mealPanel);

        customerNameTextArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        tableNumberTextArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        chooseMenuItemsPanel.add(foodLabel);
        chooseMenuItemsPanel.add(foodComboBox);
        chooseMenuItemsPanel.add(beverageLabel);
        chooseMenuItemsPanel.add(beverageComboBox);

        orderStatusPanel.setBorder(BorderFactory.createTitledBorder(labels[2][0]));
        waitingOrdersPanel.add(waitingOrders, BorderLayout.NORTH);
        waitingOrdersPanel.add(scrollableWaitingOrdersTextArea, BorderLayout.SOUTH);
        servingOrdersPanel.add(servingOrders, BorderLayout.NORTH);
        servingOrdersPanel.add(scrollableServingOrdersTextArea, BorderLayout.SOUTH);
        orderStatusPanel.add(waitingOrdersPanel);
        orderStatusPanel.add(servingOrdersPanel);

        northPanel.add(customerDetailsPanel, BorderLayout.NORTH);
        northPanel.add(chooseMenuItemsPanel, BorderLayout.CENTER);
        northPanel.add(orderStatusPanel, BorderLayout.SOUTH);

        centrePanel = new JPanel();

        southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createTitledBorder(labels[4][0]));
        enterDataButton = new JButton("Enter Data");
        displayChoicesButton = new JButton("Display Choices");
        displayOrderButton = new JButton("Display Order");
        prepareButton = new JButton("Prepare");
        billButton = new JButton("Bill");
        clearDisplayButton = new JButton("Clear Display");
        quitButton = new JButton("Exit");
//        prepareButton.setEnabled(false);
        billButton.setEnabled(false);

        southPanel.setLayout(new GridLayout(1, 7, 10, 10));
        southPanel.add(enterDataButton);
        southPanel.add(displayChoicesButton);
        southPanel.add(displayOrderButton);
        southPanel.add(prepareButton);
        southPanel.add(billButton);
        southPanel.add(clearDisplayButton);
        southPanel.add(quitButton);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centrePanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public JButton getBtnBill() {
        return billButton;
    }

    public JButton getBtnClear() {
        return clearDisplayButton;
    }

    public JButton getBtnDisplayChoice() {
        return displayChoicesButton;
    }

    public JButton getBtnDisplayOrder() {
        return displayOrderButton;
    }

    public JButton getBtnEnter() {
        return enterDataButton;
    }

    public JButton getBtnPrepare() {
        return prepareButton;
    }

    public JButton getBtnExit() {
        return quitButton;
    }

    public JComboBox<String> getCboxFood() {
        return foodComboBox;
    }

    public JComboBox<String> getCboxBeverage() {
        return beverageComboBox;
    }

    public JRadioButton getRadBreakfast() {
        return breakfastRadioButton;
    }

    public JRadioButton getRadDinner() {
        return lunchRadioButton;
    }

    public JRadioButton getRadLunch() {
        return dinnerRadioButton;
    }

    public JTextArea getTxtCustomerName() {
        return customerNameTextArea;
    }

    public JTextArea getTxtTable() {
        return tableNumberTextArea;
    }

    public JScrollPane getScrollServed() {
        return scrollableServingOrdersTextArea;
    }

    public JScrollPane getScrollWaiting() {
        return scrollableWaitingOrdersTextArea;
    }

    public JTable getTable(OrderState state) {
        return state == OrderState.WAITING ? waitingTable : servedTable;
    }

    public ButtonGroup getBgrpMealType() {
        return radioButtonGroup;
    }

    public void buildTable(OrderState state, JTable table) { 
        switch (state) {
            case WAITING:
                if (scrollableWaitingOrdersTextArea != null) {
                    waitingOrdersPanel.remove(scrollableWaitingOrdersTextArea);
                }

                scrollableWaitingOrdersTextArea = new JScrollPane(table);
                scrollableWaitingOrdersTextArea.setPreferredSize(new Dimension(0, 0));
                waitingOrdersPanel.add(scrollableWaitingOrdersTextArea);
                waitingTable = table;
                break;
            case SERVED:
                if (scrollableServingOrdersTextArea != null) {
                    servingOrdersPanel.remove(scrollableServingOrdersTextArea);
                }

                scrollableServingOrdersTextArea = new JScrollPane(table);
                scrollableServingOrdersTextArea.setPreferredSize(new Dimension(0, 0));
                servingOrdersPanel.add(scrollableServingOrdersTextArea);
                servedTable = table;
                break;
        }

        revalidate();
        repaint();
    }
}
