package kuroyoru;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

import kuroyoru.Server;

import org.tinylog.Logger;

public class Listener implements Runnable {
    private ServerSocket __listener;

    public Listener(int port) throws IOException {
        this.__listener = new ServerSocket(port);

        Logger.info("Listening on port " + port);
    }


    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            try {
                Socket socket = this.__listener.accept();

                Logger.info("New client connected");
 
                new Server(socket).start();
            }
            catch (IOException err) {
                Logger.error("Unable to connect to client ({})", err);
            }
        }
    }
}