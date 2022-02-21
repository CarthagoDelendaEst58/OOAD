package Items;

public abstract class Players extends Item {
    public Players(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
    }
}

