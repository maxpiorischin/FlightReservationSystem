//Maxim Piorischin 501015327
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;


public class FlightManager
{
  // Contains list of Flights departing from Toronto in a single day
	ArrayList<Flight> flights = new ArrayList<Flight>();
  
  String[] cities = 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
  
  // flight times in hours
  int[] flightTimes = { 3, // Dallas
  											1, // New York
  											7, // London
  											8, // Paris
  											16// Tokyo
  										};
  
  // Contains list of available airplane types and their seat capacity
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();
  
  Random random = new Random(); // random number generator - google "Java class Random". Use this in generateFlightNumber
  
  
  public FlightManager()
  {
  	// DO NOT ALTER THIS CODE - THE TA'S WILL USE IT TO TEST YOUR PROGRAM
  	// IN ASSIGNMENT 2 YOU WILL BE LOADING THIS INFORMATION FROM A FILE
  
  	// Create some aircraft types with max seat capacities
  	airplanes.add(new Aircraft(85, "Boeing 737"));
  	airplanes.add(new Aircraft(180,"Airbus 320"));
  	airplanes.add(new Aircraft(37, "Dash-8 100"));
  	airplanes.add(new Aircraft(12, "Bombardier 5000"));
  	airplanes.add(new Aircraft(592, 14, "Boeing 747"));
  	
  	// Populate the list of flights with some random test flights
  	String flightNum = generateFlightNumber("United Airlines");
  	Flight flight = new Flight(flightNum, "United Airlines", "Dallas", "1400", flightTimes[DALLAS], airplanes.get(0));
  	flights.add(flight);
  	flight.setStatus(Flight.Status.DELAYED);
  	
   	flightNum = generateFlightNumber("Air Canada");
   	flight = new Flight(flightNum, "Air Canada", "London", "2300", flightTimes[LONDON], airplanes.get(1));
   	flights.add(flight);
   	
   	flightNum = generateFlightNumber("Air Canada");
   	flight = new Flight(flightNum, "Air Canada", "Paris", "2200", flightTimes[PARIS], airplanes.get(1));
   	flights.add(flight);
   	
   	flightNum = generateFlightNumber("Porter Airlines");
   	flight = new Flight(flightNum, "Porter Airlines", "New York", "1200", flightTimes[NEWYORK], airplanes.get(2));
   	flights.add(flight);
   	
   	flightNum = generateFlightNumber("United Airlines");
   	flight = new Flight(flightNum, "United Airlines", "New York", "0900", flightTimes[NEWYORK], airplanes.get(3));
   	flights.add(flight);
   	flight.setStatus(Flight.Status.INFLIGHT);
   	
   	flightNum = generateFlightNumber("Air Canada");
   	flight = new Flight(flightNum, "Air Canada", "New York", "0600", flightTimes[NEWYORK], airplanes.get(2));
   	flights.add(flight);
   	flight.setStatus(Flight.Status.INFLIGHT);
   	
   	
   	flightNum = generateFlightNumber("United Airlines");
   	flight = new Flight(flightNum, "United Airlines", "Paris", "2330", flightTimes[PARIS], airplanes.get(0));
   	flights.add(flight);
   	
    /*
     * Add this code back in when you are ready to tackle class LongHaulFlight and have implemented its methods
     */
   	flightNum = generateFlightNumber("Air Canada");
   	flight = new LongHaulFlight(flightNum, "Air Canada", "Tokyo", "2200", flightTimes[TOKYO], airplanes.get(4));
   	flights.add(flight);
  }
  
  /*
   * This private helper method generates and returns a flight number string from the airline name parameter
   * For example, if parameter string airline is "Air Canada" the flight number should be "ACxxx" where xxx is 
   * a random 3 digit number between 101 and 300 (Hint: use class Random - see variable random at top of class)
   * you can assume every airline name is always 2 words. 
   * 
   */
    /**
     * Generates a flight number with the Random class
     * First takes the first letters in the two words of the airline name (Eg Air Canada AC)
     * Generates a random number from 0 to 199 and adds 101 to result to make it from 101 to 300
     * @param airline airline name passed in
     * */
  private String generateFlightNumber(String airline)
  {
    // Your code here
      String name = airline.charAt(0) +  "" + airline.charAt(airline.indexOf(" ") + 1);
      return name + (random.nextInt(199) + 101);
  }

  // Prints all flights in flights array list (see class Flight toString() method) 
  // This one is done for you!
    /**
     * Prints all the flights in the flights Arraylist
     * */
  public void printAllFlights()
  {
      for (Flight flight : flights) {
          System.out.println(flight.toString());
      }
  }
  
  // Given a flight number (e.g. "UA220"), check to see if there are economy seats available
  // if so return true, if not return false
    /**
     *Checks if seats are available by using the flightnumber to iterate through the flights and find the flight,
     * uses the .seatsAvailable() method from the Flight class to detemine if there are any available
     * @param flightNum used to find flight
     * @throws FlightNotFoundException if a flight isnt found
     * @throws FlightFullException if a flight is full
     * @return true if flight is successfully checked
     * */
  public boolean seatsAvailable(String flightNum) throws FlightNotFoundException, FlightFullException {
      for (Flight flight : flights) {
          if (flight.getFlightNum().equals(flightNum)) {
              if (flight.seatsAvailable()) {
                  return true;
              } else {
                  throw new FlightFullException(flightNum);
              }
          }
      }
      throw new FlightNotFoundException();

    // First check for a valid flight number
    // if it is not found, set errorMsg String and return false
    // To determine if the given flightNum is valid, search the flights array list and find 
    // the  Flight object with matching flightNum string
    // Once a Flight object is found, check if seats are available (see class Flight) 
    // if flight is full, set errorMsg to an appropriate message (see video) and return false
    // otherwise return true
  }
  
  
  // Given a flight number string flightNum and a seat type, reserve a seat on a flight
  // If successful, return a Reservation object
  // NOTE: seat types are not used for basic Flight objects (seats are all "Economy Seats")
  // class LongHaulFlight defines two seat types
  // I  suggest you first write this method *without* considering class LongHaulFlight 
  // once that works (test it!!), add the long haul flight code

    /**
     * Reserves seat on flight
     * Iterates through flights, finds a flight with the flight number parameter
     * Checks if first class, if it is, create a LongHaul object out of the flight, check if firstclass seats are available, return a new reservation
     * If economy, use flight.seatsAvailable(), return a new reservation
     * @param flightNum number used to find flight
     * @param seatType used to check if its firstclass
     * @throws FlightFullException if flight is full
     * @throws FlightNotFoundException if flight is not found
     * @throws DuplicateException if a duplicate passenger is found
     * @return reservation with parameters flightnum and flight.toString()
     * */
  public Reservation reserveSeatOnFlight(String flightNum, String seatType) throws FlightFullException, FlightNotFoundException , DuplicateException{
  	// Check for valid flight number by searching through flights array list
  	// If matching flight is not found, set instance variable errorMsg (see at top) and return null 
  	
  	// If flight found
  	//    
  	//		****beginning of long haul flight code - you may want to skip initially
  	//		check if seat type is first class and check if this is a long haul flight (Hint: use instanceof operator)
  	//    if above is true
  	//			call reserveSeat method in class LongHaulFlight
  	//			if long haul flight first class is not full
  	//				create Reservation object, set firstClass boolean variable to true in Reservation object
  	//				return reference to Reservation object
  	//			else long haul first class is full
  	//				set errorMsg and return null
  	//		***end of long haul flight code
  	//
  	//		else must be economy seat 
  	//			reserve seat on flight (see class Flight reserveSeat() and overridden reserveSeat() in class LongHaulFlight)
  	//      if flight not full
  	//				create Reservation object and return reference to Reservation object 
  	//			else set ErrorMsg (flight full) and return null
      for (Flight flight : flights) {
          if (flight.getFlightNum().equals(flightNum)) {
              if (seatType.equals(LongHaulFlight.firstClass) && flight instanceof LongHaulFlight){
                LongHaulFlight longflight = (LongHaulFlight) flight;
                if (longflight.reserveSeat(LongHaulFlight.firstClass)){
                    if (longflight.numFirstClassPassengers < longflight.aircraft.numFirstClassSeats) {
                        Reservation newRes = new Reservation(flightNum, longflight.toString() + "\n         FCL");
                        newRes.setFirstClass();
                        return newRes;
                    }
                    else{
                        throw new DuplicateException();
                    }

                }
                else{
                    throw new FlightFullException(LongHaulFlight.firstClass, flightNum);
                }
              }
              else{ //Economy flight
                  if (flight.seatsAvailable()) {
                  if (flight.reserveSeat()) {
                      return new Reservation(flightNum, flight.toString());
                  }
                  else{
                      throw new DuplicateException();
                  }
              }
              else {
                  throw new FlightFullException(flightNum);
              }
              }
          }
      }
      throw new FlightNotFoundException(flightNum);
  }
    /**
     * Reserves seat on flight
     * Iterates through flights, finds a flight with the flight number parameter
     * Use flight.seatsAvailable(), return a new reservation
     * @param flightNum number used to find flight
     * @param name used in creating Passenger
     * @param passport used in creating Passenger
     * @throws FlightFullException if flight is full
     * @throws FlightNotFoundException if flight is not found
     * @throws DuplicateException if a duplicate passenger is found
     * @return reservation with parameters flightnum and flight.toString()
     * */
  public Reservation reserveSeatOnFlightPSNGR(String flightNum, String name, int passport) throws FlightFullException, FlightNotFoundException, DuplicateException {
        for (Flight flight : flights) {
            if (flight.getFlightNum().equals(flightNum)) {
                if (flight.seatsAvailable()) {
                    Passenger passenger = new Passenger(name, passport, generateSeatNum(flight));
                        if (flight.reserveSeat(passenger)) {
                            return new Reservation(flightNum, flight.toString() + " " + passenger.toString(), passenger);
                        }
                        else{
                            throw new DuplicateException();
                        }
                    }
                    else {
                    throw new FlightFullException(flightNum);
                    }
                }
            }
        throw new FlightNotFoundException(flightNum);
        }

    /**
     * Generate a seat number and ensure the same seat number does not already exist
     * Create a list of numbers the size of the flight seats available
     * Create a list of passenger seats number from passengers arraylist
     * Remove the passenger seats from the numbers list, leaving with the seats available
     * use Random to get a random number, use it as an index to get a random seat
     * @param flight used to find amount of seats
     * @return a random seat number
     * */
    public int generateSeatNum(Flight flight){
      ArrayList<Integer> nums = new ArrayList<>();
      for (int i = 1; i < flight.aircraft.getNumSeats(); i++){
          nums.add(i);
      }
        ArrayList<Integer> nums1 = new ArrayList<>();
      for (Passenger passenger : flight.getPassengerList()){
          nums1.add(passenger.getSeatnum());
      }
      nums.removeAll(nums1);
      int index = random.nextInt(nums.size());
      return nums.get(index);
    }


  /*
   * Given a Reservation object, cancel the seat on the flight
   */
    /**
     * Cancel an existing reservation
     * Check if economy, if it is, search through flights and use.cancelseat() on the one that matches flight number from res
     * If first class, cast it too a Long Haul flight and cancel the seat from the long haul object
     * @param res reservation for checking firstclass and finding flightnumber
     * @return true if successful
     * */
  public boolean cancelReservation(Reservation res) {
      // Get the flight number string from res
      // Search flights to find the Flight object - if not found, set errorMsg variable and return false
      // if found, cancel the seat on the flight (see class Flight)

      // Once you have the above basic functionality working, try to get it working for canceling a first class reservation
      // If this is a first class reservation (see class Reservation) and the flight is a LongHaulFlight (Hint use instanceof)
      // then cancel the first class seat on the LongHaulFlight (Hint: you will need to cast)
      if (!res.isFirstClass()) { // if economy
          for (Flight flight : flights) {
              if (flight.flightNum.equals(res.getFlightNum())) {
                  flight.cancelSeat();
                  return true;
              }
          }
      }
      else{
          for (Flight flight : flights) {
              if (flight.flightNum.equals(res.getFlightNum())) {
                  LongHaulFlight longflight = (LongHaulFlight) flight;
                  longflight.cancelSeat(LongHaulFlight.firstClass);
                  return true;
              }
          }
      }
      return false;
  }
    /**
     * Cancel an existing passenger reservation
     * search through flights and use.cancelseat() on the one that matches flight number from res
     * @param res reservation for finding flightnumber
     * @param name for cancelseatPSNGR
     * @param passport for cancelseatPSNGR
     * @return true if successful
     * */
    public boolean cancelReservationPSNGR(Reservation res, int passport, String name) {
        for (Flight flight : flights) {
            if (flight.flightNum.equals(res.getFlightNum())) {
                flight.cancelSeatPSNGR(passport, name);
                return true;
            }
        }
        return false;
    }
  
  // Sort the array list of flights by increasing departure time 
  // Essentially one line of code but you will be making use of a Comparator object below
    /**
     * Sort the flights by departure time using the departure time comparator class
     * */
  public void sortByDeparture()
  {
      Collections.sort(flights, new DepartureTimeComparator());
  }
  // Write a simple inner class that implements the Comparator interface (NOTE: not *Comparable*)
  // This means you will be able to compare two Flight objects by departure time
  private class DepartureTimeComparator implements Comparator<Flight>
  {
      /**
       * overrides compare() from comparator interface, compares using departure time
       * @return 1 if object 1 departure time is greater, -1 if vice versa, 0 is equal
       * */
      @Override
      public int compare(Flight o1, Flight o2) {
          int o1time = Integer.parseInt(o1.getDepartureTime());
          int o2time = Integer.parseInt(o2.getDepartureTime());
          if (o1time > o2time){
              return 1;
          }
          if (o1time < o2time){
              return -1;
          }
          return 0;
      }
  }
  //Sort the array list of flights by increasing flight duration  
  // Essentially one line of code but you will be making use of a Comparator object below
    /**
     * Sort the flights by duration time using the duration time comparator class
     * */
  public void sortByDuration()
  {
	  Collections.sort(flights, new DurationComparator());
  }
  //Write a simple inner class that implements the Comparator interface (NOTE: not *Comparable*)
 // This means you will be able to compare two Flight objects by duration
  private class DurationComparator implements Comparator<Flight> {
      /**
       * overrides compare() from comparator interface, compares using duration time
       * @return 1 if object 1 duration time is greater, -1 if vice versa, 0 is equal
       * */
      @Override
      public int compare(Flight o1, Flight o2) {
          if (o1.getFlightDuration() > o2.getFlightDuration()) {
              return 1;
          }
          if (o2.getFlightDuration() > o1.getFlightDuration()) {
              return -1;
          }
          return 0;

      }
  }
  // Prints all aircraft in airplanes array list. 
  // See class Aircraft for a print() method
    /**
     * performs aircraft.print() on all aircrafts in airplanes arraylist
     * */
  public void printAllAircraft()
  {
  	for (Aircraft aircraft : airplanes){
  	    aircraft.print();
    }
  }
  
  // Sort the array list of Aircraft objects 
  // This is one line of code. Make sure class Aircraft implements the Comparable interface
    /**
     * sort aircrafts using the compareto method in aircraft
     * */
  public void sortAircraft()
  {
  	Collections.sort(airplanes);
  }

}
