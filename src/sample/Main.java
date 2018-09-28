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
import services.OrderService.OrderAccessor;

import java.util.List;
import java.util.logging.Level;
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

        for (MenuItem item : menuItemAccessor.readMenuItems()) {
            testOrder.addItem(item);
            testOrder.addItem(item);
        }
        OrderAccessor orderAccessor = new OrderAccessor();
        testOrder.setCustomerName("Lionel");
        testOrder.setTableNumber(4);
        testOrder.setState(OrderState.WAITING);
        orderAccessor.createOrder(testOrder);
        List<Order> orders = orderAccessor.getAllOrders();

        System.out.println("test");
        launch(args);


    }
}
