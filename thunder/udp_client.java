package thunder;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class udp_client {
    public static void main(String[] args) throws IOException {
        boolean x = true;
        while (x) {
            long startTime = System.nanoTime();
            DatagramSocket socket = new DatagramSocket();
            Scanner sc = new Scanner(System.in);
            System.out.println("Type 'get_joke' to receive a joke or 'exit' to leave and shutdown the comedy server");
            String joke_input = sc.nextLine();

            byte[] sendBuffer = joke_input.getBytes();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 6698);
            socket.send(sendPacket);

            if (joke_input.equals("exit")) {
                x = false;
                socket.close();
                continue;
            }

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("Total round-trip time (in ms): " + duration);

            String receivedJoke = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Joke is : " + receivedJoke);
        }
    }
}
