package Items;

public abstract class Players extends Item {

    boolean equalized;

    public Players(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.equalized = false;
    }
}

