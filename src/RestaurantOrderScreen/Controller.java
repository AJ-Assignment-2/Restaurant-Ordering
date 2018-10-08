package RestaurantOrderScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import model.Order.Order;

public class Controller {
    
    private final View view;
    private final Model model;
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        register();
    }
    
    public View getView() {
        return view;
    }
    
    public Model getModel() {
        return model;
    }
    
    private void register() {
        getView().getBtnEnter().addActionListener(new EnterDataButtonListener());
        getView().getBtnExit().addActionListener(new ExitButtonListener());
        
        getView().getRadBreakfast().addActionListener(new MealTypeListener(MenuItemType.BREAKFAST));
        getView().getRadLunch().addActionListener(new MealTypeListener(MenuItemType.LUNCH));
        getView().getRadDinner().addActionListener(new MealTypeListener(MenuItemType.DINNER));
    }
    
    private class EnterDataButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Order order = new Order();
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
            
            getModel().getItems(type).stream().forEach((item) -> {
                if (item.getCategory() == MenuItemCategory.FOOD) {
                    getView().getCboxFood().addItem(item.getName());
                } else {
                    getView().getCboxBeverage().addItem(item.getName());
                }
            });
        }
        
    }
    
}
