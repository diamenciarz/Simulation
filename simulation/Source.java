package simulation;

import helpers.Distributions;
import helpers.Printer;
import helpers.stats.StatsCollector;

/**
 * A source of products
 * This class implements CProcess so that it can execute events.
 * By continuously creating new events, the source keeps busy.
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Source implements CProcess {
	/** Eventlist that will be requested to construct events */
	private CEventList eventList;
	/** Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;

	/**
	 * Constructor, creates objects
	 * Interarrival times are exponentially distributed with mean 33
	 * 
	 * @param q The receiver of the products
	 * @param l The eventlist that is requested to construct events
	 * @param n Name of object
	 */
	public Source(ProductAcceptor q, CEventList l, String n) {
		eventList = l;
		queue = q;
		name = n;
		meanArrTime = 33;
		// put first event in list for initialization
		eventList.add(this, 0, drawRandomExponential(0)); // target,type,time
	}

	/**
	 * Constructor, creates objects
	 * Interarrival times are exponentially distributed with specified mean
	 * 
	 * @param q The receiver of the products
	 * @param l The eventlist that is requested to construct events
	 * @param n Name of object
	 * @param m Mean arrival time
	 */
	public Source(ProductAcceptor q, CEventList l, String n, double m) {
		eventList = l;
		queue = q;
		name = n;
		meanArrTime = m;
		// put first event in list for initialization
		eventList.add(this, 0, drawRandomExponential(m)); // target,type,time
	}

	/**
	 * Constructor, creates objects
	 * Interarrival times are prespecified
	 * 
	 * @param q  The receiver of the products
	 * @param l  The eventlist that is requested to construct events
	 * @param n  Name of object
	 * @param ia interarrival times
	 */
	public Source(ProductAcceptor q, CEventList l, String n, double[] ia) {
		eventList = l;
		queue = q;
		name = n;
		meanArrTime = -1;
		interarrivalTimes = ia;
		interArrCnt = 0;
		// put first event in list for initialization
		eventList.add(this, 0, interarrivalTimes[0]);
	}

	@Override
	public void execute(int type, double currentTime) {
		// show arrival
		Printer.printArrived(eventList.getTime(), name);
		
		
		// give arrived product to queue
		Patient p = new Patient();
		p.creationTime = eventList.getTime();
		p.stampEvent(currentTime, "Creation", name);
		queue.givePatient(p);		
		StatsCollector.collectEvent(eventList.getTime(), 888, p.getPatientID(), "Not applicable", "Patient picked up","");				
		StatsCollector.collectPatient(p, eventList.getTime(),  type);
		
		// generate duration
		if (meanArrTime > 0) {
			double duration = drawRandomExponential(currentTime);
			// Create a new event in the eventlist
			eventList.add(this, type, currentTime + duration);
		} else {
			interArrCnt++;
			if (interarrivalTimes.length > interArrCnt) {
				eventList.add(this, 0, currentTime + interarrivalTimes[interArrCnt]);
			} else {
				eventList.stop();
			}
		}
	}

	public static double drawRandomExponential(double currentTime) {
		// draw a [0,1] uniform distributed number
		double u = Math.random();

		double lambda = Distributions.lambda(currentTime);
		// Convert it into a exponentially distributed random variate with lambda
		return -lambda * Math.log(u);
	}
}