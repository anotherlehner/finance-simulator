package finsim;

import finsim.scenario.TestScenario;

import java.io.PrintWriter;
import java.util.List;

import static finsim.EventType.*;

public class Simulator {
    private final int monthsToRun;
    private final String csvFilename;
    private final List<Scenario> scenarios;
    private int curMonth;

    public Simulator(int monthsToRun, String csvFilename) {
        this.monthsToRun = monthsToRun;
        this.csvFilename = csvFilename;

        scenarios = List.of(
                new TestScenario());
    }

    public void runScenarios(EventType eventType) {
        scenarios.forEach(s -> s.run(curMonth, eventType));
    }

    public void run() {
        try (PrintWriter pw = new PrintWriter(csvFilename)) {
            pw.println(Util.getCsvHeader(scenarios));
            for (curMonth = 0; curMonth < monthsToRun; curMonth++) {
                if (curMonth == 0)
                    runScenarios(Initial);
                runScenarios(Monthly);
                if (curMonth == monthsToRun - 1)
                    runScenarios(Final);
                if (curMonth % 12 == 0)
                    pw.println(Util.getCsvLine(curMonth, scenarios));
            }
            Util.verifyFinalBalances(scenarios);
        } catch (Exception _e) {
            _e.printStackTrace();
        }
    }
}
