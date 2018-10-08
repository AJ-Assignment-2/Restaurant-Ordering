package RestaurantOrderScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Imanuel
 */
public class RestaurantOrderController {

    private RestaurantOrderView restaurantOrderView;
    private RestaurantOrderModel restaurantOrderModel;

    public RestaurantOrderController(RestaurantOrderView restaurantOrderView, RestaurantOrderModel restaurantOrderModel) {
        this.restaurantOrderModel = restaurantOrderModel;
        this.restaurantOrderView = restaurantOrderView;

        this.restaurantOrderView.addMenuAboutListener(new MenuAboutListener());
        this.restaurantOrderView.addBreakfastRadioButtonListener(new BreakfastRadioButtonListener());
        this.restaurantOrderView.addLunchRadioButtonListener(new LunchRadioButtonListener());
        this.restaurantOrderView.addDinnerRadioButtonListener(new DinnerRadioButtonListener());
        this.restaurantOrderView.addEnterDataButtonListener(new EnterDataButtonListener());
        this.restaurantOrderView.addDisplayChoicesButtonListener(new DisplayChoicesButtonListener());
        this.restaurantOrderView.addDisplayOrderButtonListener(new DisplayOrderButtonListener());
        this.restaurantOrderView.addPrepareButtonListener(new PrepareButtonListener());
        this.restaurantOrderView.addBillButtonListener(new BillButtonListener());
        this.restaurantOrderView.addClearDisplayButtonListener(new ClearButtonListener());
        this.restaurantOrderView.addQuitButtonListener(new QuitSystem());
    }

    class MenuAboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            restaurantOrderView.showMessageDialog("", "About Us");
        }
    }

    class BreakfastRadioButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class LunchRadioButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class DinnerRadioButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class EnterDataButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }

    }

    class DisplayChoicesButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String chosenFood = restaurantOrderView.getChosenFood();
            String chosenBeverage = restaurantOrderView.getChosenBeverage();
            if (!chosenFood.equals("-------- Select the food --------") || !chosenBeverage.equals("-------- Select the beverage --------")) {

            } else {
                restaurantOrderView.showErrorDialog("Please select food and beverage", "Select Menu");
            }
            System.out.println("CHOSEN MENU: " + chosenFood);
        }

    }

    class DisplayOrderButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class PrepareButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class BillButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class ClearButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            restaurantOrderView.setResetScreen();
        }

    }

    class QuitSystem implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }
}
