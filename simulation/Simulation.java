/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package simulation;

import helpers.RandGenerator;
import processes.EventList;
import processes.Machine;
import processes.Source;

public class Simulation {

	public EventList list;
	public Queue queue;
	public Source source;
	public Sink sink;
	public Machine mach;

	/**
	 * @param args the command line arguments
	 */

	public static void main(String[] args) {
		int machineCount = 3;
		int timestampCount = 10;
		double[][] timestamps = generateTimestamps(machineCount, timestampCount);
		Machine[] machines = generateMachines(machineCount, timestampCount, timestamps);

		EventList.start(2000); // 2000 is maximum time
	}

	private static double[][] generateTimestamps(int machineCount, int timestampCount) {
		double[][] timestamps = new double[machineCount][];
		for (int i = 0; i < machineCount; i++) {
			timestamps[i] = RandGenerator.generateDelays(timestampCount, 3, 6);
			printArray("Service timestamps: ", timestamps[i]);
		}

		return timestamps;
	}

	private static Machine[] generateMachines(int machineCount, int timestampCount, double[][] timestamps) {
		Queue queue = new Queue();
		Sink sink = new Sink("Sink 1");
		//The source saves a pointer to itself in the queue as an event
		Source source = new Source(queue, "Source 1", generateArrivalTimestamps(machineCount, timestampCount));
		Machine[] machines = new Machine[machineCount];
		for (int i = 0; i < machineCount; i++) {
			machines[i] = new Machine(queue, sink, "Machine " + (i + 1), timestamps[i]);
		}
		return machines;
	}

	private static double[] generateArrivalTimestamps(int machineCount, int timestampCount) {
		double[] arrivalTimestamps = RandGenerator.generateDelays(timestampCount * machineCount, 1, 3);
		printArray("Arrival timestamps: ", arrivalTimestamps);
		return arrivalTimestamps;
	}

	private static void printArray(String message, double[] arr) {
		System.out.print(message);
		for (double d : arr) {
			System.out.print(d + ", ");
		}
		System.out.println();
	}
}
