package Main;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserSaleCommandTest {
    private CashRegister register = new CashRegister();
    private Store store = new Store(register);
    private TuningStrategy haphazard = new HaphazardTuning();
    private TuningStrategy manual = new ManualTuning();
    private TuningStrategy electronic = new ElectronicTuning();

    private Command command = new UserSaleCommand("BassItem");

    @Test
    void testCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("y\n".getBytes());
        System.setIn(in);
        store.addClerk("someone", 0.1, manual);
        store.setActiveClerk(store.getStaff().get(0));
        command.setStore(store);
        command.execute();
    }
}