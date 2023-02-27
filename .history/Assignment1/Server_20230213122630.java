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

        PrintWriter pr = new PrintWriter(s.getOutputStream());

        String Joke = "";

        if(str.equals("1")){
            System.out.print("joke Option Chose = 1");
            Joke = "funny";
        } else if (str.equals("2")){
            System.out.print("joke Option Chose = 2");
            Joke = "Really funny";
        } else if (str.equals("3")){
            System.out.print("joke Option Chose = 3");
            Joke = "Really Really funny";
        } else{
            System.out.print("joke Option Chose = Other");
            Joke = "Invalid input and thats not funny";
        }

        pr.println(joke_input);
        pr.flush();



        ss.close();
    }

}