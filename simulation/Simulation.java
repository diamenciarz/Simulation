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
		Queue queue = new Queue();
		double[] arrivalTimestamps = RandGenerator.generateDelays(20, 1, 2);
		printArray("Arrival timestamps: ", arrivalTimestamps);
		Source source = new Source(queue, "Source 1", arrivalTimestamps);

		Sink sink = new Sink("Sink 1");
		double[] serviceTimestamps = RandGenerator.generateDelays(10, 1, 4);
		printArray("Service timestamps: ", serviceTimestamps);
		Machine machine = new Machine(queue, sink, "Machine 1", serviceTimestamps);

		double[] serviceTimestamps2 = RandGenerator.generateDelays(10, 1, 4);
		printArray("Service timestamps: ", serviceTimestamps2);
		Machine machine2 = new Machine(queue, sink, "Machine 2", serviceTimestamps2);

		EventList.start(2000); // 2000 is maximum time
	}

	private static void printArray(String message, double[] arr) {
		System.out.print(message);
		for (double d : arr) {
			System.out.print(d + ", ");
		}
		System.out.println();
	}
}
