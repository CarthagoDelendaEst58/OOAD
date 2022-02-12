package Items;

public abstract class Music extends Item {

    private String band;
    private String album;

    public Music(String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition, String band, String album){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        this.band = band;
        this.album = album;


    }
}
