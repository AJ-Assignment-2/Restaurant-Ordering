package RestaurantOrderScreen;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemType;
import services.MenuItemService.MenuItemAccessor;

public class Model {

    private final MenuItemAccessor accessor;
    private final List<MenuItem> items;

    public Model() {
        this.accessor = new MenuItemAccessor();
        this.items = new ArrayList<>(accessor.readMenuItems());
    }

    public MenuItemAccessor getAccessor() {
        return this.accessor;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public List<MenuItem> getItems(MenuItemType type) {
        return getItems().stream().filter(item -> item.getType() == type).collect(Collectors.toList());
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}
