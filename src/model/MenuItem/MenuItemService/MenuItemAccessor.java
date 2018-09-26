package model.MenuItem.MenuItemService;

import model.MenuItem.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuItemAccessor implements MenuItemDao {
    private static final Logger LOGGER = Logger.getLogger( MenuItemAccessor.class.getName() );

    private final String TABLE_NAME = "MenuItem";

    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_PRICE = "price";
    private final String COLUMN_ENERGY = "energy";
    private final String COLUMN_PROTEAN = "protean";
    private final String COLUMN_CARBOHYDRATES = "carbohydrates";
    private final String COLUMN_FAT = "fat";
    private final String COLUMN_FIBRE = "fibre";
    private final String COLUMN_ITEMCATEGORY_ID = "itemCategoryId";
    private final String COLUMN_ITEMTYPE_ID = "itemCategoryId";

    private final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
            "'" + COLUMN_ID + "' INTEGER NOT NULL, " +
            "'" + COLUMN_NAME + "' VARCHAR(100) NOT NULL, " +
            "'" + COLUMN_PRICE + "' INTEGER NOT NULL, " +
            "'" + COLUMN_ENERGY + "' INTEGER NOT NULL, " +
            "'" + COLUMN_PROTEAN + "' DECIMAL(1,1) NOT NULL, " +
            "'" + COLUMN_CARBOHYDRATES + "' DECIMAL(1,1) NOT NULL, " +
            "'" + COLUMN_FAT + "' DECIMAL(1,1) NOT NULL, " +
            "'" + COLUMN_FIBRE + "' DECIMAL(1,1) NOT NULL, " +
            "'" + COLUMN_ITEMCATEGORY_ID + "' INT NOT NULL " +
                "CONSTRAINT " + TABLE_NAME + "__" + "ITEMCATEGORY_FK references ItemCategory, " +
            "'" + COLUMN_ITEMTYPE_ID + "' INT NOT NULL " +
                "CONSTRAINT " + TABLE_NAME + "__" + "ITEMTYPE_FK references ItemType";

    private final Connection connection;

    public MenuItemAccessor() {
        Connection attemptedConnection = null;
        try {
            attemptedConnection = DriverManager.getConnection("jdbc:derby:RestaurantOrderingDB;create=true");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        connection = attemptedConnection;

        createMenuItemTable();
    }


    @Override
    public List<MenuItem> readMenuItems() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<MenuItem> menuItems = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet menuItemResultSet = statement.executeQuery(sql);

            while (menuItemResultSet.next()) {
                MenuItem menuItem = new MenuItem();
                menuItem.setName(menuItemResultSet.getString(COLUMN_NAME));
                menuItem.setPrice(menuItemResultSet.getInt(COLUMN_PRICE));
                menuItem.setEnergy(menuItemResultSet.getInt(COLUMN_ENERGY));
                menuItem.setProtean(menuItemResultSet.getDouble(COLUMN_PROTEAN));
                menuItem.setCarbohydrates(menuItemResultSet.getDouble(COLUMN_CARBOHYDRATES));
                menuItem.setFat(menuItemResultSet.getDouble(COLUMN_FAT));
                menuItem.setFibre(menuItemResultSet.getDouble(COLUMN_FIBRE));

                menuItems.add(menuItem);
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
        }

        return menuItems;
    }

    @Override
    public void createMenuItem(MenuItem itemToAdd) {
        String sql = "INSERT INTO " + TABLE_NAME +" (" +
                COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_PRICE + ", " + COLUMN_ENERGY + ", " + COLUMN_PROTEAN + ", " +
                COLUMN_CARBOHYDRATES + ", " + COLUMN_FAT + ", " + COLUMN_FIBRE + ")" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemToAdd.getId());
            statement.setString(2, itemToAdd.getName());
            statement.setInt(3, itemToAdd.getPrice());
            statement.setInt(4, itemToAdd.getEnergy());
            statement.setDouble(5, itemToAdd.getProtean());
            statement.setDouble(6, itemToAdd.getCarbohydrates());
            statement.setDouble(7, itemToAdd.getFat());
            statement.setDouble(8, itemToAdd.getFibre());
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

    private Boolean createMenuItemTable() {
        try {
            System.out.println(CREATE_TABLE);
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "MenuItem table is probably already created: " + e.toString(), e);
            return false;
        }
    }

    @Override
    public MenuItem getMenuItem(int id) {
        return null;
    }
}
