package edu.kings.cs233.proxy;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.kings.cs.util.ArrayPositionList;
import edu.kings.cs.util.Map;

/**
 * GUI application that records and locates all flights leaving from one airport over the course of a day.
 * 
 * @author Kathryn Lavelle
 * @version 2016-03-18
 */
public class FlightLocator implements ActionListener {
	/** Map of flight numbers to their corresponding FlightRecord. */
	private ListMap<Integer, FlightRecord> flightData;
	
	/** Map of departure times to a list of FlightRecords. */
	private ListMap<Long, ArrayPositionList<FlightRecord>> departData;
	
	/** Map of destinations to a list of FlightRecords. */
	private ListMap<String, ArrayPositionList<FlightRecord>> destData;
	
	/** Data file where all the records are stored. */
	private RandomAccessFile theFile;
	
	/** FlightNum index file. */
	private RandomAccessFile flightNumFile;
	
	/** DepartTime index file. */
	private RandomAccessFile departTimeFile;
	
	/** Destination index file. */
	private RandomAccessFile destinationFile;
	
	/** Main window component. */
	private JFrame mainFrame;
	
	/** Button that adds a new flight record. */
	private JButton addButton;
	
	/** Button that cancels a flight with given flight number. */
	private JButton cancelButton;
	
	/** Button that finds and displays a flight with given flight number. */
	private JButton listButton;
	
	/** Button that finds and displays a flight with given departure time. */
	private JButton whoisButton;
	
	/** Button that finds and displays the next flight to a given destination. */
	private JButton getawayButton;
	
	/** Button that delays a flight with given flight number. */
	private JButton delayButton;
	
	/** Button that shutdowns program. */
	private JButton quitButton;
	
	/** Text area for displaying flight information. */
	private JTextArea textDisplay;
	
	/** Text field for entering flight numbers. */
	private JTextField flightNumber;
	
	/** Text field for entering destinations. */
	private JTextField theDestination;
	
	/** Text field for entering departure times. */ 
	private JTextField departureTime;
	
	/** Text field for entering arrival times. */
	private JTextField arrivalTime;
	
	/** Text field for entring a time delay. */
	private JTextField delayTime;
	
	/** Error message for existing flight with same flight number. */
	private String flightExistsMessage;
	
	/** Error message for non-existing flight number. */
	private String flightDNEMessage;
	
	/**
	 * Creates a new FlightLocator instance.
	 */
	public FlightLocator() {
		startUp();
		
		addButton = new JButton("ADD");
		cancelButton = new JButton("CANCEL");
		listButton = new JButton("LIST");
		whoisButton = new JButton("WHOIS");
		getawayButton = new JButton("GETAWAY");
		delayButton = new JButton("DELAY");
		quitButton = new JButton("QUIT");
		
		addButton.addActionListener(this);
		cancelButton.addActionListener(this);
		listButton.addActionListener(this);
		whoisButton.addActionListener(this);
		getawayButton.addActionListener(this);
		delayButton.addActionListener(this);
		quitButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(listButton);
		buttonPanel.add(whoisButton);
		buttonPanel.add(getawayButton);
		buttonPanel.add(delayButton);
		buttonPanel.add(quitButton);
		
		textDisplay = new JTextArea("Fill in flight info, then select a command above");
		
		flightNumber = new JTextField();
		theDestination = new JTextField();
		departureTime = new JTextField();
		arrivalTime = new JTextField();
		delayTime = new JTextField();
		
		JLabel flightNumberLabel = new JLabel("Flight number:");
		JLabel destLabel = new JLabel("Destination:");
		JLabel departLabel = new JLabel("Departure time:");
		JLabel arrivalLabel = new JLabel("Arrival time:");
		JLabel delayLabel = new JLabel("Delay time");
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		inputPanel.add(flightNumberLabel);
		inputPanel.add(flightNumber);
		inputPanel.add(destLabel);
		inputPanel.add(theDestination);
		inputPanel.add(departLabel);
		inputPanel.add(departureTime);
		inputPanel.add(arrivalLabel);
		inputPanel.add(arrivalTime);
		inputPanel.add(delayLabel);
		inputPanel.add(delayTime);
		
		mainFrame = new JFrame("Flight Locator Tool");
		mainFrame.add(buttonPanel, BorderLayout.NORTH);
		mainFrame.add(textDisplay, BorderLayout.CENTER);
		mainFrame.add(inputPanel, BorderLayout.SOUTH);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setSize(800, 300);
		mainFrame.setVisible(true);
		
		flightExistsMessage = "*Sorry, there is already a flight with that flight number.";
		flightDNEMessage = "*Sorry, there is no record of that flight number.";
	}
	
	/**
	 * Adds a flight to the data file if there is not already a record with this flight number.
	 * @param flight_number The flight number.
	 * @param destination The flight destination.
	 * @param departure_time The departure time.
	 * @param arrival_time The arrival time.
	 */
	private void ADD(int flight_number, String destination, long departure_time, long arrival_time) {
		// If there is already a flight with this number, display a message.
		if (flightData.contains(flight_number)) {
			textDisplay.setText(flightExistsMessage);
		}
		else {
			// Add record to data file.
			FlightRecord record = new FlightRecord(flight_number, destination, departure_time, arrival_time, theFile);
			
			// Update flight data.
			flightData.add(flight_number, record);
			
			// Update destination data.
			ArrayPositionList<FlightRecord> flightlist;
			if (destData.contains(destination)) {
				flightlist = destData.get(destination);
				flightlist.add(record);
			}
			else {
				flightlist = new ArrayPositionList<>();
				flightlist.add(record);
				destData.add(destination, flightlist);
			}
			
			// Update departure data.
			ArrayPositionList<FlightRecord> list;
			if (departData.contains(departure_time)) {
				list = departData.get(departure_time);
				list.add(record);
			}
			else {
				list = new ArrayPositionList<>();
				list.add(record);
				departData.add(departure_time, list);
			}
			textDisplay.setText("Flight was added!");
		}
	}
	
	/**
	 * Locates and deletes a flight with the given flight number.
	 * @param flight_number The flight number.
	 */
	private void CANCEL(int flight_number) {
		// If there is no flight with matching number, display a message.
		if (!flightData.contains(flight_number)) {
			textDisplay.setText(flightDNEMessage);
		}
		else {
			// Locate flight record.
			FlightRecord record = flightData.get(flight_number);
			long departureTime = record.getDepartureTime();
			String destination = record.getDestination();
			
			// Delete flight record from data file.
			record.setFlightNumber(-1);
			record.setDestination("");
			record.setDepartureTime(-1);
			record.setArrivalTime(-1);
			
			// Remove record from flight data.
			flightData.remove(flight_number);
			
			// Remove record from departure data.
			for (ArrayPositionList<FlightRecord> list : departData.getValues()) {
				Iterator<FlightRecord> iter = list.iterator();
				while (iter.hasNext()) {
					FlightRecord fr = (FlightRecord) iter.next();
					if ((fr.getFlightNumber() == flight_number) && (list.size() > 1)) {
						iter.remove();
					}
					else if ((fr.getFlightNumber() == flight_number) && (list.size() == 1)) {
						departData.remove(departureTime);
					}
				}
			}
			textDisplay.setText("Flight was cancelled!");
		}
	}
	
	/**
	 * Finds and displays the flight number, destination, departure time and arrival time for the flight with the
	 * given flight number.
	 * @param flight_number The flight number.
	 */
	private void LIST(int flight_number) {
		// If there is no flight with matching number, display a message.
		if (!flightData.contains(flight_number)) {
			textDisplay.setText(flightDNEMessage);
		}
		else {
			// Get flight record.
			FlightRecord record = flightData.get(flight_number);
			textDisplay.setText("\nFlight number: " + record.getFlightNumber()
					+ "\nDestination: " + record.getDestination()
					+ "\nDeparture Time: " + record.getDepartureTime()
					+ "\nArrival Time: " + record.getArrivalTime());
		}
	}
	
	/**
	 * Finds and displays flight information and destination for flight departing at given departure time.
	 * @param departure_time The flight's departure time.
	 */
	private void WHOIS(long departure_time) {
		// If there is no flight departing at the given departure time, display a message.
		if (!departData.contains(departure_time)) {
			textDisplay.setText("*Sorry, there are no flights departing at this time.");
		}
		else {
			// Get flight record(s).
			ArrayPositionList<FlightRecord> list = departData.get(departure_time);
			Iterator<FlightRecord> iter = list.iterator();
			while (iter.hasNext()) {
				FlightRecord record = iter.next();
				if (record.getDepartureTime() == departure_time) {
					textDisplay.setText("\nFlight number: " + record.getFlightNumber()
							+ "\nDestination: " + record.getDestination()
							+ "\nDeparture Time: " + record.getDepartureTime()
							+ "\nArrival Time: " + record.getArrivalTime());
				}
			}
		}
	}
	
	/**
	 * Finds and prints the flight number, destination, departure time and arrival time for the next flight to the
	 * given destination.
	 * @param destination The flight's destination.
	 */
	private void GETAWAY(String destination) {
		// If there is not flight to the given destination, display a message.
		if (!destData.contains(destination)) {
			textDisplay.setText("*Sorry, there are no flights heading to that destination.");
		}
		else {
			// Sort destination data by departure time.
			// TODO
			
			// Get flight record.
			ArrayPositionList<FlightRecord> list = destData.get(destination);
			for (FlightRecord record : list) {
				if (record.getFlightNumber() == -1) {
					textDisplay.setText("*Sorry, the next flight to that destination has been cancelled.");
				}
				else {
					textDisplay.setText("\nFlight number: " + record.getFlightNumber()
							+ "\nDestination: " + record.getDestination()
							+ "\nDeparture Time: " + record.getDepartureTime()
							+ "\nArrival Time: " + record.getArrivalTime());
				}
			}
		}
	}
	
	/**
	 * Updates departure and arrival times for flight specified by flight number.
	 * @param flight_number
	 * @param time_delay
	 */
	private void DELAY(int flight_number, long time_delay) {
		// If there is no flight found with the given flight number, display a message.
		if (!flightData.contains(flight_number)) {
			textDisplay.setText(flightDNEMessage);
		}
		else {
			// Get flight record.
			FlightRecord record = flightData.get(flight_number);
			long departureTime = record.getDepartureTime();
			long arrivalTime = record.getArrivalTime();
			
			// Update departure and arrival times for this record in data file.
			record.setDepartureTime(departureTime + time_delay);
			record.setArrivalTime(arrivalTime + time_delay);
			
			// Change departure data to reflect delay.
			ArrayPositionList<FlightRecord> list = departData.get(departureTime);
			for (FlightRecord fr : list) {
				if (fr.getFlightNumber() == flight_number) {
					fr.setDepartureTime(departureTime + time_delay);
					fr.setArrivalTime(arrivalTime + time_delay);
				}
			}
			textDisplay.setText("*Flight was delayed!");
		}
	}
	
	/**
	 * 
	 */
	private void QUIT() {
		
	}
	
	/**
	 * Read and collect flight data from index files.
	 */
	private void startUp() {
		// Map of all datumMocks to prevent duplicates.
		Map<Long, FlightRecord> allMocks = new ListMap<Long, FlightRecord>();
		try {
			// Data file containing all flight information
			theFile = new RandomAccessFile("flights.dat", "rw");
			
			// Read index file and add flight numbers and offsets to datumMock. */
			flightData = new ListMap<Integer, FlightRecord>();
			flightNumFile = new RandomAccessFile("flightNum.idx", "rw");
			while (flightNumFile.getFilePointer() < flightNumFile.length()) {
				int num = flightNumFile.readInt();
				long position = flightNumFile.readLong();
				FlightRecord record = new FlightRecord(position, theFile);
				flightData.add(num, record);
				allMocks.add(position, record);
			}
			
			// Read index file and add departure times and offsets to datumMock.
			departData = new ListMap<Long, ArrayPositionList<FlightRecord>>();
			departTimeFile = new RandomAccessFile("departTime.idx", "rw");
			
			ArrayPositionList<FlightRecord> list;
			FlightRecord record = null;
			while (departTimeFile.getFilePointer() < departTimeFile.length()) {
				long time = departTimeFile.readLong();
				long position = departTimeFile.readLong();
				
				// For each existing flightRecord
				for (Long key : allMocks.getKeys()) {
					// If the flight already exists
					if (key.equals(position)) {
						// Get the proxy instance.
						record = allMocks.get(key);
					}
					// Else create a new proxy for this flight and add to map of all proxys.
					else {
						record = new FlightRecord(position, theFile);
						allMocks.add(position, record);
					}
				}
				
				if (departData.contains(time)) {
					list = departData.get(time);
					list.add(record);
				}
				else {
					list = new ArrayPositionList<>();
					list.add(record);
					departData.add(time, list);
				}
			}	
			
			// Read index file and add destinations and offsets to datumMock.
			destData = new ListMap<String, ArrayPositionList<FlightRecord>>();
			destinationFile = new RandomAccessFile("destination.idx", "rw");
			
			ArrayPositionList<FlightRecord> flightlist;
			FlightRecord flightrecord = null;
			while (destinationFile.getFilePointer() < destinationFile.length()) {
				String destination = destinationFile.readUTF();
				long position = destinationFile.readLong();
				
				// For each existing flightRecord
				for (Long key : allMocks.getKeys()) {
					// If the flight already exists
					if (key.equals(position)) {
						// Get the proxy instance.
						flightrecord = allMocks.get(key);
					}
					// Else create a new proxy for this flight and add to map of all proxys.
					else {
						flightrecord = new FlightRecord(position, theFile);
						allMocks.add(position, flightrecord);
					}
				}
				
				if (destData.contains(destination)) {
					flightlist = destData.get(destination);
					flightlist.add(flightrecord);
				}
				else {
					flightlist = new ArrayPositionList<>();
					flightlist.add(flightrecord);
					destData.add(destination, flightlist);
				}
			}
			//flightNumFile.close();
			//departTimeFile.close();
			//destinationFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write updated flight data to index files.
	 */
	private void shutDown() {
		
	}
	
	/**
	 * 
	 * @param args Array of string args from command line.
	 */
	public static void main(String[] args) {
		new FlightLocator();
	}

	/**
	 * Called whenever an object triggers an event.
	 * 
	 * @param arg0 Action event that was created.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			if (arg0.getSource() == quitButton) {
				shutDown();
			}
			else if (arg0.getSource() == delayButton) {
				DELAY(Integer.parseInt(flightNumber.getText()), Integer.parseInt(delayTime.getText()));
			}
			else if (arg0.getSource() == getawayButton) {
				GETAWAY(theDestination.getText());
			}
			else if (arg0.getSource() == whoisButton) {
				WHOIS(Integer.parseInt(departureTime.getText()));
			}
			else if (arg0.getSource() == listButton) {
				LIST(Integer.parseInt(flightNumber.getText()));
			}
			else if (arg0.getSource() == cancelButton) {
				CANCEL(Integer.parseInt(flightNumber.getText()));
			}
			else if (arg0.getSource() == addButton) {
				ADD(Integer.parseInt(flightNumber.getText()), theDestination.getText(), 
						Integer.parseInt(departureTime.getText()), Integer.parseInt(arrivalTime.getText()));
			}
		}
		catch (NumberFormatException e) {
			// DO nothing.
		}
	}
}
