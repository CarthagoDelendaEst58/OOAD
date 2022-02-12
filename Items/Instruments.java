package Items;

public abstract class Instruments extends Item {

    Instruments(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
    }
}
