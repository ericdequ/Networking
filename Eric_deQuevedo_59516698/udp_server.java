package Eric_deQuevedo_59516698;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.*;
import java.util.Random;

public class udp_server {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java udp_server 128.227.205.239");
            System.exit(1);
        }

        String serverIpAddress = args[0];

        String workingDir = System.getProperty("user.dir");
        String[] jokeFiles = new String[]{
                workingDir + File.separator + "jokes" + File.separator + "joke1.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke2.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke3.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke4.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke5.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke6.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke7.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke8.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke9.txt",
                workingDir + File.separator + "jokes" + File.separator + "joke10.txt"
        };
        String Error_File = workingDir + File.separator + "jokes" + File.separator + "Error_Page.txt";

        try {
            // Bind the server to the provided IP address and port 6698
            InetSocketAddress serverAddress = new InetSocketAddress(serverIpAddress, 6698);
            DatagramSocket socket = new DatagramSocket(serverAddress);

            // Initialize variables for statistics
            long[] localAccessTimes = new long[10];
            int counter = 0;

            while (counter < 10) {
                // Prepare a buffer to receive the client's request
                byte[] receiveBuffer = new byte[1024];

                // Receive the client's request into the buffer
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // Start timer for local joke meme access time
                long startTime = System.nanoTime();

                // Extract the client's request from the buffer
                String str = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Prepare a buffer to send the server's response
                byte[] sendBuffer;

                if (str.equals("get_joke")) {
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
                    localAccessTimes[counter] = duration;
                    counter++;
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
                
                            // Calculate and print statistics
                            double sum = 0;
                            double min = Double.MAX_VALUE;
                            double max = Double.MIN_VALUE;
                            for (long time : localAccessTimes) {
                                sum += time;
                                min = Math.min(min, time);
                                max = Math.max(max, time);
                            }
                            double mean = sum / 10;
                            double sumOfSquares = 0;
                            for (long time : localAccessTimes) {
                                sumOfSquares += Math.pow(time - mean, 2);
                            }
                            double stddev = Math.sqrt(sumOfSquares / 10);
                            System.out.println("Local Joke Meme Access Time (in ms):");
                            System.out.println("Min: " + min);
                            System.out.println("Mean: " + mean);
                            System.out.println("Max: " + max);
                            System.out.println("StdDev: " + stddev);
                
                            // Close the UDP socket
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
