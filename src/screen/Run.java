package screen;

public class Run {

    public static void main(String[] args) {
        Controller controller = new Controller(new Views(), new Model());
        controller.getView().setTitle("Restaurant Order");
        controller.getView().setSize(1350,700);
        controller.getView().setVisible(true);

    }
}
