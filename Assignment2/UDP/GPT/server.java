import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.Random;

public class server {
    public static void main(String[] args) throws IOException {
        // Array of file names containing the jokes
        String[] jokeFiles = new String[]{
                "Joke1.txt",
                "Joke2.txt",
                "Joke3.txt",
                "Joke4.txt",
                "Joke5.txt",
                "Joke6.txt",
                "Joke7.txt",
                "Joke8.txt",
                "Joke9.txt",
                "Joke10.txt"
        };
        String Error_File = "Error_Page.txt";

        // Create a UDP socket on port 6698
        DatagramSocket socket = new DatagramSocket(6698);
        boolean x = true;

        while (x) {
            // Prepare a buffer to receive the client's request
            byte[] receiveBuffer = new byte[1024];

            // Receive the client's request into the buffer
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            // Extract the client's request from the buffer
            String str = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("client's response is : " + str);

            // Prepare a buffer to send the server's response
            byte[] sendBuffer;

            if (str.equals("exit")) {
                sendBuffer = "leaving Thanks for using java comedy server".getBytes();
                x = false;
            } else if (str.equals("get_joke")) {
                // Generate a random number between 0 and 9 to select a joke file
                Random random = new Random();
                int jokeIndex = random.nextInt(10);

                // Read the selected joke file's content
                String joke = Files.readString(Paths.get(jokeFiles[jokeIndex]));

                // Convert the joke string to bytes to send it to the client
                sendBuffer = joke.getBytes();
            } else {
                // If the client's request is not recognized, send the error file content
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

        // Close the UDP socket
        socket.close();
    }
}
