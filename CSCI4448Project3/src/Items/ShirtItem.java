package Items;

public class ShirtItem extends Clothing{
    int shirtSize;

    public ShirtItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition, int shirtSize){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.shirtSize = shirtSize;
    }
}
