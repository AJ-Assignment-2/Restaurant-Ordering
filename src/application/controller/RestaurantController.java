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
import java.util.ArrayList;
import java.util.List;

public class RestaurantController implements RestaurantModelObserver, RestaurantViewObserver {

    private RestaurantModel restaurantModel;
    private JPanel restaurantView;

    private CommandPanel commandPanel;
    private CustomerDetailsPanel customerDetailsPanel;
    private MenuItemSelectionPanel menuItemSelectionPanel;
    private OrderStatusPanel orderStatusPanel;
    private OrderDetailsPanel orderDetailsPanel;

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
    }

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
        if (!Validation.isAlpha(customerName)) {
            JOptionPane.showMessageDialog(restaurantView, "Please enter a valid name", "Invalid Customer Name", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validation.isNumeric(tableNumber)) {
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

    @Override
    public void displayChoicesButtonPressed() {
        List<MenuItem> focusedItems = new ArrayList<>();
        focusedItems.add((MenuItem) menuItemSelectionPanel.getFoodComboBox().getSelectedItem());
        focusedItems.add((MenuItem) menuItemSelectionPanel.getBeverageComboBox().getSelectedItem());

        JTable orderItemDetailsTable = orderDetailsPanel.getOrderItemDetailsTable();
        ((MenuItemTableModel) orderItemDetailsTable.getModel()).setMenuItems(focusedItems);
        ((MenuItemTableModel) orderItemDetailsTable.getModel()).fireTableDataChanged();

        ColumnWidthUtil.adjustColumnWidths(orderItemDetailsTable, new int[]{0});
    }

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

        ((MenuItemTotalsTableModel) orderItemDetailsTable.getModel())
                .setMenuItems(selectedOrderMenuItems);

        ((MenuItemTotalsTableModel) orderItemDetailsTable.getModel()).fireTableDataChanged();

        ColumnWidthUtil.adjustColumnWidths(orderItemDetailsTable, new int[]{0});
    }

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
    }

    @Override
    public void clearDisplayButtonPressed() {
        MenuItemTableModel menuItemTableModel = (MenuItemTableModel) orderDetailsPanel.getOrderItemDetailsTable().getModel();
        menuItemTableModel.setMenuItems(new ArrayList<>());
        menuItemTableModel.fireTableDataChanged();

        customerDetailsPanel.getCustomerNameTextArea().setText("");
        customerDetailsPanel.getTableNumberTextArea().setText("");

        orderStatusPanel.getWaitingOrdersTable().clearSelection();
        orderStatusPanel.getServedOrdersTable().clearSelection();
        
        customerDetailsPanel.getButtonGroup().clearSelection();

//        commandPanel.getSubmitOrderButton().setEnabled(false);
//        commandPanel.getClearDisplayButton().setEnabled(false);
    }

    @Override
    public void quitButtonPressed() {
        System.exit(0);
    }

    @Override
    public void ordersUpdated(List<Order> orders) {

    }

    @Override
    public void mealTimeSelected(MenuItemType mealTime) {
        JComboBox beverageComboBox = menuItemSelectionPanel.getBeverageComboBox();
        JComboBox foodComboBox = menuItemSelectionPanel.getFoodComboBox();

        beverageComboBox.setModel(new DefaultComboBoxModel(restaurantModel.getMenuItems(mealTime, MenuItemCategory.BEVERAGE).toArray()));
        foodComboBox.setModel(new DefaultComboBoxModel(restaurantModel.getMenuItems(mealTime, MenuItemCategory.FOOD).toArray()));

        beverageComboBox.setRenderer(new MenuItemNameCellRenderer());
        foodComboBox.setRenderer(new MenuItemNameCellRenderer());
    }

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
