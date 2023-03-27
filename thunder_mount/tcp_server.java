package thunder_mount;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;

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

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resultsFile))) {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected");

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

                bw.write("Client request: " + clientRequest + ", Server response: " + response);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing results to the file: " + e.getMessage());
        }

        serverSocket.close();
    }
}
