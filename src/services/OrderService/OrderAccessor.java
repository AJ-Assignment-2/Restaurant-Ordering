package services.OrderService;

import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import model.Order.Order;
import model.Order.OrderState;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderAccessor implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(OrderAccessor.class.getName());

    private static final String TABLE_NAME = "RestaurantOrder";

    private static final String COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_ID = "id";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            COLUMN_CUSTOMER_NAME + " VARCHAR(50) NOT NULL, " +
            COLUMN_STATE + " VARCHAR(50) NOT NULL )";

    private Connection connection;

    public OrderAccessor() {
        Connection attemptedConnection = null;
        try {
            attemptedConnection = DriverManager.getConnection("jdbc:derby:RestaurantOrderingDB;create=true");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        connection = attemptedConnection;

        createOrderTable();
        createOrderItemTable();
    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM RestaurantOrder";

        try {
            Statement statement = connection.createStatement();
            ResultSet orderResultSet = statement.executeQuery(sql);

            while (orderResultSet.next()) {
                Order order = new Order();
                order.setId(orderResultSet.getInt(COLUMN_ID));
                order.setCustomerName(orderResultSet.getString(COLUMN_CUSTOMER_NAME));

                String stateString = orderResultSet.getString(COLUMN_STATE);
                Arrays.stream(OrderState.values()).forEach(orderState -> {
                    if (orderState.toString().toLowerCase().compareTo(stateString.toLowerCase()) == 0) {
                        order.setState(orderState);
                    }
                });

                final String orderItemQuery = "SELECT RestaurantOrderItem.quantity, " +
                        "MenuItem.id, MenuItem.name, MenuItem.price, MenuItem.energy, MenuItem.protean, MenuItem.carbohydrates, " +
                        "MenuItem.fat, MenuItem.fibre, MenuItem.type, MenuItem.category FROM RestaurantOrderItem " +
                        "INNER JOIN MenuItem ON RestaurantOrderItem.itemId = MenuItem.id WHERE RestaurantOrderItem.orderId = " + order.getId();

                statement = connection.createStatement();
                ResultSet menuItemResultSet = statement.executeQuery(orderItemQuery);

                while (menuItemResultSet.next()) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setId(menuItemResultSet.getInt("id"));
                    menuItem.setName(menuItemResultSet.getString("name"));
                    menuItem.setPrice(menuItemResultSet.getInt("price"));
                    menuItem.setEnergy(menuItemResultSet.getInt("energy"));
                    menuItem.setProtean(menuItemResultSet.getDouble("protean"));
                    menuItem.setCarbohydrates(menuItemResultSet.getDouble("carbohydrates"));
                    menuItem.setFat(menuItemResultSet.getDouble("fat"));
                    menuItem.setFibre(menuItemResultSet.getDouble("fibre"));

                    String categoryString = menuItemResultSet.getString("category");
                    String typeString = menuItemResultSet.getString("type");

                    Arrays.stream(MenuItemCategory.values()).forEach(menuItemCategory -> {
                        if (menuItemCategory.toString().toLowerCase().compareTo(categoryString.toLowerCase()) == 0) {
                            menuItem.setCategory(menuItemCategory);
                        }
                    });

                    Arrays.stream(MenuItemType.values()).forEach(menuItemType -> {
                        if (menuItemType.toString().toLowerCase().compareTo(typeString.toLowerCase()) == 0) {
                            menuItem.setType(menuItemType);
                        }
                    });

                    int menuItemQuantity = menuItemResultSet.getInt("Quantity");
                    for (int i = 0; i < menuItemQuantity; i++) {
                        order.addItem(menuItem);
                    }
                }
                orders.add(order);
            }

        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
        }

        return orders;
    }

    @Override
    public void createOrder(Order order) {
        String orderSql = "INSERT INTO RestaurantOrder (" + COLUMN_CUSTOMER_NAME + ", " + COLUMN_STATE + ")" +
                " VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, order.getCustomerName());
            statement.setString(2, order.getState().toString());
            statement.execute();

            ResultSet keysFromInsert = statement.getGeneratedKeys();
            int insertedOrderId = 0;
            if (keysFromInsert.next()) {
                insertedOrderId = keysFromInsert.getInt(1);
                for (MenuItem menuItem : order.getMenuItemSelections().keySet() ) {
                    String orderItemSql = "INSERT INTO RestaurantOrderItem VALUES(" + menuItem.getId() +
                            ", " + insertedOrderId + ", " + order.getMenuItemSelections().get(menuItem) + ")";
                    statement = connection.prepareStatement(orderItemSql);
                    statement.execute();
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

    @Override
    public void deleteOrder(int id) {

    }

    private void createOrderTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Order table is probably already created: " + e.toString(), e);
        }
    }

    private void createOrderItemTable() {
        try {
            Statement statement = connection.createStatement();
            String orderItemTableSql = "CREATE TABLE RestaurantOrderItem (" +
                    "itemId INTEGER REFERENCES MenuItem(id), " +
                    "orderId INTEGER REFERENCES RestaurantOrder(id), " +
                    "quantity INTEGER )";
            statement.execute(orderItemTableSql);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "RestaurantOrderItem table is probably already created: " + e.toString(), e);
        }
    }
}
