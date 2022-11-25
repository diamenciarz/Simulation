package processes;

import helpers.Printer;
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
	private int timestampCounter;
	private boolean isTimeRandom;

	{
		status = 'i';
	}

	/**
	 * Constructor
	 * Service times are exponentially distributed with mean 30
	 * 
	 * @param q Queue from which the machine has to take products
	 * @param s Where to send the completed products
	 * @param e Eventlist that will manage events
	 * @param n The name of the machine
	 */
	public Machine(Queue q, IProductAcceptor s, String n) {
		this(q, s, n, 30);
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
	public Machine(Queue q, IProductAcceptor s, String n, double m) {
		queue = q;
		sink = s;
		name = n;
		meanProcTime = Math.max(m, 1);
		isTimeRandom = true;
		queue.assignProduct(this);
	}

	/**
	 * Constructor
	 * Service times are pre-specified
	 * 
	 * @param q          Queue from which the machine has to take products
	 * @param s          Where to send the completed products
	 * @param e          Eventlist that will manage events
	 * @param n          The name of the machine
	 * @param timeStamps service times
	 */
	public Machine(Queue q, IProductAcceptor s, String n, double[] timeStamps) {
		queue = q;
		sink = s;
		name = n;
		processingTimestamps = timeStamps;
		timestampCounter = 0;
		isTimeRandom = false;
		queue.assignProduct(this);
	}

	/**
	 * Method to have this object execute an event
	 * 
	 * @param type The type of the event that has to be executed
	 * @param time The current time
	 */
	public void execute(int type, double time) {
		Printer.printFinished(time, name);
		product.stamp(time, "Production complete", name);
		sink.receiveProduct(product);
		product = null;
		status = 'i';
		queue.assignProduct(this);
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
		product.stamp(EventList.getTime(), "Production started", name);
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
			double time = EventList.getTime();
			status = 'b';
			Printer.printStartedProduction(EventList.getTime(), name);
			EventList.addEvent(this, 0, time + duration);
			return;
		}

		boolean processQueueEmpty = processingTimestamps.length <= timestampCounter;
		if (processQueueEmpty) {
			EventList.stop();
		}

		status = 'b';
		EventList.addEvent(this, 0, EventList.getTime() + processingTimestamps[timestampCounter]);
		Printer.printStartedProduction(EventList.getTime(), name);
		timestampCounter++;
	}

}