package Items;

public class FluteItem extends Wind {

    String type;

    FluteItem(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition, String type){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.type = type;
    }

}
