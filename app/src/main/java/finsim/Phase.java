package finsim;

import finsim.event.Static;

import java.util.Map;
import java.util.function.Function;

import static finsim.Event.Purpose.Income;
import static finsim.EventType.Monthly;

public class Phase {
    public static final String STOPPED = "stopped";
    public static final String TEST_THRESHOLD = "test";
    public static final String WORKING = "working";
    public static final String RETIRED = "retired";

    public static Map<String, Function<Scenario, String>> defaultPhaseChangeFunctions() {
        return Map.of(
                WORKING, Phase::defaultWorkingPhaseChangeFunction,
                TEST_THRESHOLD, Phase::defaultTestPhaseChangeFunction,
                STOPPED, Phase::defaultStoppedPhaseChangeFunction,
                RETIRED, Phase::defaultRetiredPhaseChangeFunction);
    }

    public static Map<String, Function<Events, Events>> defaultEventsChangeFunctions() {
        return Map.of(
                WORKING, Phase::defaultEventsChangeToWorking,
                TEST_THRESHOLD, Phase::defaultEventsChangeToTestThreshold,
                STOPPED, Phase::defaultEventsChangeToStopped,
                RETIRED, Phase::defaultEventsChangeToRetired);
    }

    public static String defaultWorkingPhaseChangeFunction(Scenario scenario) {
        if (scenario.getBalance() > scenario.getTestThreshold())
            return TEST_THRESHOLD;
        else
            return WORKING;
    }

    public static String defaultStoppedPhaseChangeFunction(Scenario scenario) {
        return STOPPED;
    }

    public static String defaultTestPhaseChangeFunction(Scenario scenario) {
        if (scenario.isTodayRetirementDay())
            return RETIRED;
        else
            return TEST_THRESHOLD;
    }

    public static String defaultRetiredPhaseChangeFunction(Scenario scenario) {
        return RETIRED;
    }

    public static Events defaultEventsChangeToWorking(Events events) {
        return events;
    }

    public static Events defaultEventsChangeToTestThreshold(Events events) {
        return events;
    }

    public static Events defaultEventsChangeToRetired(Events events) {
        // Testing events added when phase changes to certain phases
        return events.withPrependEvent(Monthly, new Static("test", Income, 1234.0, "checking"));
    }

    public static Events defaultEventsChangeToStopped(Events events) {
        events.clear();
        return events;
    }
}
