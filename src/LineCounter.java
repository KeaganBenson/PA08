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

        for (int i = 0; i < numArgs; i++) {
            File file = new File(args[i]);
            HashTable tbl = new HashTable();
            try(Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    tbl.insert(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("fuck");

            }
            tableList[i] = tbl;
        }

        // Find similarities across files
        System.out.println(tableList[0].toString());
        System.out.println(tableList[1].toString());
        System.out.println(tableList[2].toString());
        System.out.println(tableList[3].toString());

        for (int i = 0; i < numArgs; i++) {
            printFileName(args[i]);
            for(int j = 0; j<numArgs;j++) {
                int total = 0;
                int similar = 0;
                if (i != j) {
                    File file = new File(args[i]);
                    //System.out.println();
                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            System.out.println(scanner.nextLine());
                            System.out.println(tableList[j]);
                            if(tableList[j].lookup(scanner.nextLine())){
                                similar++;
                            }
                            total++;
                        }
                    }catch (FileNotFoundException e) {
                        System.out.println("fuck me");

                    }
                    printStatistics(args[j],total);
                }

            }

        }
    }

}