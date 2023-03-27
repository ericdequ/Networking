package Eric_deQuevedo_59516698;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class udp_client_test {
    public static void main(String[] args) throws IOException {
        String serverIpAddress = args[0];
        String filePath = "cise_udp_test_results.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        ArrayList<Long> durations = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            long startTime = System.nanoTime();
            DatagramSocket socket = new DatagramSocket();

            String joke_input = "get_joke";
            byte[] sendBuffer = joke_input.getBytes();
            InetAddress serverAddress = InetAddress.getByName(serverIpAddress);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 6698);
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            durations.add(duration);
            System.out.println("Experiment " + (i + 1) + " - Total round-trip time (in ms): " + duration);

            String receivedJoke = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Joke: " + receivedJoke);

            // Write results to the file
            writer.write("Experiment " + (i + 1) + ":");
            writer.newLine();
            writer.write("Total round-trip time (in ms): " + duration);
            writer.newLine();
            writer.write("Joke: " + receivedJoke);
            writer.newLine();
            writer.write("----------------------------");
            writer.newLine();

            socket.close();
        }

        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (long duration : durations) {
            sum += duration;
            min = Math.min(min, duration);
            max = Math.max(max, duration);
        }

        double mean = sum / 20;
        double sumOfSquares = 0;

        for (long duration : durations) {
            sumOfSquares += Math.pow(duration - mean, 2);
        }

        double stddev = Math.sqrt(sumOfSquares / 20);

        writer.write("Summary:");
        writer.newLine();
        writer.write("Min: " + min + " ms");
        writer.newLine();
        writer.write("Mean: " + mean + " ms");
        writer.newLine();
        writer.write("Max: " + max + " ms");
        writer.newLine();
        writer.write("StdDev: " + stddev + " ms");
        writer.newLine();

        writer.close();
    }
}
