package Assignment1;

import java.net.*;
import java.io.*;

public class Server{
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(4999);
        Socket s = ss.accept();
        System.out.println("Client Connected");
    }

}