/*
 * NAME: Keagan Benson
 * PID: A15939849
 */

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * LineCounter class used to find similarities between files
 * 
 * @author Keagan Benson
 * @since 3/3/21
 */
public class LineCounter {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;

    /**
     * Method to print the filename to the console
     * @param filename filename to print
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to the console
     * @param compareFileName name of the file being compared
     * @param percentage similarity percentage
     */
    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    /**
     * Main method.
     * @param args names of the files to compare
     */
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        // Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];

        // Preprocessing: Read every file and create a HashTable
        String check = "";
        for (int i = 0; i < numArgs; i++) {
            File file = new File(args[i]);
            HashTable tbl = new HashTable();
            try(Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String value = scanner.nextLine();
                    check = value;
                    tbl.insert(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            tableList[i] = tbl;
        }

        // Find similarities across files

        for (int i = 0; i < numArgs; i++) {
            printFileName(args[i]);
            for(int j = 0; j<numArgs;j++) {
                int total = 0;
                int similar = 0;
                if (i != j) {
                    File file = new File(args[i]);
                    try(Scanner scanner = new Scanner(file)) {
                        while (scanner.hasNextLine()) {
                            String value = scanner.nextLine();
                            check = value;
                            if(check != null){
                                total++;
                                if(tableList[j].lookup(check)){
                                    similar++;
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    printStatistics(args[j],(int)(similar*100/total));
                }

            }

        }
    }

}