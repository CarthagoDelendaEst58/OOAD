package Main;

import Items.*;

import java.awt.print.Paper;
import java.util.*;

public class Store {
    private ArrayList<Item> inventory;
    private ArrayList<Item> inDelivery;
    private ArrayList<Item> soldItems;
    private ArrayList<Clerk> staff;
    private CashRegister register;
    private int day;
    public static String[] conditions = {"poor", "fair", "good", "very good", "excellent"};
    public static String[] item_types = {"CDPlayerItem", "MP3PlayerItem", "RecordPlayerItem", "VinylItem",
                                         "PaperScoreItem", "CDItem", "HatItem", "BandanaItem", "ShirtItem",
                                         "StringsItem", "CablesItem", "PracticeAmpItem", "FluteItem", "HarmonicaItem",
                                         "MandolinItem", "GuitarItem", "BassItem"};
    //arrays for band types, string types, all sub attributes to make items more diverse...
    private double moneyWithdrawnFromBank;
    private ItemFactory itemFactory = new ItemFactory();

    Store() {
        register = new CashRegister();
        staff = new ArrayList<Clerk>();
        day = 0;

        moneyWithdrawnFromBank = 0;

        inDelivery = new ArrayList<Item>();
        inventory = new ArrayList<Item>();
        soldItems = new ArrayList<Item>();
        //functionality for adding preset inventory will be in initalize for main
    }

    public ArrayList<Clerk> getStaff() {
        return staff;
    }
    public ArrayList<Item> getItemsInDelivery() {
        return inDelivery;
    }

    public int getDay() {
        return day;
    }

    public ArrayList<Item> getInventory() { return inventory; }
    public void moneyWithdrawn(double money) { moneyWithdrawnFromBank += money; }

    public Item generateItem(String type) {
        Random rand = new Random();
        double itemNumber = rand.nextDouble() * 200 + 1;


        String name = "Item #" + (int)itemNumber;
        double purchasePrice = rand.nextDouble() * 49 + 1;
        double listPrice = purchasePrice*2;
        boolean newOrUsed = true;
        int dayArrived = 0;
        String condition = conditions[rand.nextInt(5)];

        Item newItem = itemFactory.createItem(type, name, purchasePrice, listPrice, newOrUsed, dayArrived, condition,
                "Nirvana", "Nevermind", true, "Wood", "B", 5, 10,
                100.00, 10.00, "Nylon");

        return newItem;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void addItemToInDelivery(Item item) { inDelivery.add(item); }

    public void soldItem(Item item) { soldItems.add(item); }

    public void addClerk(String clerk_name, double damageChance) {
        Clerk new_clerk = new Clerk(clerk_name, register, this, damageChance);
        staff.add(new_clerk);
    }


}