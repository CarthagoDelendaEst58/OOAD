package Items;

public class PracticeAmpItem extends Accessories {
    double wattage;

    public PracticeAmpItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition,double wattage){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.wattage = wattage;
    }
}
