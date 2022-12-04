package helpers;

import java.util.ArrayList;

import helpers.math.Vector2;

public class Printer {
    static boolean doDebugMessages = true;

    public static void printArrived(double time) {
        if (doDebugMessages) {
            System.out.println("Patient appeared at time: " + (float) time);
        }
    }

    public static void printStartedProduction(double time, String machineName) {
        if (doDebugMessages) {
            System.out.println("Patient picked up by ambulance " + machineName + " at time: " + (float) time);
        }
    }

    public static void printFinished(double time, String machineName) {
        if (doDebugMessages) {
            System.out.println("Patient dropped off by " + machineName + " at time: " + (float) time);
        }
    }

    public static void printQueueState(int queueLength){
        if (doDebugMessages) {
            System.out.println("There are " + queueLength + " elements in the queue");
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
