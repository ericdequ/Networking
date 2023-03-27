package thunder;

import java.io.*;
import java.net.*;
import java.util.*;

public class tcp_client_test {
    public static void main(String[] args) throws IOException {
        

        Random random = new Random();
        List<Long> roundTripTimes = new ArrayList<>();

        // Create a PrintWriter to output results to a file
        PrintWriter fileWriter = new PrintWriter("cise_tcp_test_results.txt", "UTF-8");
        for (int i = 0; i < 20; i++) {
            long startTime = System.nanoTime();

            Socket s = new Socket("localhost", 6698);
            PrintWriter pr = new PrintWriter(s.getOutputStream());

            int jokeNumber = random.nextInt(3) + 1;
            String joke_input = "Joke " + jokeNumber;

            pr.println(joke_input);
            pr.flush();

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str = bf.readLine();

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            roundTripTimes.add(duration);
            
            fileWriter.printf("Experiment %d:%n", i + 1);
            fileWriter.printf("Total round-trip time (in ms): %d%n", duration);
            fileWriter.printf("Joke: %s%n", str);
            fileWriter.println("----------------------------");

            s.close();
        }

        double min = Collections.min(roundTripTimes);
        double max = Collections.max(roundTripTimes);
        double mean = roundTripTimes.stream().mapToLong(val -> val).average().orElse(0.0);
        double stddev = Math.sqrt(roundTripTimes.stream().mapToDouble(val -> Math.pow(val - mean, 2)).average().orElse(0.0));

        fileWriter.println("Summary:");
        fileWriter.printf("Min: %.1f ms%n", min);
        fileWriter.printf("Mean: %.1f ms%n", mean);
        fileWriter.printf("Max: %.1f ms%n", max);
        fileWriter.printf("StdDev: %.1f ms%n", stddev);

        // Close the file writer
        fileWriter.close();
    }
}
