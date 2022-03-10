package Main;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class ClerkTimeCommandTest {
    private CashRegister register = new CashRegister();
    private Store store = new Store(register);
    private TuningStrategy haphazard = new HaphazardTuning();
    private TuningStrategy manual = new ManualTuning();
    private TuningStrategy electronic = new ElectronicTuning();

    private Command command = new ClerkTimeCommand();

    @Test
    void testCommand() {
        store.addClerk("someone", 0.1, manual);
        store.setActiveClerk(store.getStaff().get(0));
        command.setStore(store);
        command.execute();
        assert(command.response().equals(String.format("%d:%d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE))));
    }
}