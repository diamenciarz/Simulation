package simulation;

public class DebugLogger {
    static boolean doDebugMessages = true;

    public static void printArrived(double time) {
        if (doDebugMessages) {
            System.out.println("Arrival at time = " + time);
        }
    }

    public static void printFinished(double time) {
        if (doDebugMessages) {
            System.out.println("Product finished at time = " + time);
        }
    }
}
