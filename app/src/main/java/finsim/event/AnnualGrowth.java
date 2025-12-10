package finsim.event;

import finsim.Account;
import finsim.Accounts;
import finsim.Event;

public class AnnualGrowth extends Event {
    private final double rate;
    private final String accountName;
    private double amount = 0;

    public AnnualGrowth(String name, Event.Purpose purpose, double rate, String accountName) {
        super(name, purpose);
        this.rate = rate;
        this.accountName = accountName;
    }

    public void run(int month, Accounts accounts) {
        Account account = accounts.get(accountName);
        double balance = account.getBalance();
        if (month % 12 == 0) amount = (balance * rate) / 12;
        account.add(amount);
    }
}
