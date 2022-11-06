package simulation;

import java.util.ArrayList;

import processes.Machine;

/**
 * Queue that stores products until they can be handled on a machine machine
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Queue implements IProductAcceptor {
	/** List in which the products are kept */
	private ArrayList<Product> queue;
	/** Requests from machine that will be handling the products */
	private ArrayList<Machine> requests;

	/**
	 * Initializes the queue and introduces a dummy machine
	 * the machine has to be specified later
	 */
	public Queue() {
		queue = new ArrayList<>();
		requests = new ArrayList<>();
	}

	/**
	 * Asks a queue to give a product to a machine
	 * True is returned if a product could be delivered; false if the request is
	 * queued
	 */
	public boolean assignProduct(Machine machine) {
		if (queue.size() == 0) {
			requests.add(machine);
			return false;
		}

		boolean machineAcceptedProduct = machine.receiveProduct(queue.get(0));
		if (machineAcceptedProduct) {
			queue.remove(0);
			return true;
		}
		
		return false;
	}

	/**
	 * Offer a product to the queue
	 * It is investigated whether a machine wants the product, otherwise it is
	 * stored
	 */
	public boolean receiveProduct(Product p) {
		// Check if the machine accepts it
		if (requests.size() < 1)
			queue.add(p); // Otherwise store it
		else {
			boolean delivered = false;
			while (!delivered & (requests.size() > 0)) {
				delivered = requests.get(0).receiveProduct(p);
				// remove the request regardless of whether or not the product has been accepted
				requests.remove(0);
			}
			if (!delivered)
				queue.add(p); // Otherwise store it
		}
		return true;
	}
}