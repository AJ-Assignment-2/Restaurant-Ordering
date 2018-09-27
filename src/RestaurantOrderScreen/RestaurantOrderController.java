
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

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
    
    public RestaurantOrderController(RestaurantOrderView restaurantOrderView, RestaurantOrderModel restaurantOrderModel){
        this.restaurantOrderModel=restaurantOrderModel;
        this.restaurantOrderView=restaurantOrderView;
        
        this.restaurantOrderView.addMenuAboutListener(new MenuAboutListener());
        this.restaurantOrderView.addEnterDataButtonListener(new EnterDataButtonListener());
        this.restaurantOrderView.addDisplayChoicesButtonListener(new DisplayChoicesButtonListener());
        this.restaurantOrderView.addDisplayOrderButtonListener(new DisplayOrderButtonListener());
        this.restaurantOrderView.addPrepareButtonListener(new PrepareButtonListener());
        this.restaurantOrderView.addBillButtonListener(new BillButtonListener());   
        this.restaurantOrderView.addClearDisplayButtonListener(new ClearButtonListener());
        this.restaurantOrderView.addQuitButtonListener(new QuitSystem());
    }
    
    class MenuAboutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            restaurantOrderView.showMessageDialog("","About Us");
        }
    }
    
    class EnterDataButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class DisplayChoicesButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class DisplayOrderButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class PrepareButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class BillButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class ClearButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            restaurantOrderView.setResetScreen();
        }
        
    }
    
    class QuitSystem implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }
}
