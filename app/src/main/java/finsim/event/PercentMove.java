package finsim.event;

import finsim.Account;
import finsim.Accounts;
import finsim.Event;

public class PercentMove extends Event {
    private final double percent;
    private final String sourceAccount;
    private final String targetAccount;

    public PercentMove(String name, Event.Purpose purpose, double percent, String sourceAccount,
                       String targetAccount) {
        super(name, purpose);
        this.percent = percent;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    public void run(int month, Accounts accounts) {
        Account source = accounts.get(sourceAccount);
        double amount = source.getBalance() * percent;
        source.add(-amount);
        accounts.get(targetAccount).add(amount);
    }

    @Override
    public String toString() {
        return "PercentMove{" +
                "percent=" + percent +
                ", sourceAccount='" + sourceAccount + '\'' +
                ", targetAccount='" + targetAccount + '\'' +
                '}';
    }
}
