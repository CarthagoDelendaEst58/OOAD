package Main;

public class Main {

    public static void main(String[] args){
        FNMS simulation = new FNMS();
        simulation.initialize();
        simulation.simulate(3);
    }
}
