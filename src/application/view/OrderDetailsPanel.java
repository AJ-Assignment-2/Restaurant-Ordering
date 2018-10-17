package application.view;

import application.model.menuitem.MenuItemTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsPanel extends JPanel implements ObservableRestaurantView{
    private List<RestaurantViewObserver> observers;

    private JScrollPane tableContainer;
    private JTable orderItemDetailsTable;

    public OrderDetailsPanel() {
        observers = new ArrayList<>();

        orderItemDetailsTable = new JTable(new MenuItemTableModel(new ArrayList<>()));
        tableContainer = new JScrollPane(orderItemDetailsTable);
        tableContainer.setPreferredSize(new Dimension(800, 400));

        add(tableContainer);
    }

    public JTable getOrderItemDetailsTable() {
        return orderItemDetailsTable;
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
