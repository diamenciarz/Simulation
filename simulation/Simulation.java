/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package simulation;

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
		double[] arrivalTimestamps = { 0.4, 1.2, 0.5, 1.7, 0.2, 1.6, 0.2, 1.4, 1.9 };
		Source source = new Source(queue, "Source 1", arrivalTimestamps);
		
		Sink sink = new Sink("Sink 1");
		double[] serviceTimestamps = { 2.0, 0.7, 0.2, 1.1, 3.7, 0.6, 4.0, 4.0, 4.0 };
		Machine machine = new Machine(queue, sink, "Machine 1", serviceTimestamps);

		EventList.start(2000); // 2000 is maximum time
	}

}
