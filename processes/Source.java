package processes;

import simulation.DebugLogger;
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
	private double[] arrivalTimestamps;
	/** Interarrival time iterator */
	private int timestampCounter;
	private boolean isTimeRandom;

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
		meanArrTime = Math.max(m, 1);
		isTimeRandom = true;
		// put first event in list for initialization
		list.addEvent(this, 0, drawRandomExponential(meanArrTime)); // target,type,time
	}

	/**
	 * Constructor, creates objects
	 * Interarrival times are prespecified
	 * 
	 * @param q                 The receiver of the products
	 * @param l                 The eventlist that is requested to construct events
	 * @param n                 Name of object
	 * @param interarrivalTimes interarrival times
	 */
	public Source(IProductAcceptor q, EventList l, String n, double[] interarrivalTimes) {
		list = l;
		queue = q;
		name = n;
		isTimeRandom = false;
		this.arrivalTimestamps = interarrivalTimes;
		timestampCounter = 0;
		// put first event in list for initialization
		list.addEvent(this, 0, this.arrivalTimestamps[0]);
	}

	@Override
	public void execute(int type, double time) {
		// show arrival
		DebugLogger.printArrived(time);
		// give arrived product to queue
		Product p = new Product();
		p.stamp(time, "Creation", name);
		queue.acceptProduct(p);

		// generate duration
		if (isTimeRandom) {
			double duration = drawRandomExponential(meanArrTime);
			list.addEvent(this, 0, time + duration);
			return;
		}

		boolean eventQueueEmpty = arrivalTimestamps.length <= timestampCounter;
		if (eventQueueEmpty) {
			list.stop();
			return;
		}

		timestampCounter++;
		list.addEvent(this, 0, time + arrivalTimestamps[timestampCounter]);
	}

	public static double drawRandomExponential(double mean) {
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with given mean
		double res = -mean * Math.log(u);
		double rounded = (double) (Math.round(res * 10)) / 10;
		return rounded;
	}
}