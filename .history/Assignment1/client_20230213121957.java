import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client{
    public static void main(String[] args) throws IOException{

        Socket s = new Socket("localhost", 4999);
        PrintWriter pr = new PrintWriter(s.getOutputStream());

        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter a Number 1-3");

        String joke_input = sc.nextLine();

        pr.println(joke_input);
        pr.flush();
    }
}
