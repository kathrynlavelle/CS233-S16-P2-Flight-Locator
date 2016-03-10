package edu.kings.cs233.proxy;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Class implementing a record of flight data. Instances of this class
 * can be used to retrieve the flight number, destination, departure time
 * and arrival time for a flight on a given day.
 * 
 * @author Kathryn Lavelle
 * @version 2016-03-09
 */
public class FlightRecord {
	/** File where all the records are stored. */
	private RandomAccessFile theFile;

	/** Offset into the file where this record would be stored. */
	private long offset;
	
	/** The size alloted for the flight number. */
	private final int FLIGHTNUMBER_SIZE = 4;
	
	/** The size alloted for the destination. */
	private final int DESTINATION_SIZE = 50;
	
	/** The size alloted for the departure time. */
	private final int DEPARTURE_SIZE = 4;
	
	/** The size alloted for the arrival time. */
	private final int ARRIVAL_SIZE = 4;
	
	/** The offset for this record's flight number. */
	private final int FLIGHTNUMBER_OFFSET = 0;
	
	/** The offset for this record's destination. */
	private final int DESTINATION_OFFSET = FLIGHTNUMBER_OFFSET + FLIGHTNUMBER_SIZE;
	
	/** The offset for this record's departure time. */
	private final int DEPARTURE_OFFSET = DESTINATION_OFFSET + DESTINATION_SIZE;
	
	/** The offset for this record's arrival time. */
	private final int ARRIVAL_OFFSET = DEPARTURE_OFFSET + DEPARTURE_SIZE;
	
	/** The length of this record. */
	private final int RECORD_LENGTH = ARRIVAL_OFFSET + ARRIVAL_SIZE;

		/**
		 * Create a new instance of this class for a record that is just now being
		 * added to the data file(flights.dat). This instance will need to be added to the end of
		 * the file (and extend the length of the file, if necessary).
		 * 
		 * @param fn
		 *            The flight number.
		 * @param dest
		 * 			  The flight's destination.
		 * @param depart
		 * 			  The flight's departure time.
		 * @param arrive
		 * 			  The flight's arrival time.
		 * @param file
		 *            RandomAccessFile in which this object is to be read.
		 */
		public FlightRecord(int fn, String dest, int depart, int arrive) {
			
		}
		
		/**
		 * Create a new instance of this class for a record that already existed in
		 * the data file(flights.dat). This instance does not need to be added to the file, since
		 * it is already there. This should be used when reading in the index file
		 * and creating the Map that it stands for.
		 * 
		 * @param off
		 *            Offset into the data file at which this record is located.
		 * @param file
		 *            RandomAccessFile in which this object is to be read.
		 */
		public FlightRecord(long off, RandomAccessFile file) {
			theFile = file;
			offset = off;
		}
		
		/**
		 * Compute the offset to the first byte to create a new record in the file.
		 * This location will need to be at the end of the file and, once this
		 * location is known, the file will need its length expanded by
		 * RECORD_LENGTH.
		 * 
		 * @return Offset in the file at which the new record will begin.
		 */
		private long computeOffsetForRecord() {
			try {
				long endOfFile = theFile.length();
				offset = endOfFile;
				theFile.setLength(endOfFile + RECORD_LENGTH);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return offset;
		}
		
		/**
		 * Stores the flight number into the data record.
		 * 
		 * @param fn
		 *            Flight number to be stored with the Map.
		 */
		private void setFlightNumber(int fn) {
			
		}
		
		/**
		 * Stores the flight destination into the data record.
		 * 
		 * @param dest
		 *            Flight destination to be stored with Map.
		 */
		private void setDestination(String dest) {
			
		}
		
		/**
		 * Stores the flight's departure time into the data record.
		 * 
		 * @param depart
		 *			 Flight departure time to be stored with Map.
		 */
		private void setDepartureTime(int depart) {
			
		}
		
		/**
		 * Stores the flight's arrival time into the data record.
		 * 
		 * @param arrive
		 */
		private void setArrivalTime(int arrive) {
			
		}
		
		/**
		 * Returns the flight number for this record.
		 * 
		 * @return Flight number recorded in the data file.
		 */
		private int getFlightNumber() {
			int flightNum = 0000;
			return flightNum;
		}
		
		/**
		 * Returns the destination for this record.
		 * 
		 * @return Destination recorded in the data file.
		 */
		private String getDestination() {
			String destination = "";
			return destination;
		}
		
		/**
		 * Returns the departure time for this record.
		 * 
		 * @return Departure time recorded in the data file.
		 */
		private int getDepartureTime() {
			int departure = 0;
			return departure;
		}
		
		/**
		 * Returns the arrival time for this record.
		 * 
		 * @return Arrival time recorded in the data file.
		 */
		private int getArrivalTime() {
			int arrival = 0;
			return arrival;
		}
}
