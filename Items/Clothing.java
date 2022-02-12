package Items;

public abstract class Clothing extends Item{

    Clothing(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
    }
}
