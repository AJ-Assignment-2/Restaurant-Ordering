package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MenuItem.MenuItem;
import services.MenuItemService.MenuItemAccessor;

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
        for (MenuItem item : menuItemAccessor.readMenuItems()) {
            LOGGER.log(Level.INFO, "Menu Item: " + item.getId());
            LOGGER.log(Level.INFO, "Name: " + item.getName());
            LOGGER.log(Level.INFO, "This is a " + item.getCategory().toString() + " food.");
        }
        launch(args);

    }
}
