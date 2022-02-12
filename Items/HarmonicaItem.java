package Items;

public class HarmonicaItem extends Wind {
    String key;

    public HarmonicaItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition, String key){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.key = key;
    }
}
