import java.net.*;
import java.io.*;

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


        /* Declares string for Joke to be read into */
        String Joke = "";


        /* Is the switch statement for taking in client input and deciding what joke to send */
        if(str.equals("1")){
            //System.out.print("joke Option Chose = 1");
            Joke = "funny";
        } else if (str.equals("2")){
            //System.out.print("joke Option Chose = 2");
            Joke = "Really funny";
        } else if (str.equals("3")){
            //System.out.print("joke Option Chose = 3");
            Joke = "Really Really funny";
        } else{
            //System.out.print("joke Option Chose = Other");
            Joke = "Invalid input and thats not funny";
        }

        /*Adds Joke to pr and then sends it back to client */
        pr.println(Joke);
        pr.flush();


        /*Closes Server Socket to save memory*/
        ss.close();
    }

}