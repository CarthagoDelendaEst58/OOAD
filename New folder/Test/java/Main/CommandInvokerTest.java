package Main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CommandInvokerTest {
    private static final CashRegister register1 = new CashRegister();
    private static final Store store1 = new Store(register1);
    private static final CashRegister register2 = new CashRegister();
    private static final Store store2 = new Store(register2);

    private static final TuningStrategy haphazard = new HaphazardTuning();
    private static final TuningStrategy manual = new ManualTuning();
    private static final TuningStrategy electronic = new ElectronicTuning();

    private static CommandInvoker invoker = new CommandInvoker(store1);

    private static final Command nameCommand = new ClerkNameCommand();
    private static final Command timeCommand = new ClerkTimeCommand();
    private static Command storeCommand = new SelectStoreCommand(invoker);



    @BeforeAll
    static void setup() {
        store1.addClerk("someone", 0.1, manual);
        store1.setActiveClerk(store1.getStaff().get(0));
        store2.addClerk("someoneelse", 0.2, haphazard);
        store2.setActiveClerk(store2.getStaff().get(0));
    }

    @Test
    void testNameCommand() {
        invoker.setCommand(nameCommand);
        assert(invoker.response().equals("someone"));
    }

    @Test
    void testTimeCommand() {
        invoker.setCommand(timeCommand);
        assert(invoker.response().equals(String.format("%d:%d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE))));
    }

    @Test
    void testStoreSwitch() {
        invoker.setCommand(nameCommand);
        String r1 = invoker.response();

        storeCommand.setStore(store2);
        invoker.setCommand(storeCommand);
        invoker.setCommand(nameCommand);

        String r2 = invoker.response();

        assert (r1.equals("someone") && r2.equals("someoneelse"));
    }
}