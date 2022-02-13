package Items;

public class ItemFactory {
    public Item createItem(String type, String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition,
                           String band, String album, boolean isElectric, String fluteType, String harmonicaKey, int hatSize, int shirtSize,
                           double ampWattage, double cableLength, String stringsType){
        if (type == null || type.isEmpty())
            return null;
        if (("Bass").equals(type)){
            return new BassItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, isElectric);
        }
        return null;
    }
}
