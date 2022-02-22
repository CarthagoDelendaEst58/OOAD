package Main;

import java.util.*;
import Items.*;

// Clerk inherits Staff, which provides it with the generalized attributes and methods
public class Clerk extends Staff {
    private double damageChance;
    private TuningStrategy strategy;
    private static ArrayList<Observer> observers = new ArrayList<>();
    private static int observerID = 0;

    public static final int ITEMSOLD = 0;
    public static final int ITEMPURCHASED = 1;
    public static final int ITEMDAMAGED = 2;
    public static final int LEAVE = 3;

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
//        else if (type.equals(""))

    }

    private static void removeObserver(int id) {
        for (int i = observers.size()-1; i >= 0; i--) {
            if (observers.get(i).getID() == id) {
                observers.remove(i);
            }
        }
    }

    private void notifyObservers(int event) {
        for (Observer observer : observers) {
            observer.update(getName(), event);
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
        System.out.println(String.format("%s has arrived at the store on day %d", getName(), getStore().getDay()));
        ArrayList<Item> inDelivery = getStore().getItemsInDelivery();
        for (int i = inDelivery.size()-1; i >= 0; i--) { // looping from the back to avoid issues when removing from the ArrayList
            if (inDelivery.get(i).getDayArrived() == day) {
                System.out.println(String.format("%s has discovered that a %s has arrived at the store", getName(), inDelivery.get(i).getClassName()));
                getStore().addItemToInventory(inDelivery.get(i));
                inDelivery.remove(i);
            }
        }
    }

    // The Clerk checks the Register, announcing the balance
    // Initiates goToBank if the balance is lower than $75
    private void checkRegister() {
        double register_balance = getRegister().getBalance();
        System.out.println(String.format("%s has counted a total of $%f in the register", getName(), register_balance));

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
        for (int i = inventory.size()-1; i >= 0; i--) {
            String classname = inventory.get(i).getClassName();
            tuneItem(inventory.get(i), classname);

            total += inventory.get(i).getPurchasePrice();
            stock.put(classname, stock.get(classname)+1);
        }

        System.out.println(String.format("%s has determined that the total value of items in the store is $%f", getName(), total));

        for (Map.Entry pair : stock.entrySet()) {
            if ((int)pair.getValue() <= 0) { // No items of this type in stock
                boolean item_inDelivery = false;
                for (Item item : getStore().getItemsInDelivery()) {
                    if (item.getClassName().equals((String)pair.getKey())) {
                        item_inDelivery = true;
                    }
                }
                if (!item_inDelivery) { // Only order new items if they haven't been ordered yet
                    placeAnOrder((String) pair.getKey());
                }
            }
        }
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
        notifyObservers(ITEMSOLD);
    }

    // Executes the purchase of an Item from the Customer
    private void buyItem(Customer customer, Item item, double price) {
        item.setPurchasePrice(price);
        getStore().addItemToInventory(item);
        getRegister().alterBalance(-1*price);

        String newOrUsed = item.getNewOrUsed() ? "new" : "used";
        System.out.println(String.format("%s has bought a %s condition %s %s from %s for $%f", getName(), item.getCondition(), newOrUsed, item.getClassName(), customer.getName(), price));
        notifyObservers(ITEMPURCHASED);
    }

    // The Clerk deals with the two types of customers
    private void openTheStore() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Random rand = new Random();
        String[] names = Customer.getNames();
        String[] item_types = Store.item_types;
        ArrayList<Item> items = getStore().getInventory();
        for (int i = 0; i < rand.nextInt(7)+4; i++) { // generate buying customers
            Customer new_customer = new Customer(names[rand.nextInt(names.length)], false, item_types[rand.nextInt(item_types.length)]);
            customers.add(new_customer);
        }
        for (int i = 0; i < rand.nextInt(4)+1; i++) { // generate selling customers
            Customer new_customer = new Customer(names[rand.nextInt(names.length)], true, item_types[rand.nextInt(item_types.length)]);
            customers.add(new_customer);
        }

        for (int i = 0; i < customers.size(); i++) {
            Customer curr_customer = customers.get(i);
            if (curr_customer.isSeller()) {
                ItemFactory factory = new ItemFactory();
                Item newItem = factory.generateItem(curr_customer.getItemType()); // generate item being offered

                double price = 0;
                for (int j = 0; j < Store.conditions.length; j++) {
                    if (newItem.getCondition().equals(Store.conditions[j])) { // determine base price dependant on item condition
                        price = (j+1) * 10;
                    }
                }
                if (!newItem.getNewOrUsed()) { // halve price for used items
                    price = price/2;
                }

                if (rand.nextInt(100) < 50) { // Item bought at full price
                    buyItem(curr_customer, newItem, price);
                }
                else if (rand.nextInt(100) < 75) { // Item bought at slight markup
                    buyItem(curr_customer, newItem, price*1.1);
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
                        }
                        else if (rand.nextInt(100) < 75) { // Customer buys item at slight discount
                            sellItem(curr_customer, curr_item, curr_item.getListPrice()*0.9, j);
                            item_bought = true;
                        }
                        break;
                    }
                }
                if (!item_bought) {
                    System.out.println(String.format("%s has left the store without buying anything", curr_customer.getName()));
                }
            }
        }
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
            notifyObservers(ITEMDAMAGED);
            return 1;
        }
        else if (condition_index > 0) {
            System.out.println(String.format("%s has damaged a(n) %s, lowering its condition to %s", getName(), item.getClassName(), Store.conditions[condition_index-1]));
            item.setCondition(Store.conditions[condition_index-1]); // lowering conditions
            item.setListPrice(item.getListPrice()*0.8); // lowering listPrice
            notifyObservers(ITEMDAMAGED);
            return 0;
        }
        return -1;
    }

    // The Clerk closes up shop and has a chance to damage each item
    // If an item with the lowest condition gets damaged, it is destroyed
    private void cleanTheStore() {
        Random rand = new Random();
        ArrayList<Item> inventory = getStore().getInventory();
        for (int i = inventory.size()-1; i >= 0; i--) { // Looping from the back to avoid issues with .remove()
            if (rand.nextInt(100) < damageChance) { // Item is damaged
                if (damageItem(inventory.get(i)) == 1) {
                    inventory.remove(i);
                }
            }
        }
    }

    // The Clerk announces their leave from the store
    private void leaveTheStore() {
        System.out.println(String.format("%s is leaving the store", getName()));
        notifyObservers(LEAVE);
    }
}