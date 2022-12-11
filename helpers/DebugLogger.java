package helpers;

import java.util.ArrayList;
import simulation.Ambulance;
import simulation.Patient;
import helpers.math.Vector2;

public class DebugLogger {
    static boolean doDebugMessages = false;
    static boolean doConsoleOutputs = false;

    static int i = 0;
    static int j = 0;

    public static void printArrived(double time, String name) {
        i++;
        if (doDebugMessages) {
            System.out.println("Patient appeared at time: " + (float) time);
        }
    }

    public static void printStartedProduction(double time, Ambulance ambulance, Patient patient) {
        if (doDebugMessages) {
            System.out.println("Ambulance dispatched " + ambulance.name + " at time: " + (float) time);
        }
    }

    public static void printFinished(double time, String machineName) {
        j++;
        if (doDebugMessages) {
            System.out.println("Patient dropped off by " + machineName + " at time: " + (float) time);
        }
    }

    public static void printQueueState(int queueLength) {
        if (doDebugMessages) {
            System.out.println("There are " + queueLength + " elements in the queue");
        }
    }

    public static void printResponsePercentage(int count, int overTime) {
        double percentage = ((double) (count - overTime) / (double) count) * 100;
        System.out.println("Percentage of patients picked up within 15 minutes: " + percentage + "%");
    }

    public static void printShiftEnded(double time) {
        if (doDebugMessages) {
            System.out.println("Shift ended at time " + time);
        }
    }
    
    public static void printAddedShift(double time){
        if (doDebugMessages) {
            System.out.println("!!!!Shift started at " + time);
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

    public static void print(double[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }

    public static void print(ArrayList<Vector2> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i) + " ");
        }
    }

    public static void printSquare(double[] arr) {
        int modulus = (int) Math.sqrt(arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (i % modulus == 0) {
                System.out.println();
            }
            System.out.print(arr[i % modulus] + " ");
        }
        System.out.println();
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
