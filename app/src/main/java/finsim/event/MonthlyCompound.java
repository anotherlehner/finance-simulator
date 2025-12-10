package finsim.event;

import finsim.Account;
import finsim.Accounts;
import finsim.Event;

public class MonthlyCompound extends Event {
    private final double rate;
    private final String accountName;

    public MonthlyCompound(String name, Event.Purpose purpose, double rate, String accountName) {
        super(name, purpose);
        this.rate = rate;
        this.accountName = accountName;
    }

    public void run(int month, Accounts accounts) {
        Account a = accounts.get(accountName);
        a.add(a.getBalance() * rate);
    }

    @Override
    public String toString() {
        return "MonthlyCompound{" +
                "rate=" + rate +
                ", accountName='" + accountName + '\'' +
                '}';
    }
}
