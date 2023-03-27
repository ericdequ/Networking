package P2;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class client {
    public static void main(String[] args) throws IOException {
        int numTrials = 10;
        List<Long> roundTripTimes = new ArrayList<>();
        List<Long> tcpSetupTimes = new ArrayList<>();

        for (int i = 0; i < numTrials; i++) {
            // Start the timer for TCP setup time including IP address resolution
            long tcpStartTime = System.nanoTime();

            // Initialize the socket and PrintWriter to communicate with the server
            Socket s = new Socket("localhost", 6698);
            PrintWriter pr = new PrintWriter(s.getOutputStream());

            // End the timer for TCP setup time including IP address resolution
            long tcpEndTime = System.nanoTime();
            long tcpDuration = (tcpEndTime - tcpStartTime) / 1000000;
            tcpSetupTimes.add(tcpDuration);

            // Send the request to get a random joke
            pr.println("get_joke");
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
            roundTripTimes.add(duration);

            // Print the received joke
            System.out.println("Joke " + (i + 1) + " is: " + str);

            // Close the socket
            s.close();
        }

        // Calculate and print the statistics for total round-trip time
        long min = roundTripTimes.stream().mapToLong(Long::longValue).min().getAsLong();
        long max = roundTripTimes.stream().mapToLong(Long::longValue).max().getAsLong();
        double mean = roundTripTimes.stream().mapToLong(Long::longValue).average().getAsDouble();
        double sumOfSquares = 0;

        for (long time : roundTripTimes) {
            sumOfSquares += Math.pow(time - mean, 2);
        }
        double stddev = Math.sqrt(sumOfSquares / numTrials);

        System.out.println("Total Round-Trip Time (in ms):");
        System.out.println("Min: " + min);
        System.out.println("Mean: " + mean);
        System.out.println("Max: " + max);
        System.out.println("StdDev: " + stddev);

        // Calculate and print the statistics for TCP setup time including IP address resolution
        min = tcpSetupTimes.stream().mapToLong(Long::longValue).min().getAsLong();
        max = tcpSetupTimes.stream().mapToLong(Long::longValue).max().getAsLong();
        mean = tcpSetupTimes.stream().mapToLong(Long::longValue).average().getAsDouble();
        sumOfSquares = 0;

        for (long time : tcpSetupTimes) {
            sumOfSquares += Math.pow(time - mean, 2);
        }
        stddev = Math.sqrt(sumOfSquares / numTrials);

        System.out.println("TCP Setup Time including IP Address Resolution (in ms):");
        System.out.println("Min: " + min);
        System.out.println("Mean: " + mean);
        System.out.println("Max: " + max);
        System.out.println("StdDev: " + stddev);
    }
}