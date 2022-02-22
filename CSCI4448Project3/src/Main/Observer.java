package Main;

public interface Observer {
    void update(String clerkName, int event, int day);
    int getID();
}
