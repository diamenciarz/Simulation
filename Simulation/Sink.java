package simulation;

import java.util.ArrayList;

/**
 * A sink
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Sink implements ProductAcceptor {
	/** All products are kept */
	private ArrayList<Patient> products = new ArrayList<>();
	/** All properties of products are kept */
	private ArrayList<Integer> numbers = new ArrayList<>();
	public ArrayList<Double> travelTimes = new ArrayList<>();
	private ArrayList<Double> timestamps = new ArrayList<>();
	private ArrayList<String> events = new ArrayList<>();
	private ArrayList<String> stations = new ArrayList<>();
	private ArrayList<Double> queueWaitingTimes = new ArrayList<>();
	/** Counter to number products */
	private int number = 0;
	/** Name of the sink */
	private String name;

	/**
	 * Constructor, creates objects
	 */
	public Sink(String n) {
		name = n;
	}

	@Override
	public boolean givePatient(Patient patient) {
		number++;
		products.add(patient);
		// store stamps
		ArrayList<Double> t = patient.times;
		ArrayList<String> e = patient.eventName;
		ArrayList<String> s = patient.ambulanceName;

		numbers.add(number);
		timestamps.addAll(t);
		events.addAll(e);
		stations.addAll(s);

		queueWaitingTimes.add(patient.waitingTime);
		travelTimes.add(patient.travelTime);
		return true;
	}

	public int[] getNumbers() {
		numbers.trimToSize();
		int[] tmp = new int[numbers.size()];
		for (int i = 0; i < numbers.size(); i++) {
			tmp[i] = (numbers.get(i)).intValue();
		}
		return tmp;
	}
	
	public double[] getQueueWaitingTimes() {
		queueWaitingTimes.trimToSize();
		double[] tmp = new double[queueWaitingTimes.size()];
		for (int i = 0; i < queueWaitingTimes.size(); i++) {
			tmp[i] = (queueWaitingTimes.get(i)).doubleValue();
		}
		return tmp;
	}
	
	public double[] getTravelTimes() {
		travelTimes.trimToSize();
		double[] tmp = new double[travelTimes.size()];
		for (int i = 0; i < travelTimes.size(); i++) {
			tmp[i] = (travelTimes.get(i)).doubleValue();
		}
		return tmp;
	}

	public double[] getTimestamps() {
		timestamps.trimToSize();
		double[] tmp = new double[timestamps.size()];
		for (int i = 0; i < timestamps.size(); i++) {
			tmp[i] = (timestamps.get(i)).doubleValue();
		}
		return tmp;
	}

	public String[] getEvents() {
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStations() {
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}
}