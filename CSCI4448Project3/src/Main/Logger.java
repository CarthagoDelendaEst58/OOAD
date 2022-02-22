package Main;

import java.io.*;

public class Logger implements Observer {
    private int id;
    OutputStream out;

    Logger(int id) {
        this.id = id;
        this.out = null;
    }

    public void update(String clerkName, int event, int day) {
//        File newLog = new File(String.format("./out/logs/Logger-%d.txt", day));
//        if (!newLog.exists()) {
//            newLog.createNewFile();
//        }
//        out = new FileOutputStream();
//        if (event == Clerk.LEAVE) {
//            String toWrite = String.format("%s is leaving the store\n", clerkName);
//        }
    }

    public int getID() { return id; }
}
