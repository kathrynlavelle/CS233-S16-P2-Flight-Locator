package edu.kings.cs233.proxy;

import edu.kings.cs.util.ArrayPositionList;
import edu.kings.cs.util.Entry;
import edu.kings.cs.util.Map;

/**
 * Very simple map for use in showing why we need more complex ones. Implemented
 * as a pair of unsorted lists with indices
 * 
 * @param <K>
 *            Type of the keys used to find each Entry
 * @param <V>
 *            Type of the values to which each Key is mapped.
 * 
 * @author Maria Jump
 * @author Kathryn Lavelle
 * @version 2016-02-14
 */
public class ListMap<K, V> implements Map<K, V> {

	/**
	 * List holding all of the entries in our Map.
	 */
	private ArrayPositionList<ListEntry> entries;

	/**
	 * Create a new instance that can be used as a Map. This instance initially
	 * will be empty.
	 */
	public ListMap() {
		// Instantiate the Lists we will use.
		entries = new ArrayPositionList<ListEntry>();
	}

	/**
	 * Determine if the Map contains any key-value pairs.
	 * 
	 * @return True if the Map does not contain any entries; false otherwise.
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/**
	 * Compute the number of key-value pairs the Map holds.
	 * 
	 * @return Number of key-value pairs currently stored in the Map.
	 */
	public int size() {
		return entries.size();
	}

	/**
	 * Removes all entries from this collection.
	 */
	public void clear() {
		entries.clear();
	}

	/**
	 * Associates the given value with the given key. If an entry with the key
	 * was already in the map, this replaced the previous value with the new one
	 * and returns the old value. Otherwise, a new entry is added and null is
	 * returned.
	 * 
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            value to be associated with the specified key
	 * @return the previous value associated with the key (or null, if no such
	 *         entry)
	 * 
	 * @throws IllegalArgumentException
	 *             if either the specified key or value is null.
	 */
	public V add(K key, V value) throws IllegalArgumentException {
		V prevValue = null;
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		if (!entries.isEmpty() && contains(key)) {
			prevValue = get(key);
			entries.get(locatePosition(key)).setValue(value);
		}
		else {
			ListEntry newEntry = new ListEntry(key, value);
			entries.add(newEntry);
		}
		return prevValue;
	}

	/**
	 * Removes the entry with the specified key, if present, and returns its
	 * associated value. Otherwise does nothing and returns null.
	 * 
	 * @param key
	 *            the key whose entry is to be removed from the map
	 * @return the previous value associated with the removed key, or null if no
	 *         such entry exists
	 */
	public V remove(K key) {
		//TODO
		V removedValue = null;
		if (!entries.isEmpty() && contains(key)) {
			removedValue = get(key);
			entries.remove(locatePosition(key));
		}
		return removedValue;
	}

	/**
	 * Returns the value associated with a key.
	 * 
	 * @param key
	 *            Key for which we wish its associated value.
	 * @return The value to which the key is associated.
	 * @throws InvalidKeyException
	 *             Exception thrown if the given key is not mapped to a value.
	 */
	public V get(K key) {
		//TODO
		V result = null;
		int position = locatePosition(key);
		if (position != -1) {
			result = entries.get(position).getValue();
		}
		return result;
	}

	/**
	 * Sees whether a specific entry is in this map.
	 * 
	 * @param key
	 *            An object search key of the desired entry.
	 * @return True if key is associated with an entry in the map.
	 */
	public boolean contains(K key) {
		//TODO
		return locatePosition(key) != -1;
	}

	/**
	 * Returns an iterable collection of the keys contained in the map.
	 *
	 * @return iterable collection of the map's keys
	 */
	public Iterable<K> getKeys() {
		ArrayPositionList<K> keyIterable = new ArrayPositionList<K>();
		if (!entries.isEmpty()) {
			for (ListEntry entry : entries) {
				keyIterable.add(entry.getKey());
			}
		}
		return keyIterable;
	}

	/**
	 * Returns an iterable collection of the values contained in the map. Note
	 * that the same value will be given multiple times in the result if it is
	 * associated with multiple keys.
	 *
	 * @return iterable collection of the map's values
	 */
	public Iterable<V> getValues() {
		ArrayPositionList<V> valueIterable = new ArrayPositionList<V>();
		if (!entries.isEmpty()) {
			for (ListEntry entry : entries) {
				valueIterable.add(entry.getValue());
			}
		}
		return valueIterable;
	}

	/**
	 * Helper method to find the index of the provided key.
	 * 
	 * @param key
	 *            Key for which we wish its associated value.
	 * @return The index where the key is stored or -1 if the key is not in the
	 *         map
	 */
	private int locatePosition(K key) {
		//TODO
		boolean found = false;
		int index = 1;
		while (!found && index <= entries.size()) {
			ListEntry current = entries.get(index);
			if (current.getKey().equals(key)) {
				found = true;
			} else {
				index++;
			}
		}
		if (!found) {
			index = -1;
		}
		return index;
	}

	/**
	 * FOR TESTING PURPOSES ONLY! DO NOT REMOVE UNTIL AFTER GRADING!
	 * 
	 * @return List used to store this Map.
	 */
	protected ArrayPositionList<ListEntry> getList() {
		return entries;
	}

	/**
	 * FOR TESTING PURPOSES ONLY! DO NOT REMOVE UNTIL AFTER GRADING!
	 * 
	 * @param theEntries
	 *            List of entries that will used for this Map
	 */
	protected void setList(ArrayPositionList<ListEntry> theEntries) {
		entries = theEntries;
	}

	/**
	 * FOR TESTING PURPOSES ONLY! DO NOT REMOVE UNTIL AFTER GRADING!
	 * 
	 * @param k
	 *            The new key.
	 * @param v
	 *            The new value.
	 * @return A new instances of the Entry
	 */
	protected ListEntry createEntry(K k, V v) {
		return new ListEntry(k, v);
	}

	/** Private class representing an entry in the map. */
	protected class ListEntry implements Entry<K, V> {
		/** The key. */
		private K key;
		/** The value. */
		private V value;

		/**
		 * Constructor with initial values.
		 * 
		 * @param k
		 *            The initial value for the key.
		 * @param v
		 *            The initial value for the value.
		 */
		public ListEntry(K k, V v) {
			key = k;
			value = v;
		}

		/**
		 * Accessor method for the search key.
		 * 
		 * @return The search key.
		 */
		@Override
		public K getKey() {
			return key;
		}

		/**
		 * Accessor method for the associated value.
		 * 
		 * @return The associated value.
		 */
		@Override
		public V getValue() {
			return value;
		}

		/**
		 * Mutator method for the associated value. Returns the previous value
		 * if it exists.
		 * 
		 * @param dataValue
		 *            The new associated value.
		 * @return The previous value or null if non exists.
		 */
		@Override
		public V setValue(V dataValue) {
			V result = value;
			value = dataValue;
			return result;
		}
	}
}
