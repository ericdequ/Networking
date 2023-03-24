import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {

        boolean x = true;
        while (x) {
            Socket s = new Socket("localhost", 6698);
            PrintWriter pr = new PrintWriter(s.getOutputStream());

            Scanner sc = new Scanner(System.in);
            System.out.println("Please Enter 'get_joke' to get a random joke or 'exit' to leave and shutdown the comedy server");

            String joke_input = sc.nextLine();
            pr.println(joke_input);
            pr.flush();

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str = bf.readLine();
            System.out.println("Joke is : " + str);

            s.close();
            if (joke_input.equals("exit")) {
                x = false;
            }
        }
    }
}
