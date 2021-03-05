/*
 * Name: Keagan Benson
 * PID: a15939849
 */

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Hash Table class using linear probing
 * 
 * @author Keagan Benson
 * @since 02/28/21
 */
public class HashTable implements IHashTable {

    /* the bridge for lazy deletion */
    private static final String bridge = new String("[BRIDGE]".toCharArray());
    private static final int TOTAL_CAP = 15; // the size of the hash table if no value is given
    private static final double LF_CAP = 0.55; // the load factor cut off to rehash
    private static final int DOUBLE_CAP = 2; // magic number to double capacity of hashtable

    /* instance variables */
    private int size; // number of elements stored
    private String[] table; // data table
    private int rehashCounter; // stores how many times we have rehashed
    private double loadFactor; // stores the load factor
    private int collisonCounter; // stores the amount of collisons made
    private ArrayList<String> stats; // stores the string representaion of the stats log

    /**
     * hash table constructor
     */
    public HashTable() {
        //intializes instance variables
        table = new String[TOTAL_CAP];
        size = 0;
        rehashCounter = 0;
        loadFactor = 0;
        collisonCounter = 0;
        stats = new ArrayList<String>();
    }

    /**
     * hash table constructor with capacity
     */
    public HashTable(int capacity) {
        this();
        if (capacity<5){
            throw new IllegalArgumentException();
        }
       table = new String[capacity];
    }

    @Override
    /**
     * value - a string value to be inserted in our table
     * inserts a string value at a specific location in our table array
     * rehashes table if lf is too high
     * returns - true if value is successfuly added and false if not
     * throws - null pointer exception if value == null
     *
     */
    public boolean insert(String value) {
        if(value == null){
            throw new NullPointerException();
        }
        // uses the hash function to get our index for the value
        int key = hashString(value);
        int buckets_probed = 0;
        // checks if value is already in table
        if(lookup(value)){
            return false;
        }
        //updates load factor
        loadFactor = (((double)size()) / capacity());
        // checks if we need to rehash table
        if( loadFactor > LF_CAP){
            rehashCounter++;
            // adds a rehash log to the stats list
            stats.add("Before rehash #" + rehashCounter + ": load factor " + String.format("%.2f",loadFactor)
                    + ", " + collisonCounter + " collison(s).\n");
            collisonCounter = 0;
            this.rehash();
            // must rehash the current value, old key is outdated
            key = hashString(value);
        }
        // checks for empty spot to insert key
        while(buckets_probed < capacity()){
            if(table[key] == null){
                table[key] = value;
                size++;
                return true;
            }
            // increments collisoncounter as well as key and bucket probed
            collisonCounter ++;
            key = (key+1) % capacity();
            buckets_probed++;
        }
        return false;
    }

    @Override
    /**
     * removes a value from the hashtable and replaces it with bridge
     * throws nullpointer exception if value is null
     * returns true if value was in table, false otherwise
     */
    public boolean delete(String value) {
        if(value ==  null){
            throw new NullPointerException();
        }
        int key = hashString(value);
        // checks if value is in table so we can remove irt
        if(lookup(value)){
           table[key] = bridge;
           size--;
           return true;
        }
        return false;
    }

    @Override
    /**
     * looks for a value in our hash table
     * returns true if value is in hashtable, false otherwise
     */
    public boolean lookup(String value) {
        int bucketsProbed = 0;
        int key = hashString(value);
        // makes sure value is not null and we have not looped through the whole table
        while(table[key] != null && bucketsProbed < capacity()){
            // checks if key is located where it maps to and if not checks the next space in the linear prob
            if(table[key].equals(value) && table[key] != bridge){
                return true;
            }
            key = (key+1)%capacity();
            bucketsProbed++;
        }
        return false;
    }

    @Override
    /**
     * returns the amount of items in has table
     */
    public int size() {
        return size;
    }

    @Override
    /**
     * return the size of the array (amount of open + closed spots)
     */
    public int capacity() {
        return table.length;
    }

    /**
     * loops through our stats array list and adds them to a string
     * @return a string representation of the statsLog
     */
    public String getStatsLog() {
        String statsLog = "";
        for(int i = 0; i< stats.size(); i++){
            statsLog = statsLog + stats.get(i);
        }
        return statsLog;
    }

    /**
     * helper method to rehash the table when it is too big
     */
    private void rehash() {
        // copies table to a placeholder so we can insert elements at different spots
        String[] placeHolder = Arrays.copyOf(table, capacity());
        // resets size since it is incremented
        size = 0;
        // creates new table with double the size
        table = new String[placeHolder.length * DOUBLE_CAP];
        for(int i = 0; i < placeHolder.length; i++){
            // adds values to new table which are not bridge or null
            if (placeHolder[i] != bridge && placeHolder[i] != null) {
                insert(placeHolder[i]);
            }
        }
    }

    /**
     * helper method to hash the values we want to put in our table
     * @param value - the value we want to hash
     * @return the location where the value should be hashed to
     */
    private int hashString(String value) {
        int hashValue = 0;
        for(int i = 0; i < value.length(); i++){
            int lSValue = hashValue << 5;
            int rSValue = hashValue >>> 27;
            hashValue = (lSValue | rSValue) ^ value.charAt(i);
        }
        return Math.abs(hashValue % capacity());
    }

    /**
     * Returns the string representation of the hash table.
     * This method internally uses the string representation of the table array.
     * DO NOT MODIFY. You can use it to test your code.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}
