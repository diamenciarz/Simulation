package processes;

import helpers.DebugLogger;
import helpers.RandGenerator;
import simulation.IProductAcceptor;
import simulation.Product;
import simulation.Queue;

/**
 * Machine in a factory
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Machine implements IProcess, IProductAcceptor {
	/** Product that is being handled */
	private Product product;
	/** Eventlist that will manage events */
	private final EventList eventlist;
	/** Queue from which the machine has to take products */
	private Queue queue;
	/** Sink to dump products */
	private IProductAcceptor sink;
	/** Status of the machine (b=busy, i=idle) */
	private char status;
	/** Machine name */
	private final String name;
	/** Mean processing time */
	private double meanProcTime;
	/** Processing times (in case pre-specified) */
	private double[] processingTimestamps;
	/** Processing time iterator */
	private int procTimestampCounter;
	private boolean isTimeRandom;

	/**
	 * Constructor
	 * Service times are exponentially distributed with mean 30
	 * 
	 * @param q Queue from which the machine has to take products
	 * @param s Where to send the completed products
	 * @param e Eventlist that will manage events
	 * @param n The name of the machine
	 */
	public Machine(Queue q, IProductAcceptor s, EventList e, String n) {
		this(q, s, e, n, 30);
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
	public Machine(Queue q, IProductAcceptor s, EventList e, String n, double m) {
		status = 'i';
		queue = q;
		sink = s;
		eventlist = e;
		name = n;
		meanProcTime = Math.max(m, 1);
		isTimeRandom = false;
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
	public Machine(Queue q, IProductAcceptor s, EventList e, String n, double[] st) {
		status = 'i';
		queue = q;
		sink = s;
		eventlist = e;
		name = n;
		processingTimestamps = st;
		procTimestampCounter = 0;
		isTimeRandom = true;
		queue.askProduct(this);
	}

	/**
	 * Method to have this object execute an event
	 * 
	 * @param type The type of the event that has to be executed
	 * @param time The current time
	 */
	public void execute(int type, double time) {
		DebugLogger.printFinished(time);
		product.stamp(time, "Production complete", name);
		sink.receiveProduct(product);
		product = null;
		status = 'i';
		queue.askProduct(this);
	}

	/**
	 * The machine receives a product and handles it
	 * 
	 * @param p The product that is offered
	 * @return true if the product is accepted and started, false otherwise
	 */
	@Override
	public boolean receiveProduct(Product p) {
		// Only accept something if the machine is idle
		if (status == 'i') {
			acceptProduct(p);
			return true;
		} else
			return false;
	}

	private void acceptProduct(Product p) {
		product = p;
		// mark starting time
		product.stamp(eventlist.getTime(), "Production started", name);
		startProduction();
	}

	/**
	 * Starting routine for the production
	 * Start the handling of the current product with an exponentionally distributed
	 * processingtime with average 30
	 * This time is placed in the eventlist
	 */
	private void startProduction() {
		if (isTimeRandom) {
			double duration = RandGenerator.drawRandomExponential(meanProcTime);
			double time = eventlist.getTime();
			eventlist.addEvent(this, 0, time + duration);
			status = 'b';
			return;
		}

		boolean processQueueEmpty = processingTimestamps.length <= procTimestampCounter;
		if (processQueueEmpty) {
			eventlist.stop();
		}

		procTimestampCounter++;
		status = 'b';
		eventlist.addEvent(this, 0, eventlist.getTime() + processingTimestamps[procTimestampCounter]);
	}

}