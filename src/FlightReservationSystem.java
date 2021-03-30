// Maxim Piorischin 501015327
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		System.out.println("Flight System for one single day at YYZ Departing flights!!");
		// Create a FlightManager object
		FlightManager manager = new FlightManager();

		// List of reservations that have been made
		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations

		Scanner scanner = new Scanner(System.in); //scanner

		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) continue;

			// The command line is a scanner that scans the inputLine string
			// For example: list AC201
			Scanner commandLine = new Scanner(inputLine);
			
			// The action string is the command to be performed (e.g. list, cancel etc)
			String action = commandLine.next();

			if (action == null || action.equals("")) continue;

			if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT")) //quits program
				return;
			
			// List all flights
			else if (action.equalsIgnoreCase("LIST")) // use printAllFlights
			{
				manager.printAllFlights(); 
			}
			// Reserve a flight based on Flight number string (example input: res AC220)
			/**
			 * use try to use manager.reserveseatonflight with economy cosntant string
			 * add new reservations to myreservations list, print the reservation
			 * catch any Exceptions thrown and print their message
			 * */
			else if (action.equalsIgnoreCase("RES")) {
				// Get the flight number string from the commndLine scanner (use hasNext() to check if there is a
				// flight number string entered

				// call reserveSeatOnFlight() method in manager passing in the flight number string
				// A reference to a Reservation object is returned. Check to make sure it is not == null
				// If it is null, then call manager.getErrorMessage() and print the message
				// If it is not null, add the reservation to the myReservations array list and print the reservation (see class Reservation)

				if (commandLine.hasNext()) {
					String flightNum = commandLine.next();
					try {
						Reservation reservation = manager.reserveSeatOnFlight(flightNum, LongHaulFlight.economy);
						myReservations.add(reservation);
						reservation.print();
					}
					catch (FlightFullException | FlightNotFoundException | DuplicateException f){
						System.out.println(f.getMessage());
					}

				}
			}
				

		  // Reserve a first class seat on a flight based on Flight number string (example input: res AC220)
			/**
			 * get flightnum from scanner
			 * use try to use manager.reserveseatonflight with firstclass constant string
			 * add new reservations to myreservations list, print the reservation
			 * catch any Exceptions thrown and print their message
			 * */
			else if (action.equalsIgnoreCase("RESFCL")) {
				// Same as above except call 
				// manager.reserveSeatOnFlight() and pass in the flight number string as well as the string constant:
				// LongHaulFlight.firstClass (see class LongHaulFlight)
				// Do the LongHaulFlight class and this command after all the basic functionality is working for regular flights
				if (commandLine.hasNext()) {
					String flightNum = commandLine.next();
					try {
						Reservation reservation = manager.reserveSeatOnFlight(flightNum, LongHaulFlight.firstClass);
						myReservations.add(reservation);
						reservation.print();
					}
					catch (FlightFullException | FlightNotFoundException | DuplicateException f){
						System.out.println(f.getMessage());
					}

				}
			}
			/**
			 * get flightnum, firstname, lastname, passport number from scanner
			 * concatenate first and last name
			 * try creating a reservation and adding to myreservations
			 * catch any exceptions and print their message
			 * */
			else if (action.equalsIgnoreCase("RESPSNGR")) //todo write in readme that the format must be >respsngr AC101 Maxim Piorischin 123
			{
				if (commandLine.hasNext()){
					try {
						String flightNum = commandLine.next();
						String fName = commandLine.next() + " ";
						String lName = commandLine.next();
						String name = fName.concat(lName);
						int passport = commandLine.nextInt();
						try {
							Reservation reservation = manager.reserveSeatOnFlightPSNGR(flightNum, name, passport);
							myReservations.add(reservation);
							reservation.print();
						} catch (FlightFullException | FlightNotFoundException | DuplicateException f) {
							System.out.println(f.getMessage());
						}
					}
					catch(NoSuchElementException e){
						System.out.println("Invalid Information, Format must be >respsngr flight# firstname lastname passport#");
					}
				}
			}
			// Query the flight manager to see if seats are still available for a specific flight (example input: seats AC220)
		  // This one is done for you as a guide for other commands
			/**
			 * get flightnum from scannerm use manager.seatsavailable() with flightnum parameter
			 * */
			else if (action.equalsIgnoreCase("SEATS"))
			{
				String flightNum;

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
					try {
						manager.seatsAvailable(flightNum);
						System.out.println("Seats are available");

					}
					catch (FlightFullException | FlightNotFoundException f) {
						System.out.println(f.getMessage());
					}
				}
			}
			// Cancel an existing reservation (example input: cancel AC220)
			/**
			 * get flightnum from scanner, iterate through myReservations with it
			 * remove the reservation with the specified flightnum using manager.cancelreservation
			 * */
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				String flightNum;
				boolean exists = false;
				if (commandLine.hasNext()){
					flightNum = commandLine.next();
					for (Reservation reservation : myReservations){
						if (reservation.getFlightNum().equals(flightNum)){
							manager.cancelReservation(reservation);
							myReservations.remove(reservation);
							exists = true;
							break;

						}
					}
					try {
						if (!exists) {
							throw new ReservationNotFoundException();
						}
					}
					catch (ReservationNotFoundException e) {
						System.out.println(e.getMessage());
					}

				}
        // get the flight number string from commandLine scanner (check if there is input first)
				// Use the flight number to find the Reservation object in the myReservations array list
				// If the reservation is found,  
				// 		call cancelReservation() method in the flight manager
				//    remove the reservation from myReservations
				// If the reservation is not found, print a message (see video)
			}
			/**
			 * get flightnum, firstname, lastname, passport number from scanner
			 * concatenate first and last name
			 * try cancelling a reservation and removing to myreservations
			 * exist boolean checks if a reservation existed or to ensure the wright exception was being used
			 * catch any exceptions and print their message
			 * */
			else if (action.equalsIgnoreCase("CANCELPSNGR")) {//format must be >cancelpsngr AC101 Maxim Piorischin 123
				String flightNum;
				boolean exists = false;
				if (commandLine.hasNext()) {
					try {
						flightNum = commandLine.next();
						String fName = commandLine.next() + " ";
						String lName = commandLine.next();
						String name = fName.concat(lName);
						int passport = commandLine.nextInt();
						for (Reservation reservation : myReservations) {
							if (reservation.getFlightNum().equals(flightNum) && name.equals(reservation.getPassenger().getName()) && passport == reservation.getPassenger().getPassport()) {
								manager.cancelReservationPSNGR(reservation, passport, name);
								myReservations.remove(reservation);
								exists = true;
								break;

							}
						}
						try {
							if (!exists) {
								throw new ReservationNotFoundException();
							}
						} catch (ReservationNotFoundException e) {
							System.out.println(e.getMessage());
						}
					}
					catch (NoSuchElementException e){
						System.out.println("Invalid Information, Format must be >cancelpsngr flight# firstname lastname passport#");
					}
				}
			}
      // Print all the reservations in array list myReservations
			/**
			 * iterate through reservations and print all reservations
			 * */
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation reservation : myReservations){
					reservation.print();
				}
			}
			/**
			 * iterate through passengers in a specified flight and print them out with .toString()
			 * */
			else if (action.equalsIgnoreCase("PSNGRS"))
			{
				try {
					String flightnum = commandLine.next();

					for (Flight flight : manager.flights) {
						if (flight.getFlightNum().equals(flightnum)) {
							for (Passenger passenger : flight.getPassengerList()) {
								System.out.println(passenger.toString());
							}
						}
					}
				}
				catch (NoSuchElementException e){
					System.out.println("Invalid Information, Format must be >psngrs flight#");
				}
			}

			// Print the list of aircraft (see class Manager)
			else if (action.equalsIgnoreCase("CRAFT"))
		  {
			  manager.printAllAircraft();
			}
			// These commands can be left until we study Java interfaces
			// Feel free to implement the code in class Manager if you already understand interface Comparable
			// and interface Comparator
			/**
			 * Sorts activate the manager.sortBy method
			 * */
			else if (action.equalsIgnoreCase("SORTCRAFT"))
		  {
		  	manager.sortAircraft();
		  }
		  else if (action.equalsIgnoreCase("SORTBYDEP"))
		  {
			  manager.sortByDeparture();
			  
		  }
		  else if (action.equalsIgnoreCase("SORTBYDUR"))
		  {
			  manager.sortByDuration();
		  }
	  
			System.out.print("\n>");
		}
	}

	
}

