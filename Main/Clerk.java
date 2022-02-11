package Main;

public class Clerk extends Staff {

    Clerk(String _name, CashRegister _register, Store _store) {
        super(_name, _register, _store);
    }

    public void work(int day) {
        arriveAtStore(day);
        checkRegister();
    }

    private void arriveAtStore(int day) {
        System.out.println(String.format("%s has arrived at the store on day %d", getName(), day));
        // ArrayList<Item> inDelivery = getStore().getItemsInDelivery();
        // for (int i = 0; i < inDelivery.size(); i++) {
        //     if (inDelivery.get(i).getDayArrived() == day) {
        //         getStore().addItemToInventory(inDelivery.get(i));
        //         inDelivery.remove(i);
        //     }
        // }
    }

    private void checkRegister() {
        double register_balance = getRegister().getBalance();
        System.out.println(String.format("%s has counted a total of $%f in the register", getName(), register_balance));

        if (register_balance < 75) {
            goToBank();
        }
    }

    private void goToBank() {
        getRegister().alterBalance(1000);
        System.out.println(String.format("%s has added $1000 to the register", getName()));
    }
}