package Main;

import java.util.*;
import Main.Clerk.Event;

public class Tracker implements Observer{
    private HashMap<String, HashMap<String, Integer>> clerkData;
    private int id;

    Tracker(int id) {
        this.id = id;
        clerkData = new HashMap<>();
    }

    public int getID() { return id; }

    private void addClerk(String clerkName) {
        HashMap<String, Integer> newMap = new HashMap<>();
        newMap.put("ItemsSold", 0);
        newMap.put("ItemsPurchased", 0);
        newMap.put("ItemsDamaged", 0);
        clerkData.put(clerkName, newMap);
    }

    private void removeClerk(String clerkName) {
        clerkData.remove(clerkName);
    }

    public void update(String clerkName, Event event, int day, double val) {
        if (!clerkData.containsKey(clerkName)) {
            addClerk(clerkName);
        }

        if (event == Event.ITEMSOLD) {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsSold", data.get("ItemsSold")+(int)val);
        }
        else if (event == Event.ITEMPURCHASED) {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsPurchased", data.get("ItemsPurchased")+(int)val);
        }
        else if (event == Event.ITEMDAMAGED) {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsDamaged", data.get("ItemsDamaged")+(int)val);
        }
        else if (event == Event.LEAVE) {
            printSummary();
        }
    }

    public void printSummary() {
        System.out.println("Clerk      Items Sold      Items Purchased      Items Damaged");
        for (Map.Entry<String, HashMap<String, Integer>> pair : clerkData.entrySet()) {
            String name = pair.getKey();
            HashMap<String, Integer> data = pair.getValue();
            System.out.printf("%-11s%-16s%-21s%s\n", name, data.get("ItemsSold").toString(), data.get("ItemsPurchased").toString(), data.get("ItemsDamaged").toString());
        }
    }
}
