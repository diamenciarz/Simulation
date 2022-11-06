/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package simulation;

import processes.EventList;
import processes.Machine;
import processes.Source;

public class Simulation {

    public EventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;
	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	// Create an eventlist
	EventList l = new EventList();
	// A queue for the machine
	Queue q = new Queue();
	// A source
	Source s = new Source(q,l,"Source 1");
	// A sink
	Sink si = new Sink("Sink 1");
	// A machine
	Machine m = new Machine(q,si,l,"Machine 1");
	// start the eventlist
	l.start(2000); // 2000 is maximum time
    }
    
}
