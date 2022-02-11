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

    public Item(String _name, double _purchasePrice, double _listPrice, boolean _newOrUsed, int _dayArrived, String _condition, double _salePrice, int _daySold) {
        name = _name;
        purchasePrice = _purchasePrice;
        listPrice = _listPrice;
        newOrUsed = _newOrUsed;
        dayArrived = _dayArrived;
        condition = _condition;
        salePrice = _salePrice;
        daySold = _daySold;
    }


    //
//    public int getDayArrived() { return dayArrived; }

}
