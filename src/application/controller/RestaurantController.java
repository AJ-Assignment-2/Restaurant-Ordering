package application.controller;

import application.model.OrderTableModel;
import application.model.RestaurantModel;
import application.model.RestaurantModelObserver;
import application.view.*;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import model.Order.Order;
import model.Order.OrderState;

import javax.swing.*;
import java.awt.*;
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

        initView();
    }

    private void initView() {
        restaurantView.setLayout(new BorderLayout());
        restaurantView.add(commandPanel, BorderLayout.SOUTH);
        restaurantView.add(customerDetailsPanel, BorderLayout.NORTH);
        restaurantView.add(menuItemSelectionPanel);

        // Create a container to fit two elements in the center
        JPanel rootOrderPanelContainer = new JPanel();
        JPanel orderPanelContainer = new JPanel();

        rootOrderPanelContainer.setLayout(new BoxLayout(rootOrderPanelContainer, BoxLayout.Y_AXIS));
        orderPanelContainer.add(orderStatusPanel);

        rootOrderPanelContainer.add(menuItemSelectionPanel);
        rootOrderPanelContainer.add(orderPanelContainer);

        restaurantView.add(rootOrderPanelContainer);

        restaurantModel.addRestaurantModelObserver(this);
        restaurantModel.addRestaurantModelObserver(orderStatusPanel);

        commandPanel.addRestaurantViewObserver(this);
        customerDetailsPanel.addRestaurantViewObserver(this);
        menuItemSelectionPanel.addRestaurantViewObserver(this);
        orderStatusPanel.addRestaurantViewObserver(this);

    }

    @Override
    public void enterDataButtonPressed() {
        Order order = restaurantModel.getOrder(Integer.parseInt(customerDetailsPanel.getTableNumberTextArea().getText()));

        if (order == null) {
            String customerName = customerDetailsPanel.getCustomerNameTextArea().getText();
            String tableNumber = customerDetailsPanel.getTableNumberTextArea().getText();

            order = new Order();
            order.setCustomerName(customerName);
            order.setState(OrderState.WAITING);
            order.setTableNumber(Integer.parseInt(tableNumber));
        }

        MenuItem selectedFood = (MenuItem)menuItemSelectionPanel.getFoodComboBox().getModel().getSelectedItem();
        MenuItem selectedBeverage = (MenuItem)menuItemSelectionPanel.getBeverageComboBox().getModel().getSelectedItem();

        order.addItem(selectedBeverage);
        order.addItem(selectedFood);

        restaurantModel.storeOrder(order);
    }

    @Override
    public void displayChoicesButtonPressed() {

    }

    @Override
    public void displayOderButtonPressed() {

    }

    @Override
    public void prepareButtonPressed() {
        JTable orderTable = orderStatusPanel.getWaitingOrdersTable();
        OrderTableModel orderTableModel = (OrderTableModel)orderTable.getModel();
        Order selectedOrder = orderTableModel.getOrder(orderTable.getSelectedRow());
        selectedOrder.setState(OrderState.SERVED);
        restaurantModel.storeOrder(selectedOrder);
    }

    @Override
    public void billButtonPressed() {
        JTable orderTable = orderStatusPanel.getServedOrdersTable();
        OrderTableModel orderTableModel = (OrderTableModel)orderTable.getModel();
        Order selectedOrder = orderTableModel.getOrder(orderTable.getSelectedRow());
        selectedOrder.setState(OrderState.BILLED);
        restaurantModel.storeOrder(selectedOrder);
    }

    @Override
    public void clearDisplayButtonPressed() {

    }

    @Override
    public void quitButtonPressed() {

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
            if (value instanceof model.MenuItem.MenuItem) {
                model.MenuItem.MenuItem item = (MenuItem) value;
                setText(item.getName());
            }
            return this;
        }
    }
}
