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
        TuningStrategy haphazard = new HaphazardTuning();
        TuningStrategy manual = new ManualTuning();
        TuningStrategy electronic = new ElectronicTuning();

        FNMS = new Store();
        FNMS.addClerk("Velma", 5, manual);
        FNMS.addClerk("Shaggy", 20, haphazard);
        itemFactory = new ItemFactory();

        for (int i = 0; i < item_types.length; i++) {
            for (int j = 0; j < 3; j++) {
                Item tempItem = itemFactory.generateItem(item_types[i]);
                FNMS.addItemToInventory(tempItem);
            }
        }

        System.out.println(FNMS.getInventory());

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

        for (int day = 1; day <= numDays; day++) {
            FNMS.incrementDay();
            if (day % 7 == 0){
                System.out.println("No one works on Sundays");
                continue;
            }
            else {
                ArrayList<Clerk> available = getAvailableClerk();
                Clerk the_chosen_one = available.get(rand.nextInt(available.size()));

                for (Staff staff : FNMS.getStaff()) {
                    if (!staff.getName().equals(the_chosen_one.getName())) {
                        staff.resetConsecutiveDays();
                    }
                }
                the_chosen_one.work(day);
            }
        }
    }

        public void summary(int numDays){
            System.out.println("Here is a summary of the status at the store at the end of " + numDays +  " days.");

            int numItems = FNMS.getInventory().size();
            double itemsValue = 0.0;
            for (int i = 0; i < FNMS.getInventory().size(); i++){
                itemsValue = itemsValue + FNMS.getInventory().get(i).getListPrice();
            }

            System.out.println("The number of items sold was " + numItems + ", with value $" + String.format("%.2f",itemsValue ));

            double soldValue = 0.0;
            System.out.println("Here are the Items that were sold");
            for (int i = 0; i < FNMS.getSoldItems().size(); i++){
                Item soldItem = FNMS.getSoldItems().get(i);
                System.out.println(soldItem.getName() + " was sold on day " + soldItem.getDaySold() + " for total $" + String.format("%.2f",(soldItem.getSalePrice())));
                soldValue += soldItem.getSalePrice();
            }

            System.out.println("The total amount sold was $" + String.format("%.2f", soldValue));
//
//        System.out.println(value);

    }
    }
