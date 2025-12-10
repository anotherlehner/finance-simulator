package finsim;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    public static final DecimalFormat decimalFormat = new DecimalFormat("###.##");

    public static <T> Stream<T> asStream(Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }

    public static String formatBalance(double balance) {
        return decimalFormat.format(balance);
    }

    public static double houseMaintMonthly(double houseValue) {
        return -(houseValue * Constant.HOUSE_MAINT_RATE / 12);
    }

    public static String getCsvHeader(List<Scenario> scenarios) {
        List<String> scenarioNames = scenarios.stream().map(Scenario::getName).collect(Collectors.toList());
        scenarioNames.add(0, "index");
        return String.join(",", scenarioNames);
    }

    public static String getCsvLine(int index, List<Scenario> scenarios) {
        List<String> balances = scenarios.stream()
                .map(s -> formatBalance(s.getBalance()))
                .collect(Collectors.toList());
        return "" + index + "," + String.join(",", balances);
    }

    public static void verifyBalanceCompare(String name, long balance, long expectedBalance) {
        if (balance != expectedBalance)
            System.out.printf("ERROR: %s mismatch: %s != %s\n", name, balance, expectedBalance);
        else
            System.out.printf("SUCCESS: %s %s == %s\n", name, balance, expectedBalance);
    }

    public static void verifyFinalBalances(List<Scenario> scenarios) {
        verifyBalanceCompare("testscenario", Math.round(scenarios.get(0).getBalance()), 123456789);
    }
}
