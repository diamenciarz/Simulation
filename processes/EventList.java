package processes;

import java.util.ArrayList;

import simulation.Event;

/**
 * Event processing mechanism
 * Events are created here and it is ensured that they are processed in the
 * proper order
 * The simulation clock is located here.
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class EventList {
	/** The time in the simulation */
	private static double currentTime = 0;
	/** List of events that have to be executed */
	private static final ArrayList<Event> events = new ArrayList<>();
	/** Stop flag */
	private static boolean stopFlag = false;

	/**
	 * Method for the construction of a new event.
	 * 
	 * @param target The object that will process the event
	 * @param type   A type indicator of the event for objects that can process
	 *               multiple types of events.
	 * @param time   The time at which the event will be executed
	 */
	public static void addEvent(IProcess target, int type, double time) {
		Event event = new Event(target, type, time);
		// Keep the list chronological
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getExecutionTime() > event.getExecutionTime()) {
				events.add(i, event);
				return;
			}
		}
		events.add(event);
	}

	/**
	 * Method for starting the eventlist.
	 * It will run until there are no longer events in the list or that a maximum
	 * time has elapsed
	 * 
	 * @param simulationTime The maximum time of the simulation
	 */
	public static void start(double simulationTime) {
		addEndingEvent(-1, simulationTime);
		// stop criterion
		start();
	}

	public static void addEndingEvent(int type, double time) {
		Event event = new Event(new EndingEvent(), type, time);
		// Keep the list chronological
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getExecutionTime() > event.getExecutionTime()) {
				events.add(i, event);
				return;
			}
		}
		events.add(event);
	}

	public static void stop() {
		stopFlag = true;
	}

	/**
	 * Method for starting the eventlist.
	 * It will run until there are no longer events in the list
	 */
	public static void start() {
		// stop criterion
		while ((events.size() > 0) && (!stopFlag)) {
			// Make the similation time equal to the execution time of the first event in
			// the list that has to be processed
			currentTime = events.get(0).getExecutionTime();
			// Let the element be processed
			events.get(0).execute();
			// Remove the event from the list
			events.remove(0);
		}
	}

	/**
	 * Method for accessing the simulation time.
	 * The variable with the time is private to ensure that no other object can
	 * alter it.
	 * This method makes it possible to read the time.
	 * 
	 * @return The current time in the simulation
	 */
	public static double getTime() {
		return currentTime;
	}

	public static void stopSimulation() {
			stop();
	}
}