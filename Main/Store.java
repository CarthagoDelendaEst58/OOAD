package Main;

import Items.*;

import java.awt.print.Paper;
import java.util.*;

public class Store {
    private ArrayList<Item> inventory;
    private ArrayList<Item> inDelivery;
    private ArrayList<Clerk> staff;
    private CashRegister register;
    private int day;
    public static String[] conditions = {"poor", "fair", "good", "very good", "excellent"};
    public static String[] item_types = {"CDPlayerItem", "MP3PlayerItem", "RecordPlayerItem", "VinylItem", "PaperScoreItem", "CDItem", "HatItem", "BandanaItem", "ShirtItem", "StringsItem", "CablesItem", "PracticeAmpItem", "FluteItem", "HarmonicaItem", "MandolinItem", "GuitarItem", "BassItem"};
    private double moneyWithdrawnFromBank;

    Store() {
        register = new CashRegister();
        staff = new ArrayList<Clerk>();
        day = 0;
        moneyWithdrawnFromBank = 0;

        inDelivery = new ArrayList<Item>();
        inventory = new ArrayList<Item>();
        for (int i = 0; i < 3; i++) {
            inventory.add(generateItem());
        }
    }

    public ArrayList<Clerk> getStaff() {
        return staff;
    }
    public ArrayList<Item> getItemsInDelivery() {
        return inDelivery;
    }
    public ArrayList<Item> getInventory() { return inventory; }
    public void moneyWithdrawn(double money) { moneyWithdrawnFromBank += money; }

    public Item generateItem() {
        Random rand = new Random();

        double purchasePrice = rand.nextDouble() * 49 + 1;
        String condition = conditions[rand.nextInt(5)];
        String name = "temp";

        Item myPaperScore = new PaperScoreItem("Nirvana Album", purchasePrice, purchasePrice*2, true, 0, "good", "Nirvana", "Nevermind");
        return myPaperScore;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void addClerk(String clerk_name, double damageChance) {
        Clerk new_clerk = new Clerk(clerk_name, register, this, damageChance);
        staff.add(new_clerk);
    }


}