package finsim.scenario;

import finsim.Events;
import finsim.Phase;
import finsim.Scenario;
import finsim.event.InflatingAmt;

import static finsim.Event.Purpose.IncomeTax;
import static finsim.EventType.Monthly;
import static finsim.Phase.RETIRED;
import static finsim.Phase.TEST_THRESHOLD;

public class TestScenario extends Scenario {
    public TestScenario() {
        super("testscenario");
        this.setTestThreshold(1250000.0);
    }

    @Override
    protected void onPhaseChange(int month, String currentPhase, String toPhase) {
        // TODO: figure out a better way of handling directional changes and responses during simulation
        if (toPhase.equals(TEST_THRESHOLD))
            events = Events.defaultEvents()
                    .withReplace(Monthly, events.getEvent(Monthly, "rent"))
                    .withReplace(Monthly, events.getEvent(Monthly, "spending"))
                    .withMonthly(new InflatingAmt("retired income tax", IncomeTax, -800.0, 0.01,
                            "checking"));
        else if (toPhase.equals(RETIRED))
            Phase.defaultEventsChangeToRetired(events);
    }
}
