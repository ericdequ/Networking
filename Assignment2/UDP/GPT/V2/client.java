package V2;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        // Flag to control the loop
        boolean x = true;

        // Start the timer for the entire execution
        long startExecutionTime = System.nanoTime();

        while (x) {
            // Initialize the socket and PrintWriter to communicate with the server
            try (Socket s = new Socket("localhost", 6698);
                 PrintWriter pr = new PrintWriter(s.getOutputStream())) {

                // Create a Scanner object to read user input
                Scanner sc = new Scanner(System.in);
                System.out.println("Please Enter 'get_joke' to get a random joke or 'exit' to leave and shutdown the comedy server");

                // Read user input
                String joke_input = sc.nextLine();
                pr.println(joke_input);
                pr.flush();

                // Start the timer for the total round-trip time
                long startTime = System.nanoTime();

                // Create a BufferedReader to read the server's response
                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);

                // Read the server's response
                String str = bf.readLine();

                // End the timer for the total round-trip time
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                System.out.println("Total round-trip time (in ms): " + duration);

                // Print the received joke
                System.out.println("Joke is : " + str);

                // Exit the loop if the user input is 'exit'
                if (joke_input.equals("exit")) {
                    x = false;
                }
            }
        }

        // End the timer for the entire execution
        long endExecutionTime = System.nanoTime();
        long executionDuration = (endExecutionTime - startExecutionTime) / 1000000;
        System.out.println("Total execution time (in ms): " + executionDuration);
    }
}