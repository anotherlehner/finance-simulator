package finsim;

import finsim.exception.NoSuchAccountException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static finsim.AccountType.Growth;
import static finsim.AccountType.Static;
import static finsim.Util.asStream;

public record Accounts(Collection<Account> accounts) {
    public static Accounts defaultAccounts() {
        return new Accounts(Arrays.asList(
                new Account(10000.0, Static, "checking"),
                new Account(53317.0, Growth, "savings")
        ));
    }

    public double balance() {
        return asStream(accounts).map(Account::getBalance).mapToDouble(Double::doubleValue).sum();
    }

    public Account get(String name) {
        return asStream(accounts)
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElseThrow(NoSuchAccountException::new);
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public String getBalancesString() {
        return accounts.stream()
                .map(Account::getBalance)
                .map(Util::formatBalance)
                .collect(Collectors.joining(","));
    }
}
