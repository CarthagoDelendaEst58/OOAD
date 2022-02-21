package Main;


public class CashRegister {
    private double balance;

    CashRegister() {
        balance = 0;
    }

    public void alterBalance(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}