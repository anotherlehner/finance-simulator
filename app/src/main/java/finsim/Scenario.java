package finsim;

import finsim.exception.BalanceNegativeException;
import finsim.exception.OutOfMoneyException;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

import static finsim.Phase.STOPPED;
import static finsim.Phase.WORKING;

public abstract class Scenario {
    private final String name;
    private final Accounts accounts;
    private final Map<String, Function<Scenario, String>> phaseChangeFunctions;
    protected Events events;
    private double testThreshold = Constant.DEFAULT_LIMIT;
//    private final Map<String, Function<Events, Events>> eventsChangeFunctions;
    private int month = 0;
    private String currentPhase = WORKING;

    public Scenario(String name) {
        this(name, Accounts.defaultAccounts());
    }

    public Scenario(String name, Accounts accounts) {
        this.name = name;
        this.accounts = accounts;
        this.events = Events.defaultWorkingEvents();
        this.phaseChangeFunctions = Phase.defaultPhaseChangeFunctions();
//        this.eventsChangeFunctions = Phase.defaultEventsChangeFunctions();
    }

    private void runEvents(int month, EventType eventType) throws OutOfMoneyException {
        for (Event event : events.getChain(eventType)) {
            try {
                event.run(month, accounts);
            } catch (BalanceNegativeException _bne) {
                BalanceNegativeStrategies.roundRobin(month, accounts);
            }
        }
    }

    public void run(int month, EventType eventType) {
        this.month = month;
        maybePhaseChange();
        try {
            runEvents(month, eventType);
            if (eventType == EventType.Monthly)
                runEvents(month, EventType.MonthlyRemainder);
        } catch (OutOfMoneyException _e) {
            stop(_e);
        }
    }

    public void phaseTransition(String to) {
        onPhaseChange(month, currentPhase, to);
        currentPhase = to;
    }

    public void stop() {
        phaseTransition(STOPPED);
    }

    public void stop(Exception _e) {
        System.out.println(name + " exception: " + _e.getMessage());
        _e.printStackTrace();
        events.clear();
        stop();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return accounts.balance();
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public boolean isTodayRetirementDay() {
        LocalDate today = LocalDate.now().plusMonths(month);
        return today.getYear() == Constant.RETIREMENT_DATE.getYear()
                && today.getMonth() == Constant.RETIREMENT_DATE.getMonth();
    }

    public boolean isStopped() {
        return currentPhase.equals(STOPPED);
    }

    public boolean isPhase(String phaseName) {
        return currentPhase.equals(phaseName);
    }

    private void maybePhaseChange() {
        String nextPhase = onMaybePhaseChange();
        if (!nextPhase.equals(currentPhase))
            phaseTransition(nextPhase);
    }

    public double getTestThreshold() {
        return testThreshold;
    }

    public void setTestThreshold(double testThreshold) {
        this.testThreshold = testThreshold;
    }

    // ========= HOOKS ==========

    protected String onMaybePhaseChange() {
        // intentionally left empty
        return phaseChangeFunctions.get(currentPhase).apply(this);
    }

    protected void onPhaseChange(int month, String currentPhase, String toPhase) {
        // intentionally left empty
    }
}
