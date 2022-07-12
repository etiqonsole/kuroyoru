package kuroyoru;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;

public class Server extends Thread {
    private Socket __socket;
 
    public Server(Socket socket) {
        this.__socket = socket;
    }
 
    public void run() {
        try {
            InputStream input = this.__socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = this.__socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
 
            String text;
 
            do {
                text = reader.readLine();
                String reverseText = new StringBuilder(text).reverse().toString();
                writer.println("Server: " + reverseText);
 
            } while (!text.equals("bye"));
 
            this.__socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}