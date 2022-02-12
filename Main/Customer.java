package Main;

import Items.Item;

import java.util.ArrayList;

public class Customer {
    String name;
    private ArrayList<Item> itemsOwned;

    public Customer(String name){
        this.name = name;
    }

    public void obtainItem(Item itemName){
        itemsOwned.add(itemName);
    }

}
