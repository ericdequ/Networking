import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client{
    public static void main(String[] args) throws IOException{

        Socket s = new Socket("localhost", 4999);
        PrintWriter pr = new PrintWriter(s.getOutputStream());

        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter a Number 1-3");

        String joke_input = sc.nextLine();

        if(joke_input.equals("1")){
            System.out.print("joke Option Chose = 1");

        } else if (joke_input.equals("2")){
            System.out.print("joke Option Chose = 2");

        } else if (joke_input.equals("3")){
            System.out.print("joke Option Chose = 3");

        } else{
            System.out.print("joke Option Chose = Other");

        }

        pr.println(joke_input);
        pr.flush();
    }
}
