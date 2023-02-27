import java.net.*;
import java.io.*;

public class server{
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(4999);
        Socket s = ss.accept();
        System.out.println("Client Connected");

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("clients response is : "+ str);

        if(joke_input.equals("1")){
            System.out.print("joke Option Chose = 1");
        } else if (joke_input.equals("2")){
            System.out.print("joke Option Chose = 2");
        } else if (joke_input.equals("3")){
            System.out.print("joke Option Chose = 3");
        } else{
            System.out.print("joke Option Chose = Other");
        }

        ss.close();
    }

}