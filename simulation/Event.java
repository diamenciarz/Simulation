/**
 *	Event class
 *	Events that facilitate changes in the simulation
 *	@author Joel Karel
 *	@version %I%, %G%
 */
package simulation;

import processes.IProcess;

public class Event {
	/** The object involved with the event */
	private IProcess target;
	/** The type of the event */
	private int type;
	/** The time on which the event will be executed */
	private double executionTime;

	/**
	 * Constructor for objects
	 * 
	 * @param eventProcessor The object that will process the event
	 * @param eventType      The type of the event
	 * @param executionTime  The time on which the event will be executed
	 */
	public Event(IProcess eventProcessor, int eventType, double executionTime) {
		target = eventProcessor;
		type = eventType;
		this.executionTime = executionTime;
	}

	/**
	 * Method to signal the target to execute an event
	 */
	public void execute() {
		target.execute(type, executionTime);
	}

	/**
	 * Method to ask the event at which time it will be executed
	 * 
	 * @return The time at which the event will be executed
	 */
	public double getExecutionTime() {
		return executionTime;
	}
}
