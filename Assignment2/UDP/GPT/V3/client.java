package V3;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        boolean x = true;

        long startExecutionTime = System.nanoTime();

        try (DatagramSocket socket = new DatagramSocket()) {

            while (x) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please Enter 'get_joke' to get a random joke, 'get_fact' for a random science fact, 'get_history' for a random historical event or 'exit' to leave and shutdown the server");

                String input = sc.nextLine();

                byte[] sendData = input.getBytes();
                InetAddress serverAddress = InetAddress.getByName("localhost");
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 6698);
                socket.send(sendPacket);

                long startTime = System.nanoTime();

                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                socket.receive(receivePacket);

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                System.out.println("Total round-trip time (in ms): " + duration);

                String str = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println("Server response: " + str);

                if (input.equals("exit")) {
                    x = false;
                }
            }
        }

        long endExecutionTime = System.nanoTime();
        long executionDuration = (endExecutionTime - startExecutionTime) / 1000000;
        System.out.println("Total execution time (in ms): " + executionDuration);
    }
}
