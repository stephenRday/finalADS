import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BusProject {
	
	// class variables
	static ArrayList<Stop> stops = new ArrayList<Stop>();
	static ArrayList<Transfer> transfers = new ArrayList<Transfer>();
	static ArrayList<StopTime> stopTimes = new ArrayList<StopTime>();
	static TernarySearchTree TST = new TernarySearchTree();
	static ArrayList<StopTime> foundTrips = new ArrayList<StopTime>();
	
	

	public static void main(String[] args) {
		
		boolean exit = false;
		
		if(ReadInFiles() == false)
		{
			System.out.println("Error when reading in files");
			exit = true;
		}
		
		if(processStopTimes() == false)
		{
			System.out.println("Error when processing stop times");
			exit = true;
		}	
		
		System.out.println("The max number of connected stops is " + countMaxConnections());
		
		if(processTransfers() == false)
		{
			System.out.println("Error when processing transfers");
			exit = true;
		}	
		
		System.out.println("The max number of connected stops is " + countMaxConnections());	
		showNames(2);

		
		// Read in the TST
		
		if(addStopsToTST() == false)
		{
			System.out.println("Error when populating tree");
			exit = true;
		}	
		
		
		while(exit == false)
		{
			System.out.println("Choose which function you want to use or exit the program by inputing the corresponding integer. The options are: \n"
					+ "'1' to find shortest path between 2 bus stops.\n"
					+ "'2' to search for a bus stop.\n"
					+ "'3' to search for all trips with a given arrival time.\n"
					+ "'4' to exit the program.\n");
			System.out.print("Enter your option here: ");
			
			Scanner input = new Scanner( System.in );
			
			try {
				int option = input.nextInt();
				
				System.out.println("Option selected is " + option);
				
				if (option == 1)
				{
					System.out.print("Enter the starting bus stop: ");
					int fromStopId = input.nextInt();
					System.out.print("Enter the ending bus stop: ");
					int toStopId = input.nextInt();
					if(findRoute(fromStopId,toStopId) == false) {
						System.out.println("There was an error trying to find the route.");
					}
					
					
				}
				else if (option == 2)
				{
					if (input.hasNextLine()) input.nextLine();
					
					String startingText = "";
					
					while (!startingText.equals("exit"))
					{
					
						System.out.print("Enter the text to search for at the start of the bus stop name (or exit to return): ");
						startingText = input.nextLine();
						
						if (startingText.length() == 0)
						{
							System.out.println("Please enter a starting string to search for or type exit to return to the main menu");
							
						}
						else if (startingText.equals("exit"))
						{
							System.out.println("Returning to main menu.");
						}
						else
						{
							System.out.println("Trying to find stops which start with " + startingText);
						
							// test the TST etc
							ArrayList<String> matchingStopList = TST.getMatchingStops(startingText);
							if (matchingStopList.size() == 0) System.out.println("Sorry - did not find any stops which matched " + startingText + "\n\n");
							else
							{
								System.out.println("Found " + matchingStopList.size() + " stops that matched " + startingText);
								for(int i = 0; i<matchingStopList.size();i++)
								{
									System.out.println("" + (i+1) + " stop found is: "+ matchingStopList.get(i));
								}
								System.out.println("\n");
							}
						}
						
					}
					
				}
				else if (option == 3)
				{
					// ask the user for a time
					// check if it is valid and not over 23:59:59
					// tell user searching for trips with particular time
					// restart the found trips arraylist as empty
					ArrayList<StopTime> foundTrips = new ArrayList<StopTime>();
					// call getmatching
					// call sort
					// call print
					
				}
				else if (option == 4)
				{
					exit = true;
					System.out.println("The program is now exiting.");
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Please enter a valid input.\n");
			}
			
			//else System.out.println("Please enter a valid input.");
		}
		
		System.out.println("Finished");
	}
	
	public static boolean ReadInFiles() {
		
		boolean success = true;
		
		success = readInStops("stops.txt");
		if (success == false) return false;
		success = readInTransfers("transfers.txt");
		if (success == false) return false;
		success = readInStopTimes("stop_times.txt");
		if (success == false) return false;
		
		return true;			
		
	}
	
	public static boolean readInStops(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); // consume header
			line = br.readLine(); // read first line
			while(line != null) {
				
				Stop stop = new Stop(line);
				stops.add(stop);
					
				line = br.readLine();
			}
			System.out.println("I have read in and created " + stops.size() + " stops");
			return true;
			
			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Could not find file called " + filename + ".\n");
			return false;
		} catch (java.io.IOException e) {
			System.out.println("Error while reading in file called " + filename + ".\n");
			return false;
		}		
		
	}
	
	public static boolean readInTransfers(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); // consume header
			line = br.readLine(); // read first line
			while(line != null) {
				
				Transfer transfer = new Transfer(line);
				transfers.add(transfer);
					
				line = br.readLine();
			}
			System.out.println("I have read in and created " + transfers.size() + " transfers");
			return true;
			
			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Could not find file called " + filename + ".\n");
			return false;
		} catch (java.io.IOException e) {
			System.out.println("Error while reading in file called " + filename + ".\n");
			return false;
		}		
		
	}
	
	public static boolean readInStopTimes(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); // consume header
			line = br.readLine(); // read first line
			while(line != null) {
				
				StopTime stopTime = new StopTime(line);
				stopTimes.add(stopTime);
					
				line = br.readLine();
			}
			System.out.println("I have read in and created " + stopTimes.size() + " stop times");
			return true;
			
			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Could not find file called " + filename + ".\n");
			return false;
		} catch (java.io.IOException e) {
			System.out.println("Error while reading in file called " + filename + ".\n");
			return false;
		}		
		
	}
	
	public static boolean processStopTimes() {
		
		// Need to use trip id from stop times to figure out connected stops
		
		int prevStopId = -1;
		int prevTripId = -1;
		
		int curStopId = - 1;
		int curTripId = - 1;
		
		int countConnectionsAdded = 0;
		
		for(int i=0; i < stopTimes.size(); i++)
		{
			StopTime tempStopTime = stopTimes.get(i);			
			curStopId = tempStopTime.getStop_id();
			curTripId = tempStopTime.getTrip_id();
			
			if(prevTripId == curTripId)
			{
				// Add current stop id as connected stop of prev id
				boolean stopFound = false;
				for(int j=0; j<stops.size() && stopFound == false; j++)
				{
					if(stops.get(j).getStop_id() == prevStopId)
					{
						stopFound = true;
						if (stops.get(j).addConnectedStop(curStopId, 1) == true)
						{
							countConnectionsAdded++;
						}	
					}
				}
				if(stopFound == false) System.out.println("Stop number " + prevStopId + " was not found.");
			}
			prevStopId = curStopId;
			prevTripId = curTripId;
		}
		System.out.println("I have added " + countConnectionsAdded + " connections from Stop Times.");
		
		return true;
		
	}
	
	public static boolean processTransfers() {
			
		//from_stop_id,to_stop_id,transfer_type,min_transfer_time
		
			int countConnectionsAdded = 0;
			
			for(int i=0; i < transfers.size(); i++)
			{
				Transfer tempTransfer = transfers.get(i);	
				int cost = 0;
				if (tempTransfer.getTransfer_type() == 2)
				{
					cost = tempTransfer.getMin_transfer_time()/100;
				}
				else if (tempTransfer.getTransfer_type() == 0)
				{
					cost = 2;
				}
				else
				{
					cost = 100;
					System.out.println("Transfer type is invalid (Not 2 or 0)");
				}
				int fromStopId = tempTransfer.getFrom_stop_id();
				int toStopId = tempTransfer.getTo_stop_id();
				
				
					// Add the to stop id as connected stop of from stop id
					boolean stopFound = false;
					for(int j=0; j<stops.size() && stopFound == false; j++)
					{
						if(stops.get(j).getStop_id() == fromStopId)
						{
							stopFound = true;
							if (stops.get(j).addConnectedStop(toStopId, cost) == true)
							{
								countConnectionsAdded++;
							}	
						}
					}
					if(stopFound == false) System.out.println("Stop number " + fromStopId + " was not found.");

			}
			System.out.println("I have added " + countConnectionsAdded + " connections from transfers.");
			
			return true;
			
		}

	
	public static boolean addStopsToTST()
	{
		// go through all of the stops and add the fixed name to the TST
		try
		{
			// stops is an arraylist
			// TST is the tree and TST.insert(String) inserts a name
					
			for(int i=0; i<stops.size();i++)
			{
				TST.insert(stops.get(i).getFixedStopName());
			}	
			
			System.out.println("Added " + TST.countStopsNamed() + " to the tree");
		}
		catch (Exception e)
		{
			System.out.println("Error on populating Tree\n");
//			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public static int countMaxConnections() {
		
		int countMax = 0;
		
		for(int i=0; i < stops.size(); i++)
		{
			int current = 0;
			current = stops.get(i).getConnectedStops().size();
			if(current > countMax) countMax = current;
		}
		
		return countMax;
	}
	
	public static boolean findRoute(int fromStopId, int toStopId) {
		
		System.out.println("Trying to find a route from " + fromStopId + " to " + toStopId + ".\n\n");
		
		boolean stopIdFound = false;
		//Stop currentStop;
		
		// Finding fromStopId in stops (array list0
		for(int i=0; i<stops.size() && stopIdFound == false; i++)
		{
			if(stops.get(i).getStop_id() == fromStopId)
			{
				stopIdFound = true;
				//currentStop = stops.get(i);
			}
		}
		
		if(stopIdFound == false)
		{
			System.out.println("The fromStopId " + fromStopId + " is not in the array list stops.");
			return false;
		}
		
		stopIdFound = false;
		for(int i=0; i<stops.size() && stopIdFound == false; i++)
		{
			if(stops.get(i).getStop_id() == toStopId)
			{
				stopIdFound = true;
			}
		}
		
		if(stopIdFound == false)
		{
			System.out.println("The toStopId " + toStopId + " is not in the array list stops.");
			return false;
		}
		
		// got here so fromStopId and the toStopId are in the list
		return findDikRoute(fromStopId,toStopId);
	}
	
	public static boolean findDikRoute(int fromStopId, int toStopId) {
		System.out.println("Trying to find DIK route from " + fromStopId + " to " + toStopId + ".\n\n");
		
		ArrayList<int[]> routeTable = new ArrayList<int[]>(); // Route table with toStopId, cost and lastStopId
		ArrayList<Integer> stopsToProcess = new ArrayList<Integer>();
		
		stopsToProcess.add(fromStopId);
		
		int[] newRoute = new int[] {fromStopId,0,fromStopId};
		routeTable.add(newRoute); // add the current stop into route table
		
		String routeMessage = "";
		
		while(stopsToProcess.size() > 0)
		{
			// get the current stop id and save it into local variable AND get cost of current stop and save it locally
			int currentStopId = stopsToProcess.get(0);
			int currentStopCost = 0;
			boolean foundRoute = false;
			for (int i = 0; i< routeTable.size() && foundRoute == false; i++)  // find the entry in the route table for the current stop id)
			{
				if (routeTable.get(i)[0] == currentStopId)
						{	currentStopCost = routeTable.get(i)[1];
							foundRoute = true;
						}
				
			}
			// remove it from the stops to process /////// use arraylist.remove to remove 0th item every time
			stopsToProcess.remove(0);
			
			// process current stop id
			
			// get stop that has that id
			// get list of connected edges (arraylist) from that stop
			
			ArrayList<int[]> connectedStops = new ArrayList<int[]>();

			boolean foundStop = false;
			for (int i = 0; i < stops.size() && foundStop == false; i++)
			{
				if (stops.get(i).getStop_id()==currentStopId)
					{foundStop = true;
					connectedStops = stops.get(i).getConnectedStops();
					}
			}
			// for all the connected stops, update the route table if needed
			for (int i = 0; i < connectedStops.size(); i++)
			{
				int connectedId = connectedStops.get(i)[0];
				int connectionCost = connectedStops.get(i)[1];
				boolean routeFound = false;
				for (int j = 0; j< routeTable.size() && routeFound == false; j++)
				{
					if (routeTable.get(j)[0]== connectedId)
					{
						// if the connected stop is there, calculate if cost of getting there less than already saved there and if so update the cost and the last stop
						if (routeTable.get(j)[1] > currentStopCost + connectionCost)
						{
							routeTable.get(j)[1] = currentStopCost + connectionCost;
							routeTable.get(j)[2] = currentStopId;
				
							// if we are updating the stop in the route table because we found cheaper way of getting there then we add the updated connection to the stops to process.
							// if adding a number to the stops to process, don't add if already there or if it is the final toStopId aiming for	
							boolean stopFoundInStopsToProcess = false;
							if (connectedId == toStopId) stopFoundInStopsToProcess = true;
							for (int k= 0 ; k < stopsToProcess.size() && stopFoundInStopsToProcess == false ; k++)
							{
								if (stopsToProcess.get(k)== connectedId) stopFoundInStopsToProcess = true;
							}
							if (stopFoundInStopsToProcess == false) stopsToProcess.add(connectedId);
						}
								
						routeFound = true;
					}
				}
				// if the connected stop isn't in route table, add it with updated cost (cost to here plus the cost of this connection)
				if(routeFound == false)
				{ // add the route to the route table
					newRoute = new int[] {connectedId,currentStopCost + connectionCost,currentStopId};
					routeTable.add(newRoute); // add the current stop into route table
			
					// if adding a number to the stops to process, don't add if already there or if it is the final toStopId aiming for	
					boolean stopFoundInStopsToProcess = false;
					if (connectedId == toStopId) stopFoundInStopsToProcess = true;
					for (int k= 0 ; k < stopsToProcess.size() && stopFoundInStopsToProcess == false ; k++)
					{
						if (stopsToProcess.get(k)== connectedId) stopFoundInStopsToProcess = true;
					}
					if (stopFoundInStopsToProcess == false) stopsToProcess.add(connectedId);
				}
				
				
				
			
			} // end for - dealt with the connected stops
			
			} // end while
		// If the toStopId is in the table then we can get there and we can print out the cost and return true
		boolean foundRoute = false;
		for (int i = 0; i< routeTable.size() && foundRoute == false; i++)
		{
			if (routeTable.get(i)[0]== toStopId)
			{
				foundRoute = true;
				System.out.println("Wey hey.  We found a route and the cost is " + routeTable.get(i)[1] + " and the last stop before was " + routeTable.get(i)[2]+ "\n\n");
				 boolean finishedRoute = false;
				 int thisStop = toStopId;
				 while (finishedRoute == false)
				 {
					 int lastStop = getLastStop(thisStop, routeTable);
					//System.out.println ("The route includes going from " + lastStop + " to " + thisStop);
					routeMessage =("The route includes going from " + lastStop + " to " + thisStop + "\n") +  routeMessage;
					if (lastStop == fromStopId) finishedRoute = true;
	 
					 thisStop = lastStop;
				 }
				 System.out.println(routeMessage);
			}
			
		}

		// if the toStop id is not in the route table then print out error can't get there.
		if (foundRoute == false)
		{
			System.out.println("Sorry there is no route I could find from stop id " + fromStopId + " to stop id " + toStopId + "\n\n");
			return false;
		}
		
		return true;
	}
	
	public static int getLastStop(int thisStop, ArrayList<int[]> routeTable )
	{
		boolean routeFound = false;
		for (int i = 0; i< routeTable.size() && routeFound == false; i++)
		{
			if (routeTable.get(i)[0] == thisStop)
			{
				routeFound = true;
				return routeTable.get(i)[2];
			}
		}
		return -1;
	}
	
	public static void showNames(int howMany)
	{
		for (int i = 0; i< stops.size() && i <howMany; i++)
			System.out.println(stops.get(i).getStop_id() + ":" + stops.get(i).getStop_name() + " which then is fixed to :" + stops.get(i).getFixedStopName());
	}
	
	public static void getMatchingTrips(String findArrivalTime)
	{
		// run through all of the stoptimes and select the trips where the arrival time is the same as findArrival time
		// add the stoptime to the arraylist

	}
	
	public static void sortMatchingTrips()
	{
		// sort the arraylist of stop times by trip id
	}
	
	public static void printMatchingTrips()
	{
		// run through the array list and print out the toString of each stop time in the already sorted list
	}
	
}
