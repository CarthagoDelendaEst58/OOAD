package Main;

public class Simulate {

    public static void initializeStore() {
        //create starting set of inventory
        //  3 of each lowest subclass of merch, any name, any purchase price, list price = 2x purchase price
        //  dayArrived = 0 for each item
        //  attributes of salePrice and daysold are set when item is sold

    }

    public static void main(String[] args) {
        Store store = new Store();
        store.addClerk("Velma", 5);
        store.addClerk("Shaggy", 20);

        store.getStaff().get(1).work(1);
    }
}
