package application.view;

import application.model.menuitem.MenuItemTableModel;
import application.model.menuitem.MenuItemTotalsTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class of oreder details panel extends JPanel implements observable of restaurant view
 */
public class OrderDetailsPanel extends JPanel implements ObservableRestaurantView{
    private List<RestaurantViewObserver> observers;

    private JScrollPane tableContainer;
    private JTable orderItemDetailsTable;

    /**
     * A constructor of order details panel
     */
    public OrderDetailsPanel() {
        observers = new ArrayList<>();

        orderItemDetailsTable = new JTable(new MenuItemTotalsTableModel(new ArrayList<>()));
        tableContainer = new JScrollPane(orderItemDetailsTable);
        tableContainer.setPreferredSize(new Dimension(950, 300));

        add(tableContainer);
    }
    
    /**
     * A method to get table of order item
     * @param JTable order item table
     */
    public JTable getOrderItemDetailsTable() {
        return orderItemDetailsTable;
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
        observers.add(observer);
    }
}
