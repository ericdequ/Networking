package Eric_deQuevedo_59516698;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class tcp_client {
    public static void main(String[] args) {
        boolean x = true;
        while (x) {
            try {
                // Start the timer for the total round-trip time
                long startTime = System.nanoTime();

                // Initialize the socket and PrintWriter to communicate with the server
                Socket s = new Socket("128.227.205.239", 6698);
                PrintWriter pr = new PrintWriter(s.getOutputStream());

                // Create a Scanner object to read user input
                Scanner sc = new Scanner(System.in);
                System.out.println("Please Enter a Number 'Joke 1', 'Joke 2', or 'Joke 3' type 'exit' to leave and shutdown comedy server");

                // Read user input
                String joke_input = sc.nextLine();
                pr.println(joke_input);
                pr.flush();

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

                // Close the socket
                s.close();

                // Exit the loop if the user input is 'exit'
                if (joke_input.equalsIgnoreCase("exit")) {
                    x = false;
                }
            } catch (IOException e) {
                System.err.println("Error communicating with the server: " + e.getMessage());
                x = false;
            }
        }
    }
}
