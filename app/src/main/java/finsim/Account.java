package finsim;

import finsim.exception.BalanceNegativeException;

public class Account {
    private final AccountType accountType;
    private final String name;
    private double balance;

    public Account(double balance, AccountType accountType, String name) {
        this.balance = balance;
        this.accountType = accountType;
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void add(double amount) {
        balance += amount;
        if (balance < 0) throw new BalanceNegativeException();
    }
}
