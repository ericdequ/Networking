package V2;

import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.Scanner;

public class server {
    public static void main(String[] args) throws IOException {
        // Array of file names containing the jokes
        String[] jokeFiles = new String[]{
                "../jokes/joke1.txt",
                "../jokes/joke2.txt",
                "../jokes/joke3.txt",
                "../jokes/joke4.txt",
                "../jokes/joke5.txt",
                "../jokes/joke6.txt",
                "../jokes/joke7.txt",
                "../jokes/joke8.txt",
                "../jokes/joke9.txt",
                "../jokes/joke10.txt"
        };
        String Error_File = "Error_Page.txt";

        try (DatagramSocket socket = new DatagramSocket(6698)) {
            boolean x = true;

            while (x) {
                // Prepare a buffer to receive the client's request
                byte[] receiveBuffer = new byte[1024];

                long startTime = System.nanoTime();


                // Receive the client's request into the buffer
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // Ask the user to approve or deny the client connection
                System.out.println("A client is trying to connect. Do you want to approve (y) or deny (n) the connection?");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                boolean approved = input.equalsIgnoreCase("y");

                // Extract the client's request from the buffer
                String str = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client's response is : " + str);

                // Prepare a buffer to send the server's response
                byte[] sendBuffer;

                if (approved && str.equals("get_joke")) {
                    // Generate a random number between 0 and 9 to select a joke file
                    Random random = new Random();
                    int jokeIndex = random.nextInt(10);

                    // Read the selected joke file's content
                    String joke = Files.readString(Paths.get(jokeFiles[jokeIndex]));

                    // Convert the joke string to bytes to send it to the client
                    sendBuffer = joke.getBytes();

                    // End the timer for the joke meme access time locally
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000;
                    System.out.println("Joke meme access time (in ms): " + duration);
                } else {
                    // If the client's request is not recognized or denied, send the error file content
                    String joke = Files.readString(Paths.get(Error_File));
                    sendBuffer = joke.getBytes();
                }

                // Get the client's address and port from the received packet
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Create a packet to send the server's response to the client
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);

                // Send the packet to the client
                socket.send(sendPacket);
            }
        }
    }
}
