package Main;

public class InputTests {
    private static CashRegister register = new CashRegister();
    private static Store store = new Store(register);
    private TuningStrategy haphazard = new HaphazardTuning();
    private static TuningStrategy manual = new ManualTuning();
    private TuningStrategy electronic = new ElectronicTuning();
    private static Command command = new UserSaleCommand("BassItem");

    public static void main(String[] args) {
        store.addClerk("someone", 0.1, manual);
        store.setActiveClerk(store.getStaff().get(0));
        command.setStore(store);
        command.execute();
    }
}
