/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package simulation;

import java.util.ArrayList;

import helpers.CrewScheduler;
import helpers.WriteToCSV;
import simulation.city.City;
import simulation.city.Hex;

public class Simulation {
	public static int N_MACHINES = 1;
	public CEventList list;
	public Queue queue;
	public Source source;
	public Sink sink;
	public Ambulance mach;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		City city = new City();
		// Create an eventlist
		CEventList eventList = new CEventList();
		Queue queue = new Queue();

		Source s1 = new Source(queue, eventList, "A1");

		Source s2 = new Source(queue, eventList, "A2");

		Source s3 = new Source(queue, eventList, "B");

		Sink sink = new Sink("Sink 1");

		CrewScheduler.eventList = eventList;
		CrewScheduler.queue = queue;
		CrewScheduler.sink = sink;

		// We set ambulances for each hexagon
		for (int i = 0; i < City.getHexMap().size(); i++) {
			City.getHexMap().get(i).setAmbulances(createAmbulances(queue, sink, eventList, i, N_MACHINES));
		}
		for (Hex hex : City.getHexMap()) {
			System.out.println("Number of ambulances: " + hex.getAmbulances().size());
		}

		// start the eventlist
		eventList.start(20); // 2000 is maximum time
		WriteToCSV.dumpDataToCSV(sink);
	}

	public static ArrayList<Ambulance> createAmbulances(Queue queue, Sink sink, CEventList eventList, int hexIndex,
			int ambulanceCount) {

		ArrayList<Ambulance> machines = new ArrayList<>();
		for (int i = 1; i <= ambulanceCount; i++) {
			Ambulance ambulance = new Ambulance(queue, sink, eventList, "Machine " + i + " H " + hexIndex);
			machines.add(ambulance);
			Hex hub = City.getHexMap().get(hexIndex);
			ambulance.setHub(hub);
		}
		return machines;
	}

}