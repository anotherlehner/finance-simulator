package finsim.event;

import finsim.Accounts;
import finsim.Event;

public class InflatingAmt extends Event {
    private final double rate;
    private final String accountName;
    private double amount;

    public InflatingAmt(String name, Event.Purpose purpose, double amount, double rate, String accountName) {
        super(name, purpose);
        this.rate = rate;
        this.accountName = accountName;
        this.amount = amount;
    }

    public void run(int month, Accounts accounts) {
        if (month != 0 && month % 12 == 0) amount += amount * rate;
        accounts.get(accountName).add(amount);
    }

    @Override
    public String toString() {
        return "InflatingAmt{" +
                "rate=" + rate +
                ", accountName='" + accountName + '\'' +
                ", amount=" + amount +
                '}';
    }
}