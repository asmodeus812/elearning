package com.examples.core;

import java.util.Scanner;

public class LitteralsAndTypes {

    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);
        try (Scanner scan = consoleScanner) {
            scan.nextInt();
            System.out.println("You typed the integer value: " + consoleScanner.nextInt());
        } catch (Exception e) {
            // catch all other exceptions here ...
            System.out.println("Error: Encountered an exception and could not read an integer from the console... ");
            System.out.println("Exiting the program - restart and try the program again!");
        }

    }
}
