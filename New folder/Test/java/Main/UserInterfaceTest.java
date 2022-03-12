package Main;

class UserInterfaceTest {
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

    private static FNMS fnms = new FNMS();

    private static void setup() {
        fnms.initialize();
        for (Store store : FNMS.getStores()) {
            fnms.getAvailableClerk(store);
        }
    }

    private static void interfaceTest() {
        UserInterface ui = new UserInterface();
        ui.mainloop();
    }

    public static void main(String[] args) {
        setup();
        interfaceTest();
    }
}