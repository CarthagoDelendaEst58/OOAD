package Main;

import java.io.*;
import Main.Clerk.Event;

public class Logger implements Observer {
    private int id;
    private String log;
    private PrintWriter out;

    Logger(int id) {
        this.id = id;
        this.out = null;
        this.log = "";
    }

    private void exportLog(int day) {
        String path = String.format("./out/logs/Logger-%d.txt", day);
        File newLog = new File(path);
        if (!newLog.exists()) {
            try {
                newLog.createNewFile();
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }

        try {
            out = new PrintWriter(path);
            out.printf("%s", log);
        }
        catch (Exception e) {
            System.err.println(e);
        }
        out.close();
    }

    public void update(String clerkName, Event event, int day, double val) {
        if (event == Event.ARRIVED) {
            log += String.format("%s has arrived at the store\n", clerkName);
        }
        else if (event == Event.ITEMSARRIVED) {
            log += String.format("%.0f items were delivered to the store\n", val);
        }
        else if (event == Event.REGISTERBALANCE) {
            log += String.format("The register has a balance of $%.2f\n", val);
        }
        else if (event == Event.INVENTORYSIZE) {
            log += String.format("%s has determined that there are %.0f items in the store\n", clerkName, val);
        }
        else if (event == Event.INVENTORYVALUE) {
            log += String.format("%s has determined that the store's inventory is valued at $%.2f\n", clerkName, val);
        }
        else if (event == Event.ITEMDAMAGED) {
            log += String.format("%s has damaged %.0f items\n", clerkName, val);
        }
        else if (event == Event.ITEMORDERED) {
            log += String.format("%s has ordered %.0f items\n", clerkName, val);
        }
        else if (event == Event.ITEMPURCHASED) {
            log += String.format("%s has purchased %.0f items\n", clerkName, val);
        }
        else if (event == Event.ITEMSOLD) {
            log += String.format("%s has sold %.0f items\n", clerkName, val);
        }
        else if (event == Event.LEAVE) {
            log += String.format("%s is leaving the store\n", clerkName);
            exportLog(day);
        }
    }

    public int getID() { return id; }
}
