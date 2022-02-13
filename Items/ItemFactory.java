package Items;


import java.util.Random;

public class ItemFactory {

    public static String[] item_types = {"CDPlayerItem", "MP3PlayerItem", "RecordPlayerItem", "VinylItem", "PaperScoreItem",
            "CDItem", "HatItem", "BandanaItem", "ShirtItem", "StringsItem", "CablesItem", "PracticeAmpItem",
            "FluteItem", "HarmonicaItem", "MandolinItem", "GuitarItem", "BassItem"};
    public static String[] conditions = {"poor", "fair", "good", "very good", "excellent"};



    public Item createItem(String type, String name, double purchasePrice, double listPrice, boolean newOrUsed, int dayArrived, String condition,
                           String band, String album, boolean isElectric, String fluteType, String harmonicaKey, int hatSize, int shirtSize,
                           double ampWattage, double cableLength, String stringsType){
        if (type == null || type.isEmpty()) {
            return null;
        }

        if (("BandanaItem").equals(type)){
            return new BandanaItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        }

        if (("BassItem").equals(type)){
            return new BassItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, isElectric);
        }

        if (("CablesItem").equals(type)){
            return new CablesItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, cableLength);
        }

        if (("CDItem").equals(type)){
            return new CDItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, band, album);
        }

        if (("CDPlayerItem").equals(type)){
            return new CDPlayerItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        }

        if (("FluteItem").equals(type)){
            return new FluteItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition,fluteType);
        }

        if (("GuitarItem").equals(type)){
            return new GuitarItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, isElectric);
        }

        if (("HarmonicaItem").equals(type)){
            return new HarmonicaItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, harmonicaKey);
        }

        if (("HatItem").equals(type)){
            return new HatItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, hatSize);
        }

        if (("MandolinItem").equals(type)){
            return new MandolinItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, isElectric);
        }

        if (("MP3PlayerItem").equals(type)){
            return new MP3PlayerItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        }

        if (("PaperScoreItem").equals(type)){
            return new PaperScoreItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, band, album);
        }

        if (("PracticeAmpItem").equals(type)){
            return new PracticeAmpItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, ampWattage);
        }

        if (("RecordPlayerItem").equals(type)){
            return new RecordPlayerItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition);
        }

        if (("ShirtItem").equals(type)){
            return new ShirtItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, shirtSize);
        }

        if (("StringsItem").equals(type)){
            return new StringsItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, stringsType);
        }

        if (("VinylItem").equals(type)){
            return new VinylItem(name, purchasePrice, listPrice, newOrUsed, dayArrived, condition, band, album);
        }

        return null;
    }

    public Item generateItem(String type){
        Random rand = new Random();
        double itemNumber = rand.nextDouble()*200+1;

        String itemName = "Item #" +(int)itemNumber;
        double itemPurchasePrice = rand.nextDouble() * 49 + 1;
        double itemListPrice = itemPurchasePrice*2;
        boolean newOrUsed;
        if (Math.random() < 0.5){
            newOrUsed = true;
        }
        else {
            newOrUsed = false;
        }

        int dayArrived = 0;
        String condition =  conditions[rand.nextInt(5)];

        Item newItem = createItem(item_types[rand.nextInt(item_types.length)], itemName, itemPurchasePrice, itemListPrice, newOrUsed, dayArrived, condition,
                "Nirvana", "Nevermind", true, "Wood", "B", 5, 10,
                100.00, 10.00, "Nylon");
        return newItem;

    }
}
