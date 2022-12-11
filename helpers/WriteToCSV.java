package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import simulation.Sink;

public class WriteToCSV {
    public static void dumpDataToCSV(Sink sink) {
        String[] names = sink.getStations();
        int[] numbers = sink.getNumbers();
        double[] timestamps = sink.getTimestamps();
        double[] queueWaitingTimes = sink.getQueueWaitingTimes();
        String[] events = sink.getEvents();

        if ((names.length == numbers.length) && (timestamps.length == events.length)) {
            System.out.println("Same lengths");
        }
        for (int i = 0; i < names.length; i++) {
            System.out.println("\n" +
                    "Event:" + events[i] + "\n" +
                    "at Time " + timestamps[i] + "\n" +
                    "Stations " + names[i] + "\n" +
                    " waited for " + queueWaitingTimes[i / 3] + "\n" +
                    "Numbers " + numbers[i / 3] + "\n");
        }

        writeCSV_Double(timestamps, "simulation/matlab/timestamps.csv");
        writeCSV_Double(queueWaitingTimes, "simulation/matlab/queueWaitTimes.csv");
        writeCSV_String(names, "simulation/matlab/stations.csv");
        writeCSV_String(events, "simulation/matlab/events.csv");
        writeCSV_int(numbers, "simulation/matlab/numbers.csv");
    }

    private static void writeCSV_Double(double[] data, String file) {

        try (PrintWriter writer = new PrintWriter(new File(file))) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < data.length; i++) {

                sb.append(data[i]);
                sb.append('\n');

            }
            writer.write(sb.toString());
        }

        catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void writeCSV_String(String[] data, String file) {

        try (PrintWriter writer = new PrintWriter(new File(file))) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < data.length; i++) {

                sb.append(data[i]);
                sb.append('\n');

            }
            writer.write(sb.toString());
        }

        catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void writeCSV_int(int[] data, String file) {

        try (PrintWriter writer = new PrintWriter(new File(file))) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < data.length; i++) {
                sb.append(data[i]);
                sb.append('\n');
            }
            writer.write(sb.toString());
        }

        catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
        }
    }
}
