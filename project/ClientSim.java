/**
 * Client Code for SimulationManager
 * Follow User Manual that is in the .doc file.
 * @author Ahmet Çelik
 * @version 1.0
 *
 */
public class ClientSim {
	/**
	 * Start simulation and prints results of simulation
	 * @param args
	 */
	public static void main(String[] args)  {
		SimulationManager sim = new SimulationManager(); //takes data from the user.
		sim.Simulate(); //runs simulation
		sim.results();	//prints results
	}	
}
