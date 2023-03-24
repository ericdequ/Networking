import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {

        boolean x = true;
        while (x) {
            // Start the timer for the total round-trip time
            long startTime = System.nanoTime();

            // Initialize the socket and PrintWriter to communicate with the server
            Socket s = new Socket("localhost", 6698);
            PrintWriter pr = new PrintWriter(s.getOutputStream());

            // Create a Scanner object to read user input
            Scanner sc = new Scanner(System.in);
            System.out.println("Please Enter a Number 'Joke 1', 'Joke 2', or 'joke 3' type 'exit' to leave and shutdown comedy server");

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
            if (joke_input.equals("exit")) {
                x = false;
            }
        }
    }
}
