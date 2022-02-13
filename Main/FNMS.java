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

    private ArrayList<Clerk> getAvailableClerk() {
        ArrayList<Clerk> available = new ArrayList<Clerk>();
        for (Clerk staff : FNMS.getStaff()) {
            if (staff.getConsecutiveDays() < 3) {
                available.add(staff);
            }
        }
        return available;
    }

    public void simulate(int numDays) {

        Random rand = new Random();

        for (int day = 1; day <=30; day++ ) {
            ArrayList<Clerk> available = getAvailableClerk();
            Clerk the_chosen_one = available.get(rand.nextInt(available.size()));

            for (Staff staff : FNMS.getStaff()) {
                if (!staff.getName().equals(the_chosen_one.getName())) {
                    staff.resetConsecutiveDays();
                }
            }
            the_chosen_one.work(day);
        }

//        int numStaff = FNMS.getStaff().size();
//
//        for (int i = 0; i < numDays; i++) {
//            boolean staffFound = false;
//            while (!staffFound) {
//                int randomStaffID = rand.nextInt(numStaff);
//                if (FNMS.getStaff().get(randomStaffID).getConsecutiveDays() < 3){
//                    staffFound = true;
//                    for (int j = 0; j < numStaff; j++) {
//                        if (j == randomStaffID) {
//                            FNMS.getStaff().get(j).work(i);
//                            FNMS.getStaff().get(j).incrementConsecutiveDays();
//                            break;
//                        }
//                        else {
//                            FNMS.getStaff().get(j).resetConsecutiveDays();
//                        }
//                    }
//                }
//
//            }
//        }
    }
}