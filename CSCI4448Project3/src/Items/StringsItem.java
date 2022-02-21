package Items;

public class StringsItem extends Accessories {
    String type;

    public StringsItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition,String type){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.type = type;
    }
}
    