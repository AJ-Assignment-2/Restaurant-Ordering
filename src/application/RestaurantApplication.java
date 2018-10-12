package application;

import application.controller.RestaurantController;
import application.model.RestaurantModel;

import javax.swing.*;
import java.awt.*;

public class RestaurantApplication {
    public static void main(String[] args) {
        JPanel restaurantView = new JPanel();
        RestaurantModel restaurantModel = new RestaurantModel();
        RestaurantController restaurantController = new RestaurantController(restaurantModel, restaurantView);

        JFrame frame = new JFrame("Restaurant Application");
        frame.setPreferredSize(new Dimension(1000, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(restaurantView);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
