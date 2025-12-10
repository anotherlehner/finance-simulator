package finsim;

import finsim.exception.OutOfMoneyException;

public class BalanceNegativeStrategies {
    public static void roundRobin(int month, Accounts accounts) throws OutOfMoneyException {
        Account checking = accounts.get("checking");
        double debt = checking.getBalance() * -1;
        checking.setBalance(0);
        while (debt > 0) {
            if (accounts.balance() < 25000) {
                throw new OutOfMoneyException();
            } else {
                for (Account account : accounts.getAccounts()) {
                    if (account.getAccountType() == AccountType.Growth && account.getBalance() > 100 && debt > 0) {
                        account.add(-Math.min(100.0, debt));
                        debt -= Math.min(100.0, debt);
                    }
                }
            }
        }
    }
}
