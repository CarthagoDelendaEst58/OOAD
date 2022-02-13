package Main;

import java.util.*;
import Items.*;

// Clerk inherits Staff, which provides it with the generalized attributes and methods
public class Clerk extends Staff {
    private double damageChance;

    Clerk(String _name, CashRegister _register, Store _store, double damageChance) {
        super(_name, _register, _store);
        this.damageChance = damageChance;
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
        System.out.println(String.format("%s has arrived at the store on day %d", getName(), day));
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

    // Totals the purchase price of all items in the Store's inventory
    // Checks if items need to be ordered
    private void doInventory() {
        HashMap<String, Integer> stock = new HashMap<String, Integer>(); // Hashmap to determine counts of Item types in stock
        for (int i = 0; i < getStore().item_types.length; i++) {
            stock.put(getStore().item_types[i], 0);
        }
        ArrayList<Item> inventory = getStore().getInventory();
        double total = 0;
        for (int i = 0; i < inventory.size(); i++) {
            total += inventory.get(i).getPurchasePrice();
            stock.put(inventory.get(i).getClassName(), stock.get(inventory.get(i).getClassName())+1);
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
                if (!item_inDelivery) {
                    placeAnOrder((String) pair.getKey());
                }
            }
        }
    }

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

    private void sellItem(Customer customer, Item item, double price, int item_index) {
        item.setDaySold(getStore().getDay());
        item.setSalePrice(price);
        customer.obtainItem(item);
        getStore().getInventory().remove(item_index);
        getStore().soldItem(item);
        getRegister().alterBalance(price);

        System.out.println(String.format("%s has sold a %s to %s for $%f", getName(), item.getClassName(), customer.getName(), price));
    }

    private void buyItem(Customer customer, Item item, double price) {
        item.setPurchasePrice(price);
        getStore().addItemToInventory(item);
        getRegister().alterBalance(-1*price);

        String newOrUsed = item.getNewOrUsed() ? "new" : "used";
        System.out.println(String.format("%s has bought a %s condition %s %s from %s for $%f", getName(), item.getCondition(), newOrUsed, item.getClassName(), customer.getName(), price));
    }

    private void openTheStore() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Random rand = new Random();
        String[] names = Customer.getNames();
        String[] item_types = Store.item_types;
        ArrayList<Item> items = getStore().getInventory();
        for (int i = 0; i < rand.nextInt(7)+4; i++) {
            Customer new_customer = new Customer(names[rand.nextInt(names.length)], false, item_types[rand.nextInt(item_types.length)]);
            customers.add(new_customer);
        }
        for (int i = 0; i < rand.nextInt(4)+1; i++) {
            Customer new_customer = new Customer(names[rand.nextInt(names.length)], true, item_types[rand.nextInt(item_types.length)]);
            customers.add(new_customer);
        }

        for (int i = 0; i < customers.size(); i++) {
            Customer curr_customer = customers.get(i);
            if (curr_customer.isSeller()) {
                ItemFactory factory = new ItemFactory();
                Item newItem = factory.generateItem(curr_customer.getItemType());

                double price = 0;
                for (int j = 0; j < Store.conditions.length; j++) {
                    if (newItem.getCondition().equals(Store.conditions[j])) {
                        price = (j+1) * 10;
                    }
                }
                if (!newItem.getNewOrUsed()) {
                    price = price/2;
                }

                if (rand.nextInt(100) < 50) {
                    buyItem(curr_customer, newItem, price);
                }
                else if (rand.nextInt(100) < 75) {
                    buyItem(curr_customer, newItem, price*1.1);
                }
            }
            else {
                boolean item_bought = false;
                for (int j = items.size()-1; j >= 0; j--) {
                    Item curr_item = items.get(j);
                    if (curr_item.getClassName().equals(curr_customer.getItemType())) {
                        if (rand.nextInt(100) < 50) {
                            sellItem(curr_customer, curr_item, curr_item.getListPrice(), j);
                            item_bought = true;
                        }
                        else if (rand.nextInt(100) < 75) {
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

    int getPosOfStr(String[] arr, String str) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private void cleanTheStore() {
        Random rand = new Random();
        ArrayList<Item> inventory = getStore().getInventory();
        for (int i = inventory.size()-1; i >= 0; i--) {
            if (rand.nextInt(100) < damageChance) {
                int condition_index = getPosOfStr(Store.conditions, inventory.get(i).getCondition());
                if (condition_index == 0) {
                    System.out.println(String.format("%s has destroyed a(n) %s", getName(), inventory.get(i).getClassName()));
                    inventory.remove(i);
                }
                else if (condition_index > 0) {
                    System.out.println(String.format("%s has damaged a(n) %s, lowering its condition to %s", getName(), inventory.get(i).getClassName(), Store.conditions[condition_index-1]));
                    inventory.get(i).setCondition(Store.conditions[condition_index-1]);
                    inventory.get(i).setListPrice(inventory.get(i).getListPrice()*0.8);
                }

            }
        }
    }

    private void leaveTheStore() {
        System.out.println(String.format("%s is leaving the store", getName()));
    }
}