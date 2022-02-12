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

    public String getName(){
        return name;
    }
    public double getPurchasePrice(){
        return purchasePrice;
    }

    public void setPurchasePrice(double newPurchasePrice){
        purchasePrice = newPurchasePrice;
    }

    public double getListPrice(){
        return listPrice;
    }
    public void setListPrice(double newListPrice){
        listPrice = newListPrice;
    }
    public boolean getNewOrUsed(){
        return newOrUsed;
    }
    public int getDayArrived(){
        return dayArrived;
    }
    public void setDayArrived(int newDayArrived){
        dayArrived = newDayArrived;
    }

    public String getCondition(){
        return condition;
    }

    public void setCondition(String newCondition){
        condition = newCondition;
    }

    public double getSalePrice(){
        return salePrice;
    }

    public void setSalePrice(double newSalePrice){
        salePrice = newSalePrice;
    }

    public int getDaySold(){
        return daySold;
    }

    public void setDaySold(int newDaySold){
        daySold = newDaySold;
    }


}
