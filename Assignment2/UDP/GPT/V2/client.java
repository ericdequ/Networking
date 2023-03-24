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

        // Initialize the DatagramSocket to communicate with the server
        try (DatagramSocket socket = new DatagramSocket()) {

            while (x) {
                // Create a Scanner object to read user input
                Scanner sc = new Scanner(System.in);
                System.out.println("Please Enter 'get_joke' to get a random joke, 'compliment' for a random compliment, 'advice' for a random piece of advice, or 'exit' to leave and shutdown the comedy server");

                // Read user input
                String joke_input = sc.nextLine();

                // Send the user input to the server
                byte[] sendData = joke_input.getBytes();
                InetAddress serverAddress = InetAddress.getByName("localhost");
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 6698);
                socket.send(sendPacket);

                // Start the timer for the total round-trip time
                long startTime = System.nanoTime();

                // Prepare a buffer to receive the server's response
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Receive the server's response
                socket.receive(receivePacket);

                // End the timer for the total round-trip time
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                System.out.println("Total round-trip time (in ms): " + duration);

                // Extract the server's response from the buffer
                String str = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Print the received response
                System.out.println("Server response: " + str);

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

    // Generate a random compliment
    private static String getRandomCompliment() {
        String[] compliments = {
                "You are an amazing person!",
                "You are so talented!",
                "You have a great sense of humor!",
                "You're a fantastic friend!",
                "You light up the room!"
        };

        int randomIndex = (int) (Math.random() * compliments.length);
        return compliments[randomIndex];
    }

    // Generate a random piece of advice
    private static String getRandomAdvice() {
        String[] advices = {
                "Believe in yourself!",
                "Never give up!",
                "Learn from your mistakes!",
                "Stay positive and focused!",
                "Be kind and patient with yourself!"
        };

        int randomIndex = (int) (Math.random() * advices.length);
        return advices[randomIndex];
    }
}
