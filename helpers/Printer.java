package helpers;

public class Printer {
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

    public static void print(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printSquare(double[] arr) {
        int modulus = (int) Math.sqrt(arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i % modulus]);
        }
    }

    public static void printSquare(float[] arr) {
        int modulus = (int) Math.sqrt(arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (i % modulus == 0) {
                System.out.println();
            }
            System.out.print(arr[i % modulus] + " ");
        }
        System.out.println();
    }
}
