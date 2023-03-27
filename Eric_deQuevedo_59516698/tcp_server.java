package Eric_deQuevedo_59516698;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class tcp_server {
    public static void main(String[] args) throws IOException {
        String workingDir = System.getProperty("user.dir");
        String[] jokeFiles = new String[]{
                workingDir + File.separator + "jokes" + File.separator + "joke1.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke2.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke3.txt"
        };
        String errorFile = workingDir + File.separator + "jokes" + File.separator + "Error_Page.txt";
        String resultsFile = workingDir + File.separator + "results.txt";

        ServerSocket serverSocket = new ServerSocket(6698);
        boolean running = true;

        int connectionCount = 0;
        List<Long> roundTripTimes = new ArrayList<>();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resultsFile))) {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                connectionCount++;
                System.out.println("Client Connected");

                long startTime = System.currentTimeMillis();

                InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                String clientRequest = bf.readLine();
                System.out.println("Client's request: " + clientRequest);

                PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());

                String response = "";

                try {
                    if (clientRequest.equals("Joke 1")) {
                        response = Files.readString(Paths.get(jokeFiles[0]));
                    } else if (clientRequest.equals("Joke 2")) {
                        response = Files.readString(Paths.get(jokeFiles[1]));
                    } else if (clientRequest.equals("Joke 3")) {
                        response = Files.readString(Paths.get(jokeFiles[2]));
                    } else if (clientRequest.equals("exit")) {
                        response = "Leaving. Thanks for using the Java comedy server.";
                        running = false;
                    } else {
                        response = Files.readString(Paths.get(errorFile));
                    }
                } catch (IOException e) {
                    response = "Error: Could not read the requested file. Please try again.";
                    System.out.println("Error reading the file: " + e.getMessage());
                }

                pr.println(response);
                pr.flush();

                long endTime = System.currentTimeMillis();
                long roundTripTime = endTime - startTime;
                roundTripTimes.add(roundTripTime);

                bw.write("Experiment " + connectionCount + ":\n");
                bw.write("Total round-trip time (in ms): " + roundTripTime + "\n");
                bw.write("Joke: " + response + "\n");
                bw.write("----------------------------\n");

                if (connectionCount >= 10) {
                    double min = roundTripTimes.stream().mapToLong(Long::longValue).min().getAsLong();
                    double max = roundTripTimes.stream().mapToLong(Long::longValue).max().getAsLong();
                    double mean = roundTripTimes.stream().mapToLong(Long::longValue).average().getAsDouble();
                    double median = roundTripTimes.stream().mapToLong(Long::longValue).sorted().skip(roundTripTimes.size() / 2).findFirst().getAsLong();
                    double sumSquaredDiffs = roundTripTimes.stream().mapToDouble(rt -> Math.pow(rt - mean, 2)).sum();
                    double stdDev = Math.sqrt(sumSquaredDiffs / roundTripTimes.size());

                    bw.write("Summary:\n");
                    bw.write("Min: " + min + " ms\n");
                    bw.write("Mean: " + mean + " ms\n");
                    bw.write("Max: " + max + " ms\n");
                    bw.write("StdDev: " + stdDev + " ms\n");
                    bw.write("Median: " + median + " ms\n");

                    System.out.println("Summary:");
                    System.out.println("Min: " + min + " ms");
                    System.out.println("Mean: " + mean + " ms");
                    System.out.println("Max: " + max + " ms");
                    System.out.println("StdDev: " + stdDev + " ms");
                    System.out.println("Median: " + median + " ms");

                    TimeUnit.MILLISECONDS.sleep(500); // Add this line before setting 'running' to false
                    running = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing results to the file: " + e.getMessage());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        serverSocket.close();
    }
}



               
