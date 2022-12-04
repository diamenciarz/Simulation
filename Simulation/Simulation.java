/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.util.ArrayList;

public class Simulation {
	public static int N_MACHINES = 1;
    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;
	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	// Create an eventlist
	CEventList l = new CEventList();
	Queue q = new Queue();
	Source s = new Source(q,l,"Source 1");
	Sink si = new Sink("Sink 1");
	
	createMachines(q, si, l);
	// start the eventlist
	l.start(2000); // 2000 is maximum time
    }



	//TO-DO: DO THIS METHOD
	public static ArrayList<Machine> createMachines(Queue q, Sink si, CEventList l){
		ArrayList<Machine> machines = new ArrayList<>();
		for (int i = 1; i <= N_MACHINES; i++) {
			machines.add(new Machine(q, si, l, "Machine " + i));
		}

		return machines;
	}
    
}
