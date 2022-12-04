/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import Simulation.City.City;
import Simulation.City.Hex;

import java.util.ArrayList;

public class Simulation {
	public static int N_MACHINES = 3;
	public CEventList list;
	public Queue queue;
	public Source source;
	public Sink sink;
	public Machine mach;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// Create an eventlist
		CEventList l = new CEventList();
		Queue q = new Queue();
		Source s = new Source(q, l, "Source 1");
		// Source s = new Source(q, l, "Source 2");
		Sink si = new Sink("Sink 1");

		City city = new City();

		// We set ambulances for each hexagon
		for (int i = 0; i < City.city.size(); i++) {
			City.city.get(i).setAmbulances(createMachines(q, si, l, i, N_MACHINES));
		}

		// start the eventlist
		l.start(200); // 2000 is maximum time
	}

	public static ArrayList<Machine> createMachines(Queue queue, Sink sink, CEventList eventList, int hex_index,
			int ambulance_count) {

		ArrayList<Machine> machines = new ArrayList<>();
		for (int i = 1; i <= ambulance_count; i++) {
			Machine ambulance = new Machine(queue, sink, eventList, "Machine_" + i + "_H_" + hex_index);
			machines.add(ambulance);
			Hex hubPosition =City.city.get(hex_index);
			ambulance.setHub(hubPosition.location);
		}
		return machines;
	}

}
