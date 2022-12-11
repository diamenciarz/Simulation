package simulation;

import java.util.ArrayList;

import helpers.CrewScheduler;
import helpers.DebugLogger;
import helpers.math.Vector2;

/**
 * Queue that stores products until they can be handled on a machine machine
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Queue implements ProductAcceptor {
	/** List in which the products are kept */
	private ArrayList<Patient> queue;
	/** Requests from machine that will be handling the products */
	private ArrayList<Ambulance> idleMachines;

	/**
	 * Initializes the queue and introduces a dummy machine
	 * the machine has to be specified later
	 */
	public Queue() {
		queue = new ArrayList<>();
		idleMachines = new ArrayList<>();
	}

	/**
	 * Asks a queue to give a product to a machine
	 * True is returned if a product could be delivered; false if the request is
	 * queued
	 */
	public boolean askProduct(Ambulance machine) {
		// This is only possible with a non-empty queue
		if (queue.size() > 0) {
			// If the machine accepts the product
			if (machine.givePatient(queue.get(0))) {
				queue.remove(0);// Remove it from the queue
				return true;
			} else
				return false; // Machine rejected; don't queue request
		} else {
			idleMachines.add(machine);
			return false; // queue request
		}
	}

	/**
	 * Offer a product to the queue
	 * It is investigated whether a machine wants the product, otherwise it is
	 * stored
	 */
	public boolean givePatient(Patient patient) {

		// Check if the machine accepts it
		if (idleMachines.size() < 0)
			queue.add(patient); // Otherwise store it
		else {
			boolean delivered = false;
			while (!delivered) {

				Ambulance closestAmbulance = findClosestAmbulance(patient);
				delivered = closestAmbulance.givePatient(patient);
				// remove the request regardless of whether or not the product has been accepted
				idleMachines.remove(closestAmbulance);
			}
			if (!delivered)
				queue.add(patient); // Otherwise store it
		}
		DebugLogger.printQueueState(queue.size());
		return true;
	}

	private Ambulance findClosestAmbulance(Patient patient) {
		if (idleMachines.size() == 0) {
			return CrewScheduler.addCrew(patient.position);
		}
		
		Vector2 patientPosition = patient.position;
		Ambulance currentClosestAmbulance = idleMachines.get(0);
		double currentClosestDistance = currentClosestAmbulance.getCurrentPosition()
				.manhattanDistanceTo(patientPosition);

		for (Ambulance ambulance : idleMachines) {
			double newDistance = ambulance.getCurrentPosition().manhattanDistanceTo(patientPosition);
			if (newDistance < currentClosestDistance) {
				currentClosestAmbulance = ambulance;
				currentClosestDistance = newDistance;
			}
		}
		return currentClosestAmbulance;
	}
}