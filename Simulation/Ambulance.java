package simulation;

import helpers.Distributions;
import helpers.Printer;
import helpers.math.TimeConverter;
import helpers.math.Vector2;
import simulation.city.City;

/**
 * Machine in a factory
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Ambulance implements CProcess, ProductAcceptor {
	/** Product that is being handled */
	private Patient patient;
	/** Eventlist that will manage events */
	private final CEventList eventlist;
	/** Queue from which the machine has to take products */
	private Queue queue;
	/** Sink to dump products */
	private ProductAcceptor sink;
	/** Status of the machine (b=busy, i=idle) */
	private char status;
	/** Machine name */
	public final String name;
	/** Mean processing time */
	private double meanProcTime;
	/** Processing times (in case pre-specified) */
	private double[] processingTimes;
	/** Processing time iterator */
	private int procCnt;
	/**
	 * The location of the hub that the ambulance is coming back to after dropping
	 * off a patient
	 */
	private Vector2 hubPosition;
	/** The timestamp at which this ambulance last visited the hospital */
	private double lastHospitalVisitTime = -100;

	/**
	 * Constructor
	 * Service times are exponentially distributed with mean 30
	 * 
	 * @param q Queue from which the machine has to take products
	 * @param s Where to send the completed products
	 * @param e Eventlist that will manage events
	 * @param n The name of the machine
	 */
	public Ambulance(Queue q, ProductAcceptor s, CEventList e, String n) {
		status = 'i';
		queue = q;
		sink = s;
		eventlist = e;
		name = n;
		meanProcTime = 30;
		queue.askProduct(this);
	}

	/**
	 * Constructor
	 * Service times are exponentially distributed with specified mean
	 * 
	 * @param q Queue from which the machine has to take products
	 * @param s Where to send the completed products
	 * @param e Eventlist that will manage events
	 * @param n The name of the machine
	 * @param m Mean processing time
	 */
	public Ambulance(Queue q, ProductAcceptor s, CEventList e, String n, double m) {
		status = 'i';
		queue = q;
		sink = s;
		eventlist = e;
		name = n;
		meanProcTime = m;
		queue.askProduct(this);
	}

	/**
	 * Constructor
	 * Service times are pre-specified
	 * 
	 * @param q  Queue from which the machine has to take products
	 * @param s  Where to send the completed products
	 * @param e  Eventlist that will manage events
	 * @param n  The name of the machine
	 * @param st service times
	 */
	public Ambulance(Queue q, ProductAcceptor s, CEventList e, String n, double[] st) {
		status = 'i';
		queue = q;
		sink = s;
		eventlist = e;
		name = n;
		meanProcTime = -1;
		processingTimes = st;
		procCnt = 0;
		queue.askProduct(this);
	}

	public void setHub(Vector2 newPosition) {
		hubPosition = newPosition;
	}

	/**
	 * Method to have this object execute an event
	 * 
	 * @param type The type of the event that has to be executed
	 * @param tme  The current time
	 */
	public void execute(int type, double tme) {
		// show arrival
		Printer.printFinished(eventlist.getTime(), name);
		// Remove product from system
		patient.stamp(tme, "Patient dropped off at the hospital", name);
		sink.givePatient(patient);
		patient = null;
		// set machine status to idle
		status = 'i';
		lastHospitalVisitTime = eventlist.getTime();
		// Ask the queue for products
		queue.askProduct(this);
	}

	/**
	 * Let the machine accept a product and let it start handling it
	 * 
	 * @param p The product that is offered
	 * @return true if the product is accepted and started, false in all other cases
	 */
	@Override
	public boolean givePatient(Patient p) {
		// Only accept something if the machine is idle
		if (status == 'i') {
			// accept the product
			patient = p;
			// mark starting time
			Printer.printStartedProduction(eventlist.getTime(), this, patient);
			patient.stamp(eventlist.getTime(), "Patient picked up", name);
			// start production
			handlePatient();
			// Flag that the product has arrived
			return true;
		}
		// Flag that the product has been rejected
		else
			return false;
	}

	/**
	 * Starting routine for the production
	 * Start the handling of the current product with an exponentionally distributed
	 * processingtime with average 30
	 * This time is placed in the eventlist
	 */
	private void handlePatient() {
		// generate duration
		if (meanProcTime > 0) {

			eventlist.add(this, 0, calculateTimeUntilHospitalArrival()); // target,type,time
			// set status to busy
			status = 'b';
		} else {
			if (processingTimes.length > procCnt) {
				eventlist.add(this, 0, eventlist.getTime() + processingTimes[procCnt]); // target,type,time
				// set status to busy
				status = 'b';
				procCnt++;
			} else {
				eventlist.stop();
			}
		}
	}

	private double calculateTimeUntilHospitalArrival() {
		double patientProcessingDuration = drawRandomExponential(1);
		double travelTime = calculateTravelTime();
		double currentTime = eventlist.getTime();
		return currentTime + travelTime + patientProcessingDuration;
	}

	private double drawRandomExponential(double mean) {
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean * Math.log(u);
		return res;
	}

	/**
	 * Calculate the duration of travel from the ambulance's current position to
	 * patient and from patient to hospital
	 * 
	 * @return
	 */
	private double calculateTravelTime() {
		double distanceToPatient = patient.position.manhattanDistanceTo(getCurrentPosition());
		double distanceToHospital = patient.position.manhattanDistanceTo(City.centerPosition);
		// Create a new event in the eventlist
		double travelTimeInHours = TimeConverter.getTravelTime(distanceToPatient + distanceToHospital);
		return travelTimeInHours;
	}

	/**
	 * Simulate the ambulance travelling in the meantime and calculate, where the
	 * ambulance is currently.
	 * 
	 * @return
	 */
	public Vector2 getCurrentPosition() {
		Vector2 vectorFromHospitalToHub = new Vector2(hubPosition.x, hubPosition.y);

		double timeSinceHospitalVisit = eventlist.getTime() - lastHospitalVisitTime;
		double travelTimeFromHospitalToHub = vectorFromHospitalToHub.length();
		double fractionOfTheWayToHub = Math.min(timeSinceHospitalVisit / travelTimeFromHospitalToHub, 1);

		return vectorFromHospitalToHub.scale(fractionOfTheWayToHub);
	}

}