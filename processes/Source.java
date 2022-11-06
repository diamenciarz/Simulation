package processes;

import simulation.IProductAcceptor;
import simulation.Product;

/**
 * A source of products
 * This class implements CProcess so that it can execute events.
 * By continuously creating new events, the source keeps busy.
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Source implements IProcess {
	/** Eventlist that will be requested to construct events */
	private EventList list;
	/** Queue that buffers products for the machine */
	private IProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interarrivalTimeCounter;

	/**
	 * Constructor, creates objects
	 * Interarrival times are exponentially distributed with mean 33
	 * 
	 * @param q The receiver of the products
	 * @param l The eventlist that is requested to construct events
	 * @param n Name of object
	 */
	public Source(IProductAcceptor q, EventList l, String n) {
		this(q, l, n, 33);
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
	public Source(IProductAcceptor q, EventList l, String n, double m) {
		list = l;
		queue = q;
		name = n;
		meanArrTime = m;
		// put first event in list for initialization
		list.addEvent(this, 0, drawRandomExponential(meanArrTime)); // target,type,time
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
	public Source(IProductAcceptor q, EventList l, String n, double[] ia) {
		list = l;
		queue = q;
		name = n;
		meanArrTime = -1;
		interarrivalTimes = ia;
		interarrivalTimeCounter = 0;
		// put first event in list for initialization
		list.addEvent(this, 0, interarrivalTimes[0]); // target,type,time
	}

	@Override
	public void execute(int type, double tme) {
		// show arrival
		System.out.println("Arrival at time = " + tme);
		// give arrived product to queue
		Product p = new Product();
		p.stamp(tme, "Creation", name);
		queue.acceptProduct(p);
		// generate duration
		if (meanArrTime > 0) {
			double duration = drawRandomExponential(meanArrTime);
			// Create a new event in the eventlist
			list.addEvent(this, 0, tme + duration); // target,type,time
		} else {
			interarrivalTimeCounter++;
			if (interarrivalTimes.length > interarrivalTimeCounter) {
				list.addEvent(this, 0, tme + interarrivalTimes[interarrivalTimeCounter]); // target,type,time
			} else {
				list.stop();
			}
		}
	}

	public static double drawRandomExponential(double mean) {
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean * Math.log(u);
		double rounded = (double)(Math.round(res * 10)) / 10;
		return rounded;
	}
}