package Items;

public abstract class Wind extends Instruments {

    public Wind(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
    }
}
