package Items;

public class CablesItem extends Accessories {
    double length;

    public CablesItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition,double length){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.length = length;
    }
}
