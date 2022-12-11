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
	public void askProduct(Ambulance machine) {
		// This is only possible with a non-empty queue
		if (queue.size() > 0) {
			machine.givePatient(queue.get(0));
		} else {
			idleMachines.add(machine);
		}
	}

	/**
	 * Offer a product to the queue
	 * It is investigated whether a machine wants the product, otherwise it is
	 * stored
	 */
	public void givePatient(Patient patient) {

		Ambulance closestAmbulance = findClosestAmbulance(patient.position);
		if (closestAmbulance == null) {
			queue.add(patient); // Otherwise store it
		} else {
			dispatchAmbulance(closestAmbulance, patient);
			DebugLogger.printQueueState(queue.size());
		}
	}

	private void dispatchAmbulance(Ambulance closestAmbulance, Patient patient) {
		closestAmbulance.givePatient(patient);
		// remove the request regardless of whether or not the product has been accepted
		idleMachines.remove(closestAmbulance);

	}

	private Ambulance findClosestAmbulance(Vector2 patientPosition) {
		if (idleMachines.size() == 0) {
			return CrewScheduler.addCrew(patientPosition);
		}

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