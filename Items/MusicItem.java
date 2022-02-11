package Items;

class MusicItem extends Item {

    private String band;
    private String album;

    public MusicItem(String name, int purchasePrice, int listPrice, boolean newOrUsed, int dayArrived, String condition, double salePrice, int daySold){
        super(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, salePrice, daySold);


    }
}
