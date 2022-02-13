package Main;

import Items.*;
import Items.ItemFactory;

import java.util.ArrayList;
import java.util.Random;

public class FNMS {

    private Store FNMS;
    private ItemFactory itemFactory;
    public static String[] item_types = {"CDPlayerItem", "MP3PlayerItem", "RecordPlayerItem", "VinylItem", "PaperScoreItem",
            "CDItem", "HatItem", "BandanaItem", "ShirtItem", "StringsItem", "CablesItem", "PracticeAmpItem",
            "FluteItem", "HarmonicaItem", "MandolinItem", "GuitarItem", "BassItem"};



    public void initialize() {

        FNMS = new Store();
        FNMS.addClerk("Velma", 5);
        FNMS.addClerk("Shaggy", 20);
        itemFactory = new ItemFactory();

        for (int i = 0; i < item_types.length;i++ ){
            for (int j = 0; j < 3; j++){
                Item tempItem = itemFactory.generateItem(item_types[i]);
                System.out.println(tempItem);
                FNMS.addItemToInventory(tempItem);
            }
        }

    }

    public void simulate(){
//        ArrayList<Item> inventory;
//        inventory = FNMS.getInventory();
//        System.out.println(inventory.get(1).getName());
//        System.out.println(inventory.get(15).getPurchasePrice());


        FNMS.getStaff().get(1).work(1);

    }
}
