package Main;

import Main.Clerk.Event;

public interface Observer {
    void update(String clerkName, Event event, int day, double val);
    int getID();
}
