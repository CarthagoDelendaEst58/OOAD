package Items;

public abstract class Item {

    private String name;
    private double purchasePrice;
    private double listPrice;
    private boolean newOrUsed;
    private int dayArrived;
    private String condition;
    private double salePrice;
    private int daySold;

    public Item(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.listPrice = listPrice;
        this.newOrUsed = newOrUsed;
        this.dayArrived = dayArrived;
        this.condition = condition;
    }

}
