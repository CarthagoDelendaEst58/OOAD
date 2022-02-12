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

    Store() {
        register = new CashRegister();
        staff = new ArrayList<Clerk>();
        day = 0;

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

    public Item generateItem() {
        Random rand = new Random();
        String[] conditions = {"poor", "fair", "good", "very good", "excellent"};

        double purchasePrice = rand.nextDouble() * 49 + 1;
        String condition = conditions[rand.nextInt(5)];
        String name = "temp";

        Item myPaperScore = new PaperScoreItem("Nirvana Album", 25.00,50.00, true, 0, "Good", "Nirvana", "Nevermind");
        return myPaperScore;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void addClerk(String clerk_name) {
        Clerk new_clerk = new Clerk(clerk_name, register, this);
        staff.add(new_clerk);
    }


}