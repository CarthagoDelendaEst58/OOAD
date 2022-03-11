package Main;

import Items.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClerkTest {
    CashRegister register = new CashRegister();
    Store store = new Store(register);
    TuningStrategy manual = new ManualTuning();
    Clerk clerk = new Clerk("someone", register, store, 0.1, manual);
    ItemFactory factory = new ItemFactory();

    @Test
    void sellItem() {
        Item newItem = factory.generateItem("BassItem");
        Customer customer = new Customer("somebody", false, "BassItem", false);
        store.addItemToInventory(newItem);

        int index = store.getInventory().size()-1;
        int initial_inventorySize = index+1;
        double initial_balance = register.getBalance();
        double sale_value = 100;

        clerk.sellItem(customer, newItem, sale_value, index);

        assert(store.getInventory().size() == initial_inventorySize-1 && register.getBalance() == initial_balance+sale_value);
    }
}