import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
/**
 * 
 * Defines a class that is responsible for simulation of the system. 
 * {@link SimulationManager This class} is responsible for managing queues and vehicles in the system. 
 * It also handles the movements of trucks between mining site and disposal site queues properly. 
 *
 * @author Ahmet Çelik 2009400111
 * @version 1.0
 *
 */
public class SimulationManager{
	/**
	 * Numbers of Forklifts in the simulation
	 */
	private int numForklifts;
	/**
	 * Numbers of Trucks in the simulation
	 */
	private int numTrucks;
	/**
	 * Numbers of Long Trucks in the simulation
	 */
	private int numLongTrucks;
	/**
	 * Finish time of the simulation
	 */
	private double simulationTime;
	/**
	 * Seed for {@link #r Random} object of simulation
	 */
	private long seed;
	/**
	 * Queue of Mining Site
	 */
	private Queue queueMining;
	/**
	 * Queue of Disposal Site
	 */
	private Queue queueDisposal;
	/**
	 * Place of loading and unloadings.
	 * Mining Site at index 0 and Disposal Site at index 1
	 */
	private Vehicle[] sites = new Vehicle[2]; // 0: MiningSite and  1: DisposalSite
	/**
	 * Holds all vehicles for easy access while reading statistics.
	 * Copy all Vehicles addresses to a known place, 
	 * since after simulation finished it is hard to find all vehicles places. 
	 */
	private Vehicle[] allVehicles;
	/**
	 * Holds all pending events in chronological order
	 */
	private ArrayList<Event> eventList;
	/**
	 * Random object of simulation
	 */
	private Random r;
	/**
	 * Current time of simulation
	 */
	private double currentTime = 0;
	
	/**
	 * Constructs a Simulation Manager by scanning inputs from the user. 
	 * Does all preparations for sake of simulation. 
	 */
	public SimulationManager(){//while loops prevent to enter negative integer number
	   Scanner console = new Scanner(System.in);	  
	   System.out.println("Welcome to CMS");
	   System.out.print("Enter the number of forklifts: ");
	       numForklifts = console.nextInt(); 
	       while(numForklifts<0){
	    	   System.out.print("Enter the number of forklifts: ");
	    	   numForklifts = console.nextInt(); 
	       }
	   System.out.print("Enter the number of trucks: ");
	       numTrucks = console.nextInt();
	       while(numTrucks<0){
	    	   System.out.print("Enter the number of trucks: ");
	    	   numTrucks = console.nextInt(); 
	       }
	   System.out.print("Enter the number of long trucks: ");
	       numLongTrucks = console.nextInt();
	       while(numLongTrucks<0){
	    	   System.out.print("Enter the number of long trucks: ");
	    	   numLongTrucks = console.nextInt(); 
	       }
	   System.out.print("Enter total simulation time: ");
	       simulationTime = console.nextDouble();
	       while(simulationTime<0){
	    	   System.out.print("Enter total simulation time: ");
	    	   simulationTime = console.nextInt(); 
	       }
	   System.out.print("Enter seed: ");
	       seed = console.nextLong();
	   r = new Random(seed); 
	   queueMining = new Queue();
	   queueDisposal = new Queue();
	   allVehicles = new Vehicle[numForklifts+numTrucks+numLongTrucks];
	   for(int i=0;i<numForklifts;i++){
		   allVehicles[i] = new Forklift(i+1);
		   queueMining.add(allVehicles[i]);
	   }
	   for(int i=0;i<numTrucks;i++){
		   int temp = i+numForklifts;//to add after Forklifts 
		   allVehicles[temp] = new Truck(i+1);
		   queueMining.add(allVehicles[temp]); 
	   }
	   for(int i=0;i<numLongTrucks;i++){
		   int temp = i+numForklifts+numTrucks;//to add after Trucks
		   allVehicles[temp] = new LongTruck(i+1);
		   queueMining.add(allVehicles[temp]);
	   }
	   eventList = new ArrayList<Event>(); //empty eventlist
    }
	/**
	 * Runs simulation until to {@link #simulationTime simulation finish time}.
	 */
	public void Simulate(){
		System.out.println("Starting Simulation...");
		if(queueMining.isEmpty()){//if there is no vehicle
			System.out.println("There is no any vehicle in the queue. Restart simulation and add at least one vehicle please!");
		}else{
			eventList.add(new Event(currentTime,"LS",queueMining.get()));//trigger event
			while( eventList.get(0).getStartTime()<simulationTime){//looks pending event can be start before times out			
				currentTime = eventList.get(0).getStartTime();
				Event temp = eventList.get(0);  // to solve conflicts for some 
				eventList.remove(0);            // events that have been created from current event and 
				eventReader(temp);              // have same start time with current event
				Collections.sort(eventList);   // sorts event with chronological order
			}
			System.out.println("Completed Simulation...\n");
		}
	}
	
	/**
	 * Starts loadings of vehicles. Also creates {@link #loadFinish() load finish event} and adds it to {@link #eventList event list}.
	 */
	private void loadStart(){
		if(sites[0] == null && !queueMining.isEmpty()){
			sites[0] = queueMining.get();
			queueMining.remove();
			Event loadFinish = new Event(currentTime+sites[0].eventLoadTime(r.nextDouble()),"LF",sites[0]);
			eventList.add(loadFinish);		
		}
	}
	
	/**
	 * Does necessary jobs after load finish. Also creates {@link #arriveDispose(Event) arrive disposal queue} and {@link #loadStart() load start} events and adds them to {@link #eventList event list}.
	 */
	private void loadFinish(){
		System.out.printf("%s#%d is loaded at %.4f\n",sites[0].getClass().getName(),sites[0].ID,currentTime);
		sites[0].roundTripTimeStart=currentTime;// start of Round Trip Time
		sites[0].loadFinishTime=currentTime; // start of Loaded Time - indeed one of them enough,but two will provide more clear understanding
		Event travelToDispose = new Event(currentTime+sites[0].eventTravelToDisposeTime(r.nextDouble()),"DA",sites[0]);
		sites[0] = null; // vehicle started travel
		if(!queueMining.isEmpty()){// loadStart Event
			Event loadStart = new Event(currentTime,"LS",queueMining.get());
			eventList.add(loadStart);
		}
		eventList.add(travelToDispose);// arrive event to dispose queue
	}
	/**
	 * Adds vehicle to {@link #queueDisposal disposal site queue}. Also creates {@link #unloadStart() unload start event} if Disposal site is empty and there is only this vehicle in the {@link #queueDisposal Disposal site queue}.
	 */
	private void arriveDispose(Event e){
		queueDisposal.add(e.getVehicle());//add to queue
		if(sites[1]== null && queueDisposal.size()==1){//if there is nobody, start unload
			Event unloadStart = new Event(currentTime,"US",queueDisposal.get());
			eventList.add(unloadStart);
		}
		
	}
	
	/**
	 * Adds vehicle to {@link #queueDisposal disposal site queue}. Also creates {@link #unloadStart() unload start event} if Disposal site is empty and there is only this vehicle in the {@link #queueDisposal Disposal site queue}.
	 */
	private void unloadStart(){
		if(sites[1]==null && !queueDisposal.isEmpty()){//unload start conditions
			sites[1] = queueDisposal.get();
			queueDisposal.remove();
			Event unloadFinish = new Event(currentTime+sites[1].eventUnloadTime(r.nextDouble()),"UF",sites[1]);
			eventList.add(unloadFinish);
		}
	}
	/**
	 * Does necessary jobs after unload finish. Also creates {@link #arriveMine(Event) arrive mining queue} and {@link #unloadStart() unload start} events and adds them to {@link #eventList event list}.
	 */
	private void unloadFinish(){
		System.out.printf("%s#%d is unloaded at %.4f\n",sites[1].getClass().getName(),sites[1].ID,currentTime);
		sites[1].loadedTime+=(currentTime - sites[1].loadFinishTime); // loaded time for this vehicle
		sites[1].incLoadTour(); //to measure carried total coal
		Event travelToMine = new Event(currentTime+sites[1].eventTravelToMineTime(r.nextDouble()),"MA",sites[1]);
		sites[1] = null;
		if(!queueDisposal.isEmpty()){
			Event unloadStart = new Event(currentTime,"US",queueDisposal.get());
			eventList.add(unloadStart);
		}
		eventList.add(travelToMine);
	}
	/**
	 * Adds vehicle to {@link #queueMining Mining Site queue}. Also creates {@link #loadStart() load start event} if Mining Site is empty and there is only this vehicle in the {@link #queueMining Mining Site queue}.
	 */
	private void arriveMine(Event e){
		queueMining.add(e.getVehicle());
		e.getVehicle().incTotalTour();//for average Round Trip Time for a vehicle type
		e.getVehicle().numTour++; //for travels for each vehicle
		e.getVehicle().incRoundTripTimeTotal(currentTime-e.getVehicle().roundTripTimeStart);
		if(sites[0]== null && queueMining.size()==1){//if there is nobody, start load
			Event loadStart = new Event(currentTime,"LS",queueMining.get());
			eventList.add(loadStart);
		}	
	}
	/**
	 * Reads type of event and runs this type of event. 
	 * If there is unknown type of event, it will print "Unknown Event" to the console.
	 * 
	 * @param e Event that will be read
	 */
	private void eventReader(Event e){
		if(e.getEventType().equalsIgnoreCase("LS")){
			loadStart();
		}else if(e.getEventType().equalsIgnoreCase("LF")){
			loadFinish();
		}else if(e.getEventType().equalsIgnoreCase("DA")){
			arriveDispose(e);
		}else if(e.getEventType().equalsIgnoreCase("US")){
			unloadStart();
		}else if(e.getEventType().equalsIgnoreCase("UF")){
			unloadFinish();
		}else if(e.getEventType().equalsIgnoreCase("MA")){
			arriveMine(e);
		}else
			System.out.println("Unknown Event");//this required condition to compile. in fact this will never be returned.
	}
	/**
	 * Prints statistical data about simulation.
	 */
	public void results(){
		if(allVehicles.length>0){//there is vehicles
				System.out.println("Simulation Results");
			for(Vehicle ve:allVehicles){//prints statistics about each vehicle
				System.out.println(ve);
			}
			if(numForklifts>0){//there is Forklifts
				if(Forklift.getTotalTour()>0){
					System.out.printf("Forklifts carried a total of %d tons with average %.4f mins round trip time.\n",(Forklift.getCapacity()*Forklift.getLoadTour()),(Forklift.getRoundTripTimeTotal()/Forklift.getTotalTour()));
				}else{//TotalTour is zero. so this  prevents num/0.
					System.out.println("Forklifts carried a total of "+(Forklift.getCapacity()*Forklift.getLoadTour())+" tons with average "+0+" mins round trip time.");
				}
			}		
			if(numTrucks>0){//there is Truck
				if(Truck.getTotalTour()>0){
					System.out.printf("Trucks carried a total of %d tons with average %.4f mins round trip time.\n",(Truck.getCapacity()*Truck.getLoadTour()),(Truck.getRoundTripTimeTotal()/Truck.getTotalTour()));
				}else{//TotalTour is zero. so this  prevents num/0.
					System.out.println("Trucks carried a total of "+(Truck.getCapacity()*Truck.getLoadTour())+" tons with average "+0+" mins round trip time.");
				}
			}		
			if(numLongTrucks>0){//there is Long Trucks
				if(LongTruck.getTotalTour()>0){
					System.out.printf("Long Trucks carried a total of %d tons with average %.4f mins round trip time.",(LongTruck.getCapacity()*LongTruck.getLoadTour()),(LongTruck.getRoundTripTimeTotal()/LongTruck.getTotalTour()));
				}else{//TotalTour is zero. so this  prevents num/0.
					System.out.println("Long Trucks carried a total of "+(LongTruck.getCapacity()*LongTruck.getLoadTour())+" tons with average "+0+" mins round trip time.");
				}	
			}
		}
	}
	   
}
