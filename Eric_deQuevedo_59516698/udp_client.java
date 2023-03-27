package Eric_deQuevedo_59516698;
import java.net.*;
import java.io.*;

public class udp_client {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java udp_client 128.227.205.239");
            System.exit(1);
        }

        String serverIpAddress = args[0];
        int serverPort = 6698;

        // Create a UDP socket for the client
        DatagramSocket socket = new DatagramSocket();

        // Send a "get_joke" request to the server
        String request = "get_joke";
        byte[] requestBuffer = request.getBytes();
        InetAddress serverAddress = InetAddress.getByName(serverIpAddress);
        DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length, serverAddress, serverPort);
        socket.send(requestPacket);

        // Receive the server's response
        byte[] responseBuffer = new byte[1024];
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
        socket.receive(responsePacket);

        // Print the received joke
        String joke = new String(responsePacket.getData(), 0, responsePacket.getLength());
        System.out.println("Received joke:\n" + joke);

        // Close the UDP socket
        socket.close();
    }
}
