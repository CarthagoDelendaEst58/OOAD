package Main;

import java.util.*;

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

    public void update(String clerkName, int event) {
        if (!clerkData.containsKey(clerkName)) {
            addClerk(clerkName);
        }

        if (event == Clerk.ITEMSOLD) {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsSold", data.get("ItemsSold")+1);
        }
        else if (event == Clerk.ITEMPURCHASED) {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsPurchased", data.get("ItemsPurchased")+1);
        }
        else if (event == Clerk.ITEMDAMAGED) {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsDamaged", data.get("ItemsDamaged")+1);
        }
        else if (event == Clerk.LEAVE) {
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
