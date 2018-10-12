package application.view;

import application.model.RestaurantModelObserver;
import model.Order.Order;
import application.model.order.OrderTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusPanel extends JPanel implements ObservableRestaurantView, RestaurantModelObserver {
    private List<RestaurantViewObserver> observers;

    private JPanel waitingOrdersPanel;
    private JPanel preparedOrdersPanel;

    private JLabel waitingOrdersLabel;
    private JLabel preparedOrdersLabel;
    private JTable waitingOrdersTable;
    private JTable servedOrdersTable;
    private JScrollPane scrollableWaitingOrdersTextArea;
    private JScrollPane scrollableServingOrdersTextArea;

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
        scrollableWaitingOrdersTextArea = new JScrollPane(waitingOrdersTable);
        scrollableWaitingOrdersTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableServingOrdersTextArea = new JScrollPane(servedOrdersTable);
        scrollableServingOrdersTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        waitingOrdersPanel.add(waitingOrdersLabel);
        waitingOrdersPanel.add(waitingOrdersTable);
        preparedOrdersPanel.add(preparedOrdersLabel);
        preparedOrdersPanel.add(servedOrdersTable);

        add(waitingOrdersPanel);
        add(preparedOrdersPanel);
    }

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

    public JTable getServedOrdersTable() {
        return servedOrdersTable;
    }

    public JTable getWaitingOrdersTable() {
        return waitingOrdersTable;
    }

    @Override
    public void addRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeRestaurantViewObserver(RestaurantViewObserver observer) {
        observers.add(observer);
    }
}
