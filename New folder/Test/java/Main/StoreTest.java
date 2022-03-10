package Main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    private CashRegister register = new CashRegister();
    private Store store = new Store(register);
    private TuningStrategy haphazard = new HaphazardTuning();
    private TuningStrategy manual = new ManualTuning();
    private TuningStrategy electronic = new ElectronicTuning();

    boolean clerkEquality(Clerk c1, Clerk c2) {
        return (c1.getStore() == c2.getStore()) && (c1.getRegister() == c2.getRegister()) && (c1.getName().equals(c2.getName())) && (c1.getStrategy() == c2.getStrategy()) && (c1.getDamageChance() == c2.getDamageChance());
    }

    @Test
    void addingStaff() {
        Clerk testClerk = new Clerk("someone", register, store, 0.1, manual);
        store.addClerk("someone", 0.1, manual);
        store.addClerk("someoneelse", 0.2, haphazard);
        Clerk c1 = store.getStaff().get(0);
        Clerk c2 = store.getStaff().get(1);
        assert(clerkEquality(testClerk, c1));
        assert(!clerkEquality(testClerk, c2));
    }
}