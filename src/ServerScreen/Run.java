package ServerScreen;

import ReceptionistScreen.*;
import RestaurantOrderScreen.*;

/**
 *
 * @author Imanuel
 */
public class Run {
    public static void main(String[] args){
        ServerScreenView serverScreenView=new ServerScreenView();
        ServerScreenModel serverScreenModel=new ServerScreenModel();
        serverScreenView.setTitle("Server Side Screen");
        serverScreenView.setSize(750,400);
        serverScreenView.setVisible(true);
    }
}
