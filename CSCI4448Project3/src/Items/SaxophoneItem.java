package Items;

public class SaxophoneItem extends Wind{
    String type;

    public SaxophoneItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition, String type){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.type = type;
    }
}
