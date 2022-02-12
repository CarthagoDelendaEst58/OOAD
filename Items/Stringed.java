package Items;

public abstract class Stringed extends Instruments {

    boolean isElectric;

    Stringed(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition, boolean isElectric){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.isElectric = isElectric;
    }
}
