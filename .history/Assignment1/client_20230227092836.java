import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client{
    public static void main(String[] args) throws IOException{

        /* Client creats a new socket at localhost + port 4999 */
        Socket s = new Socket("localhost", 6698);
        PrintWriter pr = new PrintWriter(s.getOutputStream());

        /* Gets user input from the user  */
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter a Number 1-3");

        /*Handles user input and sends it to the Server */
        String joke_input = sc.nextLine();
        pr.println(joke_input);
        pr.flush();

        /* Creates Stream reader to handle response from the server */
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        /*Gets Response from the server and prints it to Terminal */
        String str = bf.readLine();
        System.out.println("Joke is : "+ str);

        s.close();
    }
}
