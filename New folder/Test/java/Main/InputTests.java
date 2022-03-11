package Main;

import Items.*;

import java.util.Scanner;

public class InputTests {
    private static CashRegister register = new CashRegister();
    private static Store store = new Store(register);
    private TuningStrategy haphazard = new HaphazardTuning();
    private static TuningStrategy manual = new ManualTuning();
    private TuningStrategy electronic = new ElectronicTuning();
    private static Command sell = new UserSaleCommand("BassItem");
    private static Command purchase = new UserPurchaseCommand("BassItem");

    public static void main(String[] args) {
        ItemFactory factory = new ItemFactory();
        store.addItemToInventory(factory.generateItem("BassItem"));
        store.addClerk("someone", 0.1, manual);
        store.setActiveClerk(store.getStaff().get(0));
        sell.setStore(store);
        purchase.setStore(store);

        Scanner in = new Scanner(System.in);
        System.out.print("Would you wish to sell or buy (exclusive or) an item? (sell/buy): ");
        String response = in.nextLine();
        if (response.equals("sell") || response.equals("Sell")) {
            sell.execute();
        }
        if (response.equals("buy") || response.equals("Buy")) {
            purchase.execute();
        }
    }
}
