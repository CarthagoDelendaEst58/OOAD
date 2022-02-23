package Main;

import java.util.*;
import Items.*;

// Clerk inherits Staff, which provides it with the generalized attributes and methods
public class Clerk extends Staff {
    private double damageChance;
    private TuningStrategy strategy;
    private static ArrayList<Observer> observers = new ArrayList<>();
    private static int observerID = 0;

    enum Event { ITEMSOLD, ITEMPURCHASED, ITEMDAMAGED, LEAVE, ARRIVED, ITEMSARRIVED, REGISTERBALANCE, INVENTORYSIZE, INVENTORYVALUE, ITEMORDERED }

    public static final int ITEMSOLD = 0;
    public static final int ITEMPURCHASED = 1;
    public static final int ITEMDAMAGED = 2;
    public static final int LEAVE = 3;
    public static final int ARRIVED = 4;

    Clerk(String _name, CashRegister _register, Store _store, double damageChance, TuningStrategy strategy) {
        super(_name, _register, _store);
        this.damageChance = damageChance;
        this.strategy = strategy;
    }

    public static void registerObserver(String type) {
        if (type.equals("Tracker")) {
            Tracker newTracker = new Tracker(observerID);
            observers.add(newTracker);
            observerID++;
        }
        else if (type.equals("Logger")) {
            Logger newLogger = new Logger(observerID);
            observers.add(newLogger);
            observerID++;
        }

    }

    private static void removeObserver(int id) {
        for (int i = observers.size()-1; i >= 0; i--) {
            if (observers.get(i).getID() == id) {
                observers.remove(i);
            }
        }
    }

    private static int getLoggerID() {
        for (Observer observer : observers) {
            if (observer.getClass().getSimpleName() == "Loggger") {
                return observer.getID();
            }
        }
        return -1;
    }

    private void notifyObservers(Event event, double val) {
        for (Observer observer : observers) {
            observer.update(getName(), event, getStore().getDay(), val);
        }
    }

    // Executes the sequence of actions to be performed within one day
    //
    // This is the only public method in Clerk, which is a good example of
    // abstraction as outside objects are only able to initiate this sequence
    public void work(int day) {
        arriveAtStore(day);
        checkRegister();
        doInventory();
        openTheStore();
        cleanTheStore();
        leaveTheStore();
    }

    // The Clerk arrives at the store on the given day, announcing their arrival
    // The Clerk also checks the Store's inDelivery ArrayList for any newly delivered items
    private void arriveAtStore(int day) {
        registerObserver("Logger");
        System.out.println(String.format("%s has arrived at the store on day %d", getName(), getStore().getDay()));
        notifyObservers(Event.ARRIVED, 0);
        ArrayList<Item> inDelivery = getStore().getItemsInDelivery();

        int num_items_arrived = 0;
        for (int i = inDelivery.size()-1; i >= 0; i--) { // looping from the back to avoid issues when removing from the ArrayList
            if (inDelivery.get(i).getDayArrived() == day) {
                System.out.println(String.format("%s has discovered that a %s has arrived at the store", getName(), inDelivery.get(i).getClassName()));
                getStore().addItemToInventory(inDelivery.get(i));
                inDelivery.remove(i);
                num_items_arrived++;
            }
        }

        if (num_items_arrived > 0) {
            notifyObservers(Event.ITEMSARRIVED, num_items_arrived);
        }
    }

    // The Clerk checks the Register, announcing the balance
    // Initiates goToBank if the balance is lower than $75
    private void checkRegister() {
        double register_balance = getRegister().getBalance();
        System.out.println(String.format("%s has counted a total of $%f in the register", getName(), register_balance));
        notifyObservers(Event.REGISTERBALANCE, register_balance);

        if (register_balance < 75) {
            goToBank();
        }
    }

    // Occurs when the Register has less than $75
    // Adds $1000 to the register
    private void goToBank() {
        getRegister().alterBalance(1000);
        getStore().moneyWithdrawn(1000);
        System.out.println(String.format("%s has added $1000 to the register", getName()));
        double register_balance = getRegister().getBalance();
        notifyObservers(Event.REGISTERBALANCE, register_balance);
    }

    private int tuningOutcome(Item item, boolean before, boolean after) {
        Random rand = new Random();
        if (before && !after) {
            System.out.printf("%s has detuned a %s\n", getName(), item.getClassName());
            if (rand.nextInt(100) < 10) {
                return damageItem(item);
            }
        }
        else if (!before && after) {
            System.out.printf("%s has successfully tuned up a %s\n", getName(), item.getClassName());
        }
        return 2;
    }

    private int tuneItem(Item item, String classname) {
        int result = -2;
        if (Item.isStringed(classname)) {
            boolean tuning_result = strategy.tune(item.getTuned());
            result = tuningOutcome(item, item.getTuned(), tuning_result);
            item.setTuned(tuning_result);
        }
        else if (Item.isWind(classname)) {
            boolean tuning_result = strategy.tune(item.getAdjusted());
            result = tuningOutcome(item, item.getAdjusted(), tuning_result);
            item.setAdjusted(tuning_result);
        }
        else if (Item.isPlayer(classname)) {
            boolean tuning_result = strategy.tune(item.getEqualized());
            result = tuningOutcome(item, item.getEqualized(), tuning_result);
            item.setEqualized(tuning_result);
        }

        return result;
    }

    // Totals the purchase price of all items in the Store's inventory
    // Checks if items need to be ordered
    private void doInventory() {
        HashMap<String, Integer> stock = new HashMap<String, Integer>(); // Hashmap to determine counts of Item types in stock
        for (int i = 0; i < getStore().item_types.length; i++) {
            stock.put(getStore().item_types[i], 0);
        }
        ArrayList<Item> inventory = getStore().getInventory();
        double total = 0;
        int num_items_damaged = 0;
        for (int i = inventory.size()-1; i >= 0; i--) {
            String classname = inventory.get(i).getClassName();
            int tuning_result = tuneItem(inventory.get(i), classname);

            if (tuning_result != 1) {
                total += inventory.get(i).getPurchasePrice();
                stock.put(classname, stock.get(classname)+1);
            }

            if (tuning_result != -2 && tuning_result != 2) {
                num_items_damaged++;
            }
        }

        System.out.println(String.format("%s has determined that the total value of items in the store is $%f", getName(), total));
        notifyObservers(Event.INVENTORYSIZE, inventory.size());
        notifyObservers(Event.INVENTORYVALUE, total);
        notifyObservers(Event.ITEMDAMAGED, num_items_damaged);

        int num_items_ordered = 0;
        for (Map.Entry pair : stock.entrySet()) {
            if ((int)pair.getValue() <= 0) { // No items of this type in stock
                boolean item_inDelivery = false;
                for (Item item : getStore().getItemsInDelivery()) {
                    if (item.getClassName().equals((String)pair.getKey())) {
                        item_inDelivery = true;
                    }
                }
                if (!item_inDelivery && !Item.isStringed((String)pair.getKey())) { // Only order new items if they haven't been ordered yet
                    placeAnOrder((String) pair.getKey());
                    num_items_ordered += 3;
                }
            }
        }
        notifyObservers(Event.ITEMORDERED, num_items_ordered);
    }

    // Orders 3 new items of the given item type
    // Adds Items to the delivery list and removes money from the Register
    private void placeAnOrder(String item_type) {
        ItemFactory factory = new ItemFactory();
        Random rand = new Random();
        int days_to_delivery = rand.nextInt(3)+1;
        for (int i = 0; i < 3; i++) {
            Item newItem = factory.generateItem(item_type);
            newItem.setDayArrived(getStore().getDay()+days_to_delivery);
            Store store = getStore();
            store.addItemToInDelivery(newItem);
            getRegister().alterBalance(-1*newItem.getPurchasePrice());
        }

        System.out.println(String.format("%s has placed an order for 3 %s", getName(), item_type));
    }

    // Executes the sale of the specified Item to the Customer
    private void sellItem(Customer customer, Item item, double price, int item_index) {
        item.setDaySold(getStore().getDay());
        item.setSalePrice(price);
        customer.obtainItem(item);
        getStore().getInventory().remove(item_index);
        getStore().soldItem(item);
        getRegister().alterBalance(price);

        System.out.println(String.format("%s has sold a %s to %s for $%f", getName(), item.getClassName(), customer.getName(), price));
//        notifyObservers(Event.ITEMSOLD, 1);
    }

    // Executes the purchase of an Item from the Customer
    private void buyItem(Customer customer, Item item, double price) {
        item.setPurchasePrice(price);
        getStore().addItemToInventory(item);
        getRegister().alterBalance(-1*price);

        String newOrUsed = item.getNewOrUsed() ? "new" : "used";
        System.out.println(String.format("%s has bought a %s condition %s %s from %s for $%f", getName(), item.getCondition(), newOrUsed, item.getClassName(), customer.getName(), price));
//        notifyObservers(Event.ITEMPURCHASED, 1);
    }

    // This function was taken from the top rated answer of this StackOverflow post: https://stackoverflow.com/questions/9832919/generate-poisson-arrival-in-java
    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    // The Clerk deals with the two types of customers
    private void openTheStore() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Random rand = new Random();
        String[] names = Customer.getNames();
        String[] item_types = Store.item_types;
        ArrayList<Item> items = getStore().getInventory();
        for (int i = 0; i < getPoissonRandom(3)+4; i++) { // generate buying customers
            Customer new_customer = new Customer(names[rand.nextInt(names.length)], false, item_types[rand.nextInt(item_types.length)]);
            customers.add(new_customer);
        }
        for (int i = 0; i < rand.nextInt(4)+1; i++) { // generate selling customers
            Customer new_customer = new Customer(names[rand.nextInt(names.length)], true, item_types[rand.nextInt(item_types.length)]);
            customers.add(new_customer);
        }

        int num_items_bought = 0;
        int num_items_sold = 0;
        for (int i = 0; i < customers.size(); i++) {
            Customer curr_customer = customers.get(i);
            if (curr_customer.isSeller()) {
                ItemFactory factory = new ItemFactory();
                Item newItem = factory.generateItem(curr_customer.getItemType()); // generate item being offered

                if (!Item.isClothing(newItem.getClassName())) {
                    double price = 0;
                    for (int j = 0; j < Store.conditions.length; j++) {
                        if (newItem.getCondition().equals(Store.conditions[j])) { // determine base price dependant on item condition
                            price = (j + 1) * 10;
                        }
                    }
                    if (!newItem.getNewOrUsed()) { // halve price for used items
                        price = price / 2;
                    }

                    if (rand.nextInt(100) < 50) { // Item bought at full price
                        buyItem(curr_customer, newItem, price);
                        num_items_bought++;
                    } else if (rand.nextInt(100) < 75) { // Item bought at slight markup
                        buyItem(curr_customer, newItem, price * 1.1);
                        num_items_bought++;
                    }
                }
            }
            else {
                boolean item_bought = false;
                for (int j = items.size()-1; j >= 0; j--) {
                    Item curr_item = items.get(j);
                    if (curr_item.getClassName().equals(curr_customer.getItemType())) { // Customer finds Item of type wanting to buy
                        if (rand.nextInt(100) < 50) { // Customer buys item at list price
                            sellItem(curr_customer, curr_item, curr_item.getListPrice(), j);
                            item_bought = true;
                            num_items_sold++;
                        }
                        else if (rand.nextInt(100) < 75) { // Customer buys item at slight discount
                            sellItem(curr_customer, curr_item, curr_item.getListPrice()*0.9, j);
                            item_bought = true;
                            num_items_sold++;
                        }
                        break;
                    }
                }
                if (!item_bought) {
                    System.out.println(String.format("%s has left the store without buying anything", curr_customer.getName()));
                }
            }
        }
        notifyObservers(Event.ITEMPURCHASED, num_items_bought);
        notifyObservers(Event.ITEMSOLD, num_items_sold);
    }

    // Returns the index of the first occurrence of the given String in the given String array
    // Returns -1 if the given String does not exist within the array
    int getPosOfStr(String[] arr, String str) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private int damageItem(Item item) {
        int condition_index = getPosOfStr(Store.conditions, item.getCondition());
        if (condition_index == 0) { // Item will be destroyed
            System.out.println(String.format("%s has destroyed a(n) %s", getName(), item.getClassName()));
//            notifyObservers(Event.ITEMDAMAGED, 1);
            return 1;
        }
        else if (condition_index > 0) {
            System.out.println(String.format("%s has damaged a(n) %s, lowering its condition to %s", getName(), item.getClassName(), Store.conditions[condition_index-1]));
            item.setCondition(Store.conditions[condition_index-1]); // lowering conditions
            item.setListPrice(item.getListPrice()*0.8); // lowering listPrice
//            notifyObservers(Event.ITEMDAMAGED, 1);
            return 0;
        }
        return -1;
    }

    // The Clerk closes up shop and has a chance to damage each item
    // If an item with the lowest condition gets damaged, it is destroyed
    private void cleanTheStore() {
        Random rand = new Random();
        ArrayList<Item> inventory = getStore().getInventory();
        int num_items_damaged = 0;
        for (int i = inventory.size()-1; i >= 0; i--) { // Looping from the back to avoid issues with .remove()
            if (rand.nextInt(100) < damageChance) { // Item is damaged
                num_items_damaged++;
                if (damageItem(inventory.get(i)) == 1) {
                    inventory.remove(i);
                }
            }
        }
        notifyObservers(Event.ITEMDAMAGED, num_items_damaged);
    }

    // The Clerk announces their leave from the store
    private void leaveTheStore() {
        System.out.println(String.format("%s is leaving the store", getName()));
        notifyObservers(Event.LEAVE, 0);

        int loggerID = getLoggerID();
        if (loggerID >= 0) {
            removeObserver(loggerID);
        }
    }
}