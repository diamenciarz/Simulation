package helpers;

public class DebugLogger {
    static boolean doDebugMessages = true;

    public static void printArrived(double time) {
        if (doDebugMessages) {
            System.out.println("Arrival at time: " + (float) time);
        }
    }

    public static void printStartedProduction(double time, String machineName) {
        if (doDebugMessages) {
            System.out.println("Production started at machine " + machineName + " at time: " + (float) time);
        }
    }

    public static void printFinished(double time, String machineName) {
        if (doDebugMessages) {
            System.out.println("Product finished at " + machineName + " at time: " + (float) time);
        }
    }
}
