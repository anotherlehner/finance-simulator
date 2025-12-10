package finsim.event;

import finsim.Accounts;
import finsim.Event;

public class Static extends Event {
    private final String accountName;
    private final double amount;

    public Static(String name, Event.Purpose purpose, double amount, String accountName) {
        super(name, purpose);
        this.accountName = accountName;
        this.amount = amount;
    }

    public void run(int month, Accounts accounts) {
        accounts.get(accountName).add(amount);
    }

    @Override
    public String toString() {
        return "Static{" +
                "accountName='" + accountName + '\'' +
                ", amount=" + amount +
                '}';
    }
}