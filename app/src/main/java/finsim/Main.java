package finsim;

public class Main {
    public static void main(String[] args) {
        try {
            // Run simulation
            new Simulator(40 * 12, "./output.csv").run();

            // Generate chart
            Runtime.getRuntime().exec("python3 chart.py");
        } catch (Exception _e) {
            _e.printStackTrace();
        }
    }
}
