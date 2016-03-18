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
	/** The size alloted for the flight number. */
	private static final int FLIGHTNUMBER_SIZE = 4;
	
	/** The size alloted for the destination. */
	private static final int DESTINATION_SIZE = 50;
	
	/** The size alloted for the departure time. */
	private static final int DEPARTURE_SIZE = 8;
	
	/** The size alloted for the arrival time. */
	private static final int ARRIVAL_SIZE = 8;
	
	/** The offset for this record's flight number. */
	private static final int FLIGHTNUMBER_OFFSET = 0;
	
	/** The offset for this record's destination. */
	private static final int DESTINATION_OFFSET = FLIGHTNUMBER_OFFSET + FLIGHTNUMBER_SIZE;
	
	/** The offset for this record's departure time. */
	private static final int DEPARTURE_OFFSET = DESTINATION_OFFSET + DESTINATION_SIZE;
	
	/** The offset for this record's arrival time. */
	private static final int ARRIVAL_OFFSET = DEPARTURE_OFFSET + DEPARTURE_SIZE;
	
	/** The length of this record. */
	private static final int RECORD_LENGTH = ARRIVAL_OFFSET + ARRIVAL_SIZE;

	/** File where all the records are stored. */
	private RandomAccessFile theFile;

	/** Offset into the file where this record would be stored. */
	private long offset;
	
	
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
		public FlightRecord(int fn, String dest, long depart, long arrive, RandomAccessFile file) {
			theFile = file;
			offset = computeOffsetForRecord();
			setFlightNumber(fn);
			setDestination(dest);
			setDepartureTime(depart);
			setArrivalTime(arrive);
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
		public void setFlightNumber(int fn) {
			try {
				theFile.seek(offset + FLIGHTNUMBER_OFFSET);
				theFile.writeInt(fn);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Stores the flight destination into the data record.
		 * 
		 * @param dest
		 *            Flight destination to be stored with Map.
		 */
		public void setDestination(String dest) {
			try {
				theFile.seek(offset + DESTINATION_OFFSET);
				theFile.writeUTF(dest);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Stores the flight's departure time into the data record.
		 * 
		 * @param depart
		 *			 Flight departure time to be stored with Map.
		 */
		public void setDepartureTime(long depart) {
			try {
				theFile.seek(offset + DEPARTURE_OFFSET);
				theFile.writeLong(depart);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Stores the flight's arrival time into the data record.
		 * 
		 * @param arrive
		 * 			The arrival time to be stored with Map.
		 */
		public void setArrivalTime(long arrive) {
			try {
				theFile.seek(offset + ARRIVAL_OFFSET);
				theFile.writeLong(arrive);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Returns the flight number for this record.
		 * 
		 * @return Flight number recorded in the data file.
		 */
		public int getFlightNumber() {
			int flightNumber = -0;
			try {
				theFile.seek(offset + FLIGHTNUMBER_OFFSET);
				flightNumber = theFile.readInt();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return flightNumber;
		}
		
		/**
		 * Returns the destination for this record.
		 * 
		 * @return Destination recorded in the data file.
		 */
		public String getDestination() {
			String destination = "";
			try {
				theFile.seek(offset + DESTINATION_OFFSET);
				destination = theFile.readUTF();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return destination;
		}
		
		/**
		 * Returns the departure time for this record.
		 * 
		 * @return Departure time recorded in the data file.
		 */
		public long getDepartureTime() {
			long departureTime = -0;
			try {
				theFile.seek(offset + DEPARTURE_OFFSET);
				departureTime = theFile.readLong();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return departureTime;
		}
		
		/**
		 * Returns the arrival time for this record.
		 * 
		 * @return Arrival time recorded in the data file.
		 */
		public long getArrivalTime() {
			long arrivalTime = -0;
			try {
				theFile.seek(offset + ARRIVAL_OFFSET);
				arrivalTime = theFile.readLong();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return arrivalTime;
		}
}
