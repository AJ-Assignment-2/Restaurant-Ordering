package screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JTable;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import model.Order.Order;
import model.Order.OrderState;
import model.Order.OrderTableModel;

public class Controller {

    private final Views view;
    private final Model model;

    public Controller(Views view, Model model) {
        this.view = view;
        this.model = model;
        new TableRunnable(view, model).run();
        register();
    }

    public Views getView() {
        return view;
    }

    public Model getModel() {
        return model;
    }

    private void register() {
        getView().getBtnEnter().addActionListener(new EnterDataButtonListener());
        getView().getBtnClear().addActionListener(new ClearDisplayListener());
        getView().getBtnPrepare().addActionListener(new PrepareButtonListener());
        getView().getBtnExit().addActionListener(new ExitButtonListener());

        getView().getRadBreakfast().addActionListener(new MealTypeListener(MenuItemType.BREAKFAST));
        getView().getRadLunch().addActionListener(new MealTypeListener(MenuItemType.LUNCH));
        getView().getRadDinner().addActionListener(new MealTypeListener(MenuItemType.DINNER));
    }

    private class EnterDataButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Order order = new Order();
            order.setCustomerName(getView().getTxtCustomerName().getText());
            order.setTableNumber(Integer.valueOf(getView().getTxtTable().getText()));
            order.addItem((MenuItem) getView().getCboxFood().getSelectedItem());
            order.addItem((MenuItem) getView().getCboxBeverage().getSelectedItem());
            order.setState(OrderState.WAITING);
            getModel().addOrder(order);
        }
    }

    private class ClearDisplayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            getView().getBgrpMealType().clearSelection();
            getView().getCboxFood().removeAllItems();
            getView().getCboxBeverage().removeAllItems();
            getView().getCboxFood().addItem("-------- Select Food --------");
            getView().getCboxBeverage().addItem("-------- Select Beverage --------");
            getView().getContentPane().invalidate();
            getView().getContentPane().validate();
            getView().getBtnClear().setEnabled(false);
        }
    }

    private class PrepareButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = getView().getTable(OrderState.WAITING);
            OrderTableModel model = (OrderTableModel) table.getModel();
            getModel().findOrder(model.getOrder(table.getSelectedRow()).getTableNumber()).setState(OrderState.SERVED);
        }

    }

    private class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class MealTypeListener implements ActionListener {

        private final MenuItemType type;

        public MealTypeListener(MenuItemType type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getView().getCboxFood().removeAllItems();

            getView().getCboxFood().setModel(new DefaultComboBoxModel(getModel().getItems(type, MenuItemCategory.FOOD).toArray()));
            getView().getCboxFood().setRenderer(new MenuItemNameCellRenderer());

            getView().getCboxBeverage().setModel(new DefaultComboBoxModel(getModel().getItems(type, MenuItemCategory.BEVERAGE).toArray()));
            getView().getCboxBeverage().setRenderer(new MenuItemNameCellRenderer());
        }

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
