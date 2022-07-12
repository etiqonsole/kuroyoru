package kuroyoru;

import java.io.IOException;

import kuroyoru.utils.StartupFlags;

import org.tinylog.Logger;
import org.tinylog.configuration.Configuration;

public class App {
    public static void main(String[] args) {
        StartupFlags flags = StartupFlags.parse(args);

        if (flags.contains(StartupFlags.DEVEL)) {
            Configuration.set("writer", "console");
            Configuration.set("writer.level", "debug");
            Logger.info("[development mode enabled]");
        }

        try {
            Listener listener = new Listener(1111);
            listener.start();
        }
        catch (IOException err) {
            Logger.error(err);
            System.exit(1);
        }
    }
}
