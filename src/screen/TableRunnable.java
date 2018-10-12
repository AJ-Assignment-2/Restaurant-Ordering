package screen;

import java.awt.Color;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JTable;
import model.Order.Order;
import model.Order.OrderState;
import model.Order.OrderTableModel;

public class TableRunnable implements Runnable {

    private final Views view;
    private final Model model;

    public TableRunnable(Views view, Model model) {
        this.view = view;
        this.model = model;
    }

    public Views getView() {
        return view;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void run() {
        new Thread(() -> {
            while (getView() != null) {
                try {
                    List<Order> orders = getModel().getOrders();

                    for (OrderState state : OrderState.values()) {
                        OrderTableModel model = new OrderTableModel(orders.stream().filter(o -> o.getState() == state).collect(Collectors.toList()));

                        if (getView().getTable(state) != null) {
                            OrderTableModel m = (OrderTableModel) getView().getTable(state).getModel();
                            if (m.getOrders().equals(model.getOrders())) {
                                continue;
                            }
                        }

                        if (!model.getOrders().isEmpty()) {
                            JTable table = new JTable(model);
                            table.setGridColor(Color.GRAY);
                            getView().buildTable(state, table);
                        }
                    }

                    Thread.sleep(250L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TableRunnable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

}
