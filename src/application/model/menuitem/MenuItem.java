package application.model.menuitem;

/**
 * Object to represent a single menu item.
 */
public class MenuItem {
    
    private String name;
    private MenuItemType type;
    private MenuItemCategory category;
    private int id;
    private int price;
    private int energy;
    private double protean;
    private double carbohydrates;
    private double fat;
    private double fibre;

    /**
     * A method to get a name of the item
     * 
     * @return String contains name
     */
    public String getName() {
        return name;
    }

    /**
     * A method to set a name of the item
     * 
     * @param name name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A method to get a price of the item
     * 
     * @return price of an item
     */
    public int getPrice() {
        return price;
    }

    /**
     * A method to set price of the item
     * 
     * @param price price of the item
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * A method to get an energy of the item
     * 
     * @return energy of an item
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * A method to set an energy of the item
     * 
     * @param energy energy of an item
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * A method to get an protein of the item
     * 
     * @return protein protein of an item
     */
    public double getProtean() {
        return protean;
    }

    /**
     * A method to set a protein of the item
     * 
     * @param protean protein of an item
     */
    public void setProtean(double protean) {
        this.protean = protean;
    }

    /**
     * A method to get a fat of the item
     * 
     * @return fat for an item
     */
    public double getFat() {
        return fat;
    }

    /**
     * A method to set a fat of the item
     * 
     * @param fat fat of an item
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * A method to get a fibre of the item
     * 
     * @return fibre of an item
     */
    public double getFibre() {
        return fibre;
    }

    /**
     * A method to set an energy of the item
     * 
     * @param fibre fibre for an item
     */
    public void setFibre(double fibre) {
        this.fibre = fibre;
    }

    /**
     * A method to get a type of the item
     * 
     * @return type type of the menu item
     */
    public MenuItemType getType() {
        return type;
    }

    /**
     * A method to set a type of the menu item
     * 
     * @param type type of menu item
     */
    public void setType(MenuItemType type) {
        this.type = type;
    }

    /**
     * A method to get an category of the item
     * 
     * @return category category of menu item
     */
    public MenuItemCategory getCategory() {
        return category;
    }

    /**
     * A method to set category of the item
     * 
     * @param category category of an item
     */
    public void setCategory(MenuItemCategory category) {
        this.category = category;
    }

    /**
     * A method to get a carbohydrate of the item
     * 
     * @return carbohydrate carbohydrate of an item
     */
    public double getCarbohydrates() {
        return carbohydrates;
    }

    /**
     * A method to set a carbohydrate of the item
     * 
     * @param carbohydrates carbohydrate of an item
     */
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    /**
     * A method to get an id of the item
     * 
     * @return id an id of an item
     */
    public int getId() {
        return id;
    }

    /**
     * A method to set a id of the item
     * 
     * @param id an id of an item
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A constructor of menu item
     * 
     * @param id id of an item
     * @param name name of an item
     * @param price price of an item
     * @param energy energy of an item
     * @param protean protein of an item
     * @param carbohydrates carbohydrate of an item
     * @param fat fat of an item
     * @param fibre fibre of an item
     * @param category category of menu item 
     * @param type type of menu item
     */
    public MenuItem(int id, String name, int price, int energy, double protean, double carbohydrates,
                    double fat, double fibre, MenuItemCategory category, MenuItemType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.energy = energy;
        this.protean = protean;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.fibre = fibre;
        this.type = type;
        this.category = category;
    }

    /**
     * A constructor of menu item
     * 
     */
    public MenuItem() {
    }
}
