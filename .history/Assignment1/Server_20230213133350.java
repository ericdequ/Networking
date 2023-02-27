import java.net.*;
import java.io.*;
import java.nio.file.*;

public class server{
    public static void main(String[] args) throws IOException{

        /*Creats a new Server Socket on Port 4999 */
        ServerSocket ss = new ServerSocket(4999);

        /* Waits until Client is connected when client connects it prints message */
        Socket s = ss.accept();
        System.out.println("Client Connected");

        /* Gets input stream from client and then prints to terminal  */
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String str = bf.readLine();
        System.out.println("clients response is : "+ str);

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        OutputStream outputStream = s.getOutputStream();

        /* Declares string for Joke to be read into */
        String Joke = Files.readString(Paths.get("Joke1.txt"));


        /* Is the switch statement for taking in client input and deciding what joke to send */
        if(str.equals("1")){
            Joke = Files.readString(Paths.get("Joke1.txt"));
        } else if (str.equals("2")){
            Joke = Files.readString(Paths.get("Joke2.txt"));
        } else if (str.equals("3")){
            Joke = Files.readString(Paths.get("Joke3.txt"));
        } else{
            Joke = Files.readString(Paths.get("Error_Page.txt"));
        }

        /*Adds Joke to pr and then sends it back to client */
        pr.println(Joke);
        pr.flush();

        /*Closes Server Socket to save memory*/
        ss.close();
    }

}