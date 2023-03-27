package thunder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;

public class tcp_server {
    public static void main(String[] args) throws IOException {

        String projectRootPath = System.getProperty("user.dir");
        String Joke1File = projectRootPath + "/thunder/jokes/joke1.txt";
        String Joke2File = projectRootPath + "/thunder/jokes/joke2.txt";
        String Joke3File = projectRootPath + "/thunder/jokes/joke3.txt";
        String Error_File = projectRootPath + "/thunder/jokes/Error_Page.txt";
        String resultsFile = projectRootPath + "/thunder/results.txt";

        /*Creates a new Server Socket on Port 6698 */
        ServerSocket ss = new ServerSocket(6698);
        String str = "";
        boolean x = true;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resultsFile))) {
            while (x) {
                /* Waits until Client is connected when client connects it prints message */
                Socket s = ss.accept();
                System.out.println("Client Connected");

                /* Gets input stream from client and then prints to terminal  */
                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                str = bf.readLine();
                System.out.println("clients response is : " + str);

                PrintWriter pr = new PrintWriter(s.getOutputStream());
                OutputStream outputStream = s.getOutputStream();

                /* Declares string for Joke to be read into */
                String Joke = "";

                try {
                    /* Is the switch statement for taking in client input and deciding what joke to send */
                    if (str.equals("Joke 1")) {
                        Joke = Files.readString(Paths.get(Joke1File));
                    } else if (str.equals("Joke 2")) {
                        Joke = Files.readString(Paths.get(Joke2File));
                    } else if (str.equals("Joke 3")) {
                        Joke = Files.readString(Paths.get(Joke3File));
                    } else if (str.equals("exit")) {
                        Joke = "leaving Thanks for using java comedy server";
                        x = false;
                    } else {
                        Joke = Files.readString(Paths.get(Error_File));
                    }
                } catch (IOException e) {
                    Joke = "Error: Could not read the requested file. Please try again.";
                    System.out.println("Error reading the file: " + e.getMessage());
                }

                /*Adds Joke to pr and then sends it back to client */
                pr.println(Joke);
                pr.flush();

                /* Save the result to the results.txt file */
                bw.write("Client request: " + str + ", Server response: " + Joke);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing results to the file: " + e.getMessage());
        }

        /*Closes Server Socket to save memory*/
        ss.close();
    }
}
