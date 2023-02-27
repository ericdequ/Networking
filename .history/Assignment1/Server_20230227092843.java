import java.net.*;
import java.io.*;
import java.nio.file.*;

public class server{
    public static void main(String[] args) throws IOException{


        String Joke1File = "Joke1.txt";
        String Joke2File = "Joke2.txt";
        String Joke3File = "Joke3.txt";
        String Error_File = "Error_Page.txt";

        /*Creats a new Server Socket on Port 4999 */
        ServerSocket ss = new ServerSocket();
        String str = "";
        boolean x = true;

        while (x){
            /* Waits until Client is connected when client connects it prints message */
            Socket s = ss.accept();
            System.out.println("Client Connected");

            /* Gets input stream from client and then prints to terminal  */
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            str = bf.readLine();
            System.out.println("clients response is : "+ str);

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            OutputStream outputStream = s.getOutputStream();

            /* Declares string for Joke to be read into */
            String Joke = "";


            /* Is the switch statement for taking in client input and deciding what joke to send */
            if(str.equals("Joke 1")){
                Joke = Files.readString(Paths.get(Joke1File));
            } else if (str.equals("JOke 2")){
                Joke = Files.readString(Paths.get(Joke2File));
            } else if (str.equals("Joke 3")){
                Joke = Files.readString(Paths.get(Joke3File));
            } else if(str.equals("exit")){
                Joke = "leaving Thanks for using java comedy server";
                x = false;
            } else{ 
                Joke = Files.readString(Paths.get(Error_File));
            }

            /*Adds Joke to pr and then sends it back to client */
            pr.println(Joke);
            pr.flush();

        }

        
        /*Closes Server Socket to save memory*/
        ss.close();
    }

}