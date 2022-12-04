package Simulation;

import java.util.ArrayList;

import helpers.Printer;

/**
 *	Queue that stores products until they can be handled on a machine machine
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Queue implements ProductAcceptor
{
	/** List in which the products are kept */
	private ArrayList<Product> queue;
	/** Requests from machine that will be handling the products */
	private ArrayList<Machine> idleMachines;
	
	/**
	*	Initializes the queue and introduces a dummy machine
	*	the machine has to be specified later
	*/
	public Queue()
	{
		queue = new ArrayList<>();
		idleMachines = new ArrayList<>();
	}
	
	/**
	*	Asks a queue to give a product to a machine
	*	True is returned if a product could be delivered; false if the request is queued
	*/
	public boolean askProduct(Machine machine)
	{
		// This is only possible with a non-empty queue
		if(queue.size()>0)
		{
			// If the machine accepts the product
			if(machine.giveProduct(queue.get(0)))
			{
				queue.remove(0);// Remove it from the queue
				return true;
			}
			else
				return false; // Machine rejected; don't queue request
		}
		else
		{
			idleMachines.add(machine);
			return false; // queue request
		}
	}
	
	/**
	*	Offer a product to the queue
	*	It is investigated whether a machine wants the product, otherwise it is stored
	*/
	public boolean giveProduct(Product p)
	{
		// Check if the machine accepts it
		if(idleMachines.size()<1)
		queue.add(p); // Otherwise store it
		else
		{
			boolean delivered = false;
			while(!delivered & (idleMachines.size()>0))
			{
				delivered=idleMachines.get(0).giveProduct(p);
				// remove the request regardless of whether or not the product has been accepted
				idleMachines.remove(0);
			}
			if(!delivered)
			queue.add(p); // Otherwise store it
		}
		Printer.printQueueState(queue.size());
		return true;
	}
}