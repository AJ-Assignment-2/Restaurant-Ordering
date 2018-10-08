package RestaurantOrderScreen;

/**
 *
 * @author Imanuel
 */
public class Run {

    public static void main(String[] args) {
        Controller controller = new Controller(new View(), new Model());
        controller.getView().setTitle("Restaurant Order");
        controller.getView().setVisible(true);
    }
}
