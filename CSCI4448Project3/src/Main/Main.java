package Main;

public class Main {

    public static void main(String[] args){
        int numDays = 10;
        FNMS simulation = new FNMS();
        simulation.initialize();
        simulation.simulate(numDays);
        simulation.summary(numDays);
    }
}
