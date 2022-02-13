package Main;

import java.util.*;
import Items.*;

public class Clerk extends Staff {
    private double damageChance;

    Clerk(String _name, CashRegister _register, Store _store, double damageChance) {
        super(_name, _register, _store);
        this.damageChance = damageChance;
    }

    public void work(int day) {
        arriveAtStore(day);
        checkRegister();
        doInventory();
        cleanTheStore();
        leaveTheStore();
    }

    private void arriveAtStore(int day) {
        System.out.println(String.format("%s has arrived at the store on day %d", getName(), day));
        ArrayList<Item> inDelivery = getStore().getItemsInDelivery();
        for (int i = inDelivery.size()-1; i >= 0; i--) {
            if (inDelivery.get(i).getDayArrived() == day) {
                getStore().addItemToInventory(inDelivery.get(i));
                inDelivery.remove(i);
            }
        }
    }

    private void checkRegister() {
        double register_balance = getRegister().getBalance();
        System.out.println(String.format("%s has counted a total of $%f in the register", getName(), register_balance));

        if (register_balance < 75) {
            goToBank();
        }
    }

    private void goToBank() {
        getRegister().alterBalance(1000);
        getStore().moneyWithdrawn(1000);
        System.out.println(String.format("%s has added $1000 to the register", getName()));
    }

    private void doInventory() {
        HashMap<String, Integer> stock = new HashMap<String, Integer>();
        for (int i = 0; i < getStore().item_types.length; i++) {
            stock.put(getStore().item_types[i], 0);
        }
        ArrayList<Item> inventory = getStore().getInventory();
        double total = 0;
        for (int i = 0; i < inventory.size(); i++) {
            total += inventory.get(i).getPurchasePrice();
            stock.put(inventory.get(i).getClassName(), stock.get(inventory.get(i).getClassName()+1));
        }
        System.out.println(String.format("%s has determined that the total value of items in the store is $%f", getName(), total));

        for (Map.Entry pair : stock.entrySet()) {
            if ((int)pair.getValue() <= 0) {
                placeAnOrder((String)pair.getKey());
            }
        }
    }

//    private void purchaseItem() {
//        Item newItem = getStore().generateItem();
//    }

    private void placeAnOrder(String item_type) {

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
                    inventory.remove(i);
                }
                else {
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