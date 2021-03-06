package application.controller;

import application.model.ColumnWidthUtil;
import application.model.menuitem.*;
import application.model.menuitem.MenuItem;
import application.model.order.OrderTableModel;
import application.model.RestaurantModel;
import application.model.RestaurantModelObserver;
import application.model.order.Order;
import application.model.order.OrderState;
import application.utilities.Validation;
import application.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A class of restaurant screen controller implements restaurant model and view interface
 *
 */
public class RestaurantController implements RestaurantModelObserver, RestaurantViewObserver {

    private RestaurantModel restaurantModel;
    private JPanel restaurantView;

    private CommandPanel commandPanel;
    private CustomerDetailsPanel customerDetailsPanel;
    private MenuItemSelectionPanel menuItemSelectionPanel;
    private OrderStatusPanel orderStatusPanel;
    private OrderDetailsPanel orderDetailsPanel;

    /**
     * A constructor of restuarant controller
     * 
     * @param model model of restaurant 
     * @param view JPanel of restaurant view
     *
     */
    public RestaurantController(RestaurantModel model, JPanel view) {
        this.restaurantModel = model;
        this.restaurantView = view;
        this.commandPanel = new CommandPanel();
        this.customerDetailsPanel = new CustomerDetailsPanel();
        this.menuItemSelectionPanel = new MenuItemSelectionPanel();
        this.orderStatusPanel = new OrderStatusPanel();
        this.orderDetailsPanel = new OrderDetailsPanel();

        initView();
    }

    /**
     * A method to execute the view of standalone version screen
     *
     */
    private void initView() {
        restaurantView.setLayout(new BorderLayout());
        restaurantView.add(commandPanel, BorderLayout.SOUTH);
        restaurantView.add(customerDetailsPanel, BorderLayout.NORTH);
        restaurantView.add(menuItemSelectionPanel);

        // Create a container to fit three panels in the center
        JPanel rootOrderPanelContainer = new JPanel();

        rootOrderPanelContainer.setLayout(new BoxLayout(rootOrderPanelContainer, BoxLayout.Y_AXIS));

        rootOrderPanelContainer.add(menuItemSelectionPanel);
        rootOrderPanelContainer.add(orderStatusPanel);
        rootOrderPanelContainer.add(orderDetailsPanel);

        restaurantView.add(rootOrderPanelContainer);

        restaurantModel.addRestaurantModelObserver(this);
        restaurantModel.addRestaurantModelObserver(orderStatusPanel);

        commandPanel.addRestaurantViewObserver(this);
        customerDetailsPanel.addRestaurantViewObserver(this);
        menuItemSelectionPanel.addRestaurantViewObserver(this);
        orderStatusPanel.addRestaurantViewObserver(this);
        orderDetailsPanel.addRestaurantViewObserver(this);

        commandPanel.getPrepareButton().setEnabled(false);
        commandPanel.getBillButton().setEnabled(false);

        /*
         * Handle all button enable and disbale events here
         */
        orderStatusPanel.getServedOrdersTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commandPanel.getPrepareButton().setEnabled(false);
            }
        });

        orderStatusPanel.getWaitingOrdersTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commandPanel.getPrepareButton().setEnabled(true);
            }
        });

        orderStatusPanel.getServedOrdersTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commandPanel.getBillButton().setEnabled(true);
            }
        });

    }

    /**
     * A method to execute enter data button when it pressed
     *
     */
    @Override
    public void enterDataButtonPressed() {
        String customerName = customerDetailsPanel.getCustomerNameTextArea().getText();
        String tableNumber = customerDetailsPanel.getTableNumberTextArea().getText();

        // Check if inputs are empty
        if (customerName.isEmpty() || tableNumber.isEmpty()) {
            JOptionPane.showMessageDialog(restaurantView, "Please enter your name or your table number", "Incorrect Name or Table Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if data types are correct
        if (customerName.chars().allMatch(Character::isLetter)) {
            JOptionPane.showMessageDialog(restaurantView, "Please enter a valid (first) name", "Invalid Customer Name", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tableNumber.chars().allMatch(Character::isDigit)) {
            JOptionPane.showMessageDialog(restaurantView, "Please enter a valid table number", "Invalid Table Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if a meal type is selected
        if (customerDetailsPanel.getButtonGroup().getSelection() == null) {
            JOptionPane.showMessageDialog(restaurantView, "Please select a meal type", "Invalid Meal Type", JOptionPane.ERROR_MESSAGE);
            return;
        }

//        String dialogMessage = "Are you sure you wish to add these items to your order?";
//
//        int dialogResult = JOptionPane.showConfirmDialog(restaurantView, dialogMessage, "Confirm additional order item", JOptionPane.YES_NO_OPTION);
//
//        if (dialogResult == JOptionPane.NO_OPTION) return;

        Order order = restaurantModel.getOrder(Integer.parseInt(tableNumber));
        if (order == null) {
            order = new Order();
            order.setCustomerName(customerName);
            order.setState(OrderState.WAITING);
            order.setTableNumber(Integer.parseInt(tableNumber));
        }

        MenuItem selectedFood = (MenuItem) menuItemSelectionPanel.getFoodComboBox().getModel().getSelectedItem();
        MenuItem selectedBeverage = (MenuItem) menuItemSelectionPanel.getBeverageComboBox().getModel().getSelectedItem();

        order.addItem(selectedBeverage);
        order.addItem(selectedFood);

        restaurantModel.storeOrder(order);
        commandPanel.getClearDisplayButton().setEnabled(true);
    }

    /**
     * A method to display choices button when it pressed
     *
     */
    @Override
    public void displayChoicesButtonPressed() {
        if (menuItemSelectionPanel.getFoodComboBox().getSelectedItem() == null
                && menuItemSelectionPanel.getBeverageComboBox().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(restaurantView, "Please select a food to display", "Invalid Order Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<MenuItem> focusedItems = new ArrayList<>();
        focusedItems.add((MenuItem) menuItemSelectionPanel.getFoodComboBox().getSelectedItem());
        focusedItems.add((MenuItem) menuItemSelectionPanel.getBeverageComboBox().getSelectedItem());

        JTable orderItemDetailsTable = orderDetailsPanel.getOrderItemDetailsTable();
      
        orderItemDetailsTable.setModel(new MenuItemTableModel(focusedItems));
        ((MenuItemTableModel)orderItemDetailsTable.getModel()).fireTableDataChanged();

        ColumnWidthUtil.adjustColumnWidths(orderItemDetailsTable, new int[]{0});
    }

    /**
     * A method to display order button when it pressed
     *
     */
    @Override
    public void displayOrderButtonPressed() {
        if (orderStatusPanel.getWaitingOrdersTable().getSelectedRow() == -1
                && orderStatusPanel.getServedOrdersTable().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(restaurantView, "Please select a order to display", "Invalid Order Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // assign values depending on table that was clicked
        JTable table = null;
        OrderTableModel model = null;
        Order order = null;
        if (orderStatusPanel.getWaitingOrdersTable().getSelectedRow() != -1) {
            table = orderStatusPanel.getWaitingOrdersTable();
            model = (OrderTableModel) table.getModel();
            order = model.getOrder(table.getSelectedRow());
        } else if (orderStatusPanel.getServedOrdersTable().getSelectedRow() != -1) {
            table = orderStatusPanel.getServedOrdersTable();
            model = (OrderTableModel) table.getModel();
            order = model.getOrder(table.getSelectedRow());
        }

        JTable orderItemDetailsTable = orderDetailsPanel.getOrderItemDetailsTable();

        List<MenuItem> selectedOrderMenuItems = new ArrayList<>();

        for (MenuItem item : order.getMenuItemSelections().keySet()) {
            int quantity = order.getMenuItemSelections().get(item);

            for (int i = 0; i < quantity; i++) {
                selectedOrderMenuItems.add(item);
            }
        }

        orderItemDetailsTable.setModel(new MenuItemTotalsTableModel(selectedOrderMenuItems));

        ((MenuItemTotalsTableModel) orderItemDetailsTable.getModel()).fireTableDataChanged();

        ColumnWidthUtil.adjustColumnWidths(orderItemDetailsTable, new int[]{0});
    }

    /**
     * A method to execute prepare button when it pressed
     *
     */
    @Override
    public void prepareButtonPressed() {
        if (orderStatusPanel.getWaitingOrdersTable().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(restaurantView, "Please select a order to prepare", "Invalid Prepare Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dialogMessage = "Are you sure you wish to mark this order as prepared?";
        int result = JOptionPane.showConfirmDialog(restaurantView, dialogMessage, "Mark order as prepared", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.NO_OPTION) return;

        JTable orderTable = orderStatusPanel.getWaitingOrdersTable();
        OrderTableModel orderTableModel = (OrderTableModel) orderTable.getModel();
        Order selectedOrder = orderTableModel.getOrder(orderTable.getSelectedRow());
        selectedOrder.setState(OrderState.SERVED);
        restaurantModel.storeOrder(selectedOrder);
    }

    /**
     * A method to execute ordered bill when it pressed
     *
     */
    @Override
    public void billButtonPressed() {
        if (orderStatusPanel.getServedOrdersTable().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(restaurantView, "Please select a order to bill", "Invalid Bill Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dialogMessage = "Are you sure you want to mark this order as billed?";
        int dialogResult = JOptionPane.showConfirmDialog(restaurantView, dialogMessage, "Mark order as billed", JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.NO_OPTION) return;

        JTable orderTable = orderStatusPanel.getServedOrdersTable();
        OrderTableModel orderTableModel = (OrderTableModel) orderTable.getModel();
        Order selectedOrder = orderTableModel.getOrder(orderTable.getSelectedRow());
        selectedOrder.setState(OrderState.BILLED);
        restaurantModel.storeOrder(selectedOrder);
        commandPanel.getBillButton().setEnabled(false);
    }

    /**
     * A method to clear display button when it pressed
     *
     */
    @Override
    public void clearDisplayButtonPressed() {
        MenuItemTotalsTableModel menuItemTableModel = (MenuItemTotalsTableModel) orderDetailsPanel.getOrderItemDetailsTable().getModel();
        menuItemTableModel.setMenuItems(new ArrayList<>());
        menuItemTableModel.fireTableDataChanged();
        
        menuItemSelectionPanel.getBeverageComboBox().setModel(new DefaultComboBoxModel());
        menuItemSelectionPanel.getFoodComboBox().setModel(new DefaultComboBoxModel());

        customerDetailsPanel.getCustomerNameTextArea().setText("");
        customerDetailsPanel.getTableNumberTextArea().setText("");

        orderStatusPanel.getWaitingOrdersTable().clearSelection();
        orderStatusPanel.getServedOrdersTable().clearSelection();

        customerDetailsPanel.getButtonGroup().clearSelection();

        commandPanel.getPrepareButton().setEnabled(false);
        commandPanel.getBillButton().setEnabled(false);
    }

    /**
     * A method of pressed quit button when it pressed
     *
     */
    @Override
    public void quitButtonPressed() {
        System.exit(0);
    }

    /**
     * A method of updating orders
     *
     * @param orders list of order
     */
    @Override
    public void ordersUpdated(List<Order> orders) {

    }

    /**
     * A method of type of menu item
     *
     * @param mealTime type of menu item
     */
    @Override
    public void mealTimeSelected(MenuItemType mealTime) {
        JComboBox beverageComboBox = menuItemSelectionPanel.getBeverageComboBox();
        JComboBox foodComboBox = menuItemSelectionPanel.getFoodComboBox();

        beverageComboBox.setModel(new DefaultComboBoxModel(restaurantModel.getMenuItems(mealTime, MenuItemCategory.BEVERAGE).toArray()));
        foodComboBox.setModel(new DefaultComboBoxModel(restaurantModel.getMenuItems(mealTime, MenuItemCategory.FOOD).toArray()));

        beverageComboBox.setRenderer(new MenuItemNameCellRenderer());
        foodComboBox.setRenderer(new MenuItemNameCellRenderer());
    }

    /**
     * A class of menu item name cell render
     *
     */
    private class MenuItemNameCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof MenuItem) {
                MenuItem item = (MenuItem) value;
                setText(item.getName());
            }
            return this;
        }
    }
}
