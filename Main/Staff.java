package Main;

class Staff {
    private String name;
    private int consecutiveDays;
    private CashRegister register;
    private Store store;

    Staff(String _name, CashRegister _register, Store _store) {
        name = _name;
        consecutiveDays = 0;
        register = _register;
        store = _store;
    }

    public String getName() {
        return name;
    }

    public int getConsecutiveDays() {
        return consecutiveDays;
    }

    public CashRegister getRegister() {
        return register;
    }

    public Store getStore() {
        return store;
    }
}
