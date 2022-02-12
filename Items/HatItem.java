package Items;

public class HatItem extends Clothing{

    int hatSize;

    public HatItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition, int hatSize){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.hatSize = hatSize;
    }
}
