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

        for (int i = 0; i < item_types.length; i++) {
            for (int j = 0; j < 3; j++) {
                Item tempItem = itemFactory.generateItem(item_types[i]);
                FNMS.addItemToInventory(tempItem);
            }
        }

    }

    public void simulate(int numDays) {

        Random rand = new Random();
        int numStaff = FNMS.getStaff().size();

        for (int i = 0; i < numDays; i++) {
            outerloop:
            while (true) {
                int randomStaffID = rand.nextInt(numStaff);
                if (FNMS.getStaff().get(randomStaffID).getConsecutiveDays() >= 3){
                    break;
                }
                for (int j = 0; j < numStaff; j++) {
                        if (j == randomStaffID) {
                                FNMS.getStaff().get(j).work(i);
                                FNMS.getStaff().get(j).incrementConsecutiveDays();
                                break outerloop;
                        }
                        else {
                            FNMS.getStaff().get(j).resetConsecutiveDays();
                        }
                }
            }
        }
    }
}