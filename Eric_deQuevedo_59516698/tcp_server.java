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
        // Set up the working directory and file paths
        String workingDir = System.getProperty("user.dir");
        String imageFilePath = workingDir + File.separator + "images" + File.separator + "pic_joke1.jpg";
        String imageFilePath2 = workingDir + File.separator + "images" + File.separator + "pic_joke2.jpg";
        String imageFilePath3 = workingDir + File.separator + "images" + File.separator + "pic_joke3.jpg";
        String resultsFile = workingDir + File.separator + "results.txt";

        // Start the server
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

                // Check the client's request
                if (clientRequest.equals("Joke 1")) {
                try {
                    // Read the image file
                    byte[] imageData = Files.readAllBytes(Paths.get(imageFilePath));

                    // Send the image to the client
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                    dos.writeInt(imageData.length);
                    dos.write(imageData);
                    dos.flush();

                } catch (IOException e) {
                    System.out.println("Error reading or sending the image file: " + e.getMessage());
                }
            } else if (clientRequest.equals("exit")) {
                System.out.println("Leaving. Thanks for using the Java image server.");
                running = false;
            } else if (clientRequest.equals("Joke 2")) {
            
                try {
                    // Read the image file
                    byte[] imageData = Files.readAllBytes(Paths.get(imageFilePath2));

                    // Send the image to the client
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                    dos.writeInt(imageData.length);
                    dos.write(imageData);
                    dos.flush();

                } catch (IOException e) {
                    System.out.println("Error reading or sending the image file: " + e.getMessage());
                }
            
            } else if (clientRequest.equals("Joke 3")) {

                try {
                    // Read the image file
                    byte[] imageData = Files.readAllBytes(Paths.get(imageFilePath3));

                    // Send the image to the client
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                    dos.writeInt(imageData.length);
                    dos.write(imageData);
                    dos.flush();

                } catch (IOException e) {
                    System.out.println("Error reading or sending the image file: " + e.getMessage());
                }
            
            
            }
            
            
            else {
                PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
                pr.println("Invalid request. Please try again.");
                pr.flush();
            }

            long endTime = System.currentTimeMillis();
            long roundTripTime = endTime - startTime;
            roundTripTimes.add(roundTripTime);

            bw.write("Experiment " + connectionCount + ":\n");
            bw.write("Total round-trip time (in ms): " + roundTripTime + "\n");
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

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    
                    e.printStackTrace();
                } 
                running = false;
            }
        }
    } catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
    }
    }
}
