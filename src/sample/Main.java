package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MenuItem.MenuItem;
import model.Order.Order;
import model.Order.OrderState;
import services.MenuItemService.MenuItemAccessor;
import java.util.logging.Logger;

public class Main extends Application {
    private static Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        MenuItemAccessor menuItemAccessor = new MenuItemAccessor();
        Order testOrder = new Order();

        // Add two of each menu item to the test order.
        for (MenuItem item : menuItemAccessor.readMenuItems()) {
            testOrder.addItem(item);
            testOrder.addItem(item);
        }

        testOrder.setCustomerName("Lionel");
        testOrder.setTableNumber(4);
        testOrder.setState(OrderState.WAITING);

        // The test order now contains two of each menu item.
        System.out.println("Order for: " + testOrder.getCustomerName() + " (table number: " + testOrder.getTableNumber() + ")");
        testOrder.getMenuItemSelections().entrySet().forEach((entry) -> {
            System.out.println("Customer requires " + entry.getValue() + " of the: " + entry.getKey().getName());
        });

        launch(args);
    }
}
