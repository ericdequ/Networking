package V2;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        // Initialize the socket and PrintWriter to communicate with the server
        Socket s = new Socket("localhost", 6698);
        PrintWriter pr = new PrintWriter(s.getOutputStream());

        // Create a Scanner object to read user input
        Scanner sc = new Scanner(System.in);

        // Create a BufferedReader to read the server's response
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        // Flag to control the loop
        boolean x = true;

        while (x) {
            // Start the timer for the total round-trip time
            long startTime = System.nanoTime();

            // Read user input
            String joke_input = read_user_input(sc);

            // Write the user input to the server
            write_to_server(joke_input, pr);

            // Read the server's response
            String str = read_from_server(bf);

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

        // Close the socket
        s.close();
    }

    public static String read_user_input(Scanner sc) {
        System.out.println("Please Enter 'get_joke' to get a random joke or 'exit' to leave and shutdown the comedy server");
        String joke_input = sc.nextLine();
        return joke_input;
    }

    public static void write_to_server(String joke_input, PrintWriter pr) {
        pr.println(joke_input);
        pr.flush();
    }

    public static String read_from_server(BufferedReader bf) throws IOException {
        String str = bf.readLine();
        return str;
    }
}
