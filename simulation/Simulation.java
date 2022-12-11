/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import helpers.stats.StatsCollector;
import simulation.City.City;
import simulation.City.Hex;

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
		CEventList l = new CEventList();
		Queue q = new Queue();

		Source s1 = new Source(q, l, "A1");

		Source s2 = new Source(q, l, "A2");

		Source s3 = new Source(q, l, "B");

		Sink sink = new Sink("Sink 1");

		// We set ambulances for each hexagon
		for (int i = 0; i < City.getHexMap().size(); i++) {
			City.getHexMap().get(i).setAmbulances(createAmbulances(q, sink, l, i, N_MACHINES));
		}
		for (Hex hex : City.getHexMap()) {
			System.out.println("Number of ambulances: " + hex.getAmbulances().size());
		}

		// start the eventlist
		l.start(20); // 2000 is maximum time
		dumpDataToCSV(sink);
	}
	
	private static void dumpDataToCSV(Sink sink){
		String[] stations = sink.getStations();
		int[] numbers = sink.getNumbers();
		double[] times = sink.getTimestamps();
		String[] events = sink.getEvents();
	
		if((stations.length == numbers.length) && (times.length == events.length)){
			System.out.println("Same lengths");
		}
		for (int i = 0; i < stations.length ; i++) {
			System.out.println("\n"+
					"Event:" + events[i] + "\n" +
					"at Time " + times[i] + "\n" +
					"Stations " + stations[i] + "\n" +
					"Numbers " + numbers[i] + "\n"
			);
		}
		StatsCollector.dumpToFiles();
		writeCSV_Double(times, "C:/Users/demoadmin/Documents/study/ssa/bonus project/logs/times.csv");
		writeCSV_String(stations, "C:/Users/demoadmin/Documents/study/ssa/bonus project/logs/stations.csv");
		writeCSV_String(events, "C:/Users/demoadmin/Documents/study/ssa/bonus project/logs/events.csv");
		writeCSV_int(numbers, "C:/Users/demoadmin/Documents/study/ssa/bonus project/logs/numbers.csv");
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



	public static void writeCSV_Double(double[] data, String file) {

		try (PrintWriter writer = new PrintWriter(new File(file))) {

			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < data.length; i++){

				sb.append(data[i]);
				sb.append('\n');

			}
			writer.write(sb.toString());
		}

		catch (FileNotFoundException e) {

			System.out.println(e.getMessage());
		}
	}

	public static void writeCSV_String(String[] data, String file) {

		try (PrintWriter writer = new PrintWriter(new File(file))) {

			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < data.length; i++){

				sb.append(data[i]);
				sb.append('\n');

			}
			writer.write(sb.toString());
		}

		catch (FileNotFoundException e) {

			System.out.println(e.getMessage());
		}
	}

	public static void writeCSV_int(int[] data, String file) {

		try (PrintWriter writer = new PrintWriter(new File(file))) {

			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < data.length; i++){

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