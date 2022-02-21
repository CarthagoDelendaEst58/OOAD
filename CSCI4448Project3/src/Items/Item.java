package Items;

import java.util.Arrays;

public abstract class Item {

    private String name;
    private double purchasePrice;
    private double listPrice;
    private boolean newOrUsed;
    private int dayArrived;
    private String condition;
    private double salePrice;
    private int daySold;
    private boolean adjusted;
    private boolean tuned;
    private boolean equalized;

    private static String[] stringed = {"MandolinItem", "BassItem", "GuitarItem"};
    private static String[] wind = {"SaxophoneItem", "FluteItem", "HarmonicaItem"};
    private static String[] players = {"CDPlayerItem", "RecordPlayerItem", "MP3PlayerItem", "CassettePlayerItem"};

    public Item(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.listPrice = listPrice;
        this.newOrUsed = newOrUsed;
        this.dayArrived = dayArrived;
        this.condition = condition;
        adjusted = false;
        tuned = false;
        equalized = false;
    }

    // https://stackoverflow.com/questions/6271417/java-get-the-current-class-name
    public String getClassName() {
        return this.getClass().getSimpleName();
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

    public boolean getAdjusted() { return adjusted; }
    public void setAdjusted(boolean state) { adjusted = state;}

    public boolean getTuned() { return tuned; }
    public void setTuned(boolean state) { tuned = state; }

    public boolean getEqualized() { return equalized; }
    public void setEqualized(boolean state) { equalized = state; }

    public static boolean isStringed(String type) {
        if (Arrays.asList(stringed).contains(type)) {
            return true;
        }
        return false;
    }

    public static boolean isWind(String type) {
        if (Arrays.asList(wind).contains(type)) {
            return true;
        }
        return false;
    }

    public static boolean isPlayer(String type) {
        if (Arrays.asList(players).contains(type)) {
            return true;
        }
        return false;
    }
}
