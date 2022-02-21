package Main;

import java.util.*;

public class Tracker implements Observer{
    private HashMap<String, HashMap<String, Integer>> clerkData;

    Tracker() {
        clerkData = new HashMap<>();
    }

    private void addClerk(String clerkName) {
        HashMap<String, Integer> new_map = new HashMap<>();
        new_map.put("ItemsSold", 0);
        new_map.put("ItemsPurchased", 0);
        new_map.put("ItemsDamaged", 0);
        clerkData.put(clerkName, new_map);
    }

    private void removeClerk(String clerkName) {
        clerkData.remove(clerkName);
    }

    public void update(String clerkName, String event) {
        if (!clerkData.containsKey(clerkName)) {
            addClerk(clerkName);
        }

        if (event == "ItemSold") {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsSold", data.get("ItemsSold")+1);
        }
        else if (event == "ItemPurchased") {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsPurchased", data.get("ItemsPurchased")+1);
        }
        else if (event == "ItemDamaged") {
            HashMap<String, Integer> data = clerkData.get(clerkName);
            data.put("ItemsDamaged", data.get("ItemsDamaged")+1);
        }
    }
}
