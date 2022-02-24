package Items;

public abstract class Wind extends Instruments {

    boolean adjusted;

    public Wind(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.adjusted = false;
    }
}
