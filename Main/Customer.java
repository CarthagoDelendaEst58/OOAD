package Main;

import Items.Item;

import java.util.ArrayList;

public class Customer {
    private String name;
    private boolean seller;
    private String itemType;
    private ArrayList<Item> itemsOwned;

    // List of names which can be randomly sampled from
    private static String[] names = {"Liam", "Olivia", "Noah", "Emma", "Oliver",
                              "Ava", "Elijah", "Charlotte", "William", "Sophia"};

    public Customer(String name, boolean seller, String itemType){
        this.name = name;
        this.seller = seller;
        this.itemType = itemType;
        itemsOwned = new ArrayList<Item>();
    }

    public String getName() { return name; }
    public boolean isSeller() { return seller; }
    public String getItemType() { return itemType; }
    public static String[] getNames() { return names; }

    public void obtainItem(Item itemName){
        itemsOwned.add(itemName);
    }

}
