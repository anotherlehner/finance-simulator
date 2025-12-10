package finsim.event;

import finsim.Accounts;
import finsim.Event;

public class Move extends Event {
    private final double amount;
    private final String sourceAccount;
    private final String targetAccount;

    public Move(String name, Event.Purpose purpose, double amount, String sourceAccount, String targetAccount) {
        super(name, purpose);
        this.amount = amount;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    public void run(int month, Accounts accounts) {
        accounts.get(sourceAccount).add(-amount);
        accounts.get(targetAccount).add(amount);
    }

    @Override
    public String toString() {
        return "Move{" +
                "amount=" + amount +
                ", sourceAccount='" + sourceAccount + '\'' +
                ", targetAccount='" + targetAccount + '\'' +
                '}';
    }
}
