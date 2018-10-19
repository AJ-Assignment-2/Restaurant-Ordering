package application.view;

import application.model.RestaurantModelObserver;
import application.model.order.Order;
import application.model.order.OrderTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class of order status panel extends JPanel and implements observable restaurant view
 *
 */
public class OrderStatusPanel extends JPanel implements ObservableRestaurantView, RestaurantModelObserver {
    private List<RestaurantViewObserver> observers;

    private JPanel waitingOrdersPanel;
    private JPanel preparedOrdersPanel;

    private JLabel waitingOrdersLabel;
    private JLabel preparedOrdersLabel;
    private JTable waitingOrdersTable;
    private JTable servedOrdersTable;
    private JScrollPane scrollableWaitingOrdersContainer;
    private JScrollPane scrollableServedOrdersContainer;

    /**
     * A constructor of order status panel
     *
     */
    public OrderStatusPanel() {
        observers = new ArrayList<>();

        setLayout(new GridLayout(1, 2, 50, 10));
        setBorder(BorderFactory.createTitledBorder("Active Orders"));

        waitingOrdersPanel = new JPanel();
        preparedOrdersPanel = new JPanel();
        waitingOrdersPanel.setLayout(new BoxLayout(waitingOrdersPanel, BoxLayout.Y_AXIS));
        preparedOrdersPanel.setLayout(new BoxLayout(preparedOrdersPanel,BoxLayout.Y_AXIS));

        waitingOrdersTable = new JTable(new OrderTableModel(new ArrayList<>()));
        servedOrdersTable = new JTable(new OrderTableModel(new ArrayList<>()));

        waitingOrdersLabel = new JLabel("Orders that are waiting to be prepared");
        waitingOrdersLabel.setHorizontalAlignment(JLabel.CENTER);
        preparedOrdersLabel = new JLabel("Served orders waiting to be billed");
        preparedOrdersLabel.setHorizontalAlignment(JLabel.CENTER);
        scrollableWaitingOrdersContainer = new JScrollPane(waitingOrdersTable);
        //scrollableWaitingOrdersContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableServedOrdersContainer = new JScrollPane(servedOrdersTable);
        //scrollableServedOrdersContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrollableServedOrdersContainer.setPreferredSize(new Dimension(400, 150));
        scrollableWaitingOrdersContainer.setPreferredSize(new Dimension(400, 150));

        waitingOrdersPanel.add(waitingOrdersLabel);
        waitingOrdersPanel.add(scrollableWaitingOrdersContainer);
        preparedOrdersPanel.add(preparedOrdersLabel);
        preparedOrdersPanel.add(scrollableServedOrdersContainer);

        add(waitingOrdersPanel);
        add(preparedOrdersPanel);
    }

    /**
     * A method to update order
     *
     * @param orders list of order
     */
    @Override
    public void ordersUpdated(List<Order> orders) {
        List<Order> waitingOrders = new ArrayList<>();
        List<Order> servedOrders = new ArrayList<>();

        orders.stream().forEach(order -> {
            switch (order.getState()) {
                case WAITING:
                    waitingOrders.add(order);
                    break;
                case SERVED:
                    servedOrders.add(order);
                    break;
            }
        });

        ((OrderTableModel)waitingOrdersTable.getModel()).setOrders(waitingOrders);
        ((OrderTableModel)waitingOrdersTable.getModel()).fireTableDataChanged();

        ((OrderTableModel)servedOrdersTable.getModel()).setOrders(servedOrders);
        ((OrderTableModel)servedOrdersTable.getModel()).fireTableDataChanged();
    }

    /**
     * A method to get served orders table
     *
     * @return JTable server orders table 
     */
    public JTable getServedOrdersTable() {
        return servedOrdersTable;
    }

    /**
     * A method to get waiting orders table
     *
     * @return JTable waiing orders table 
     */
    public JTable getWaitingOrdersTable() {
        return waitingOrdersTable;
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
     * A method to add restaurant view observer
     * @param observer observer of restaurant view
     * 
     */
    @Override
    public void removeRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.add(observer);
    }
}
