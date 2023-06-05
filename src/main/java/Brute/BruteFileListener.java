package Brute;

import Brute.WebSocket.BruteServer;
import main.BruteUtilities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class BruteFileListener {

    private Path directory;
    private File file;
    private Boolean log;

    public BruteFileListener(String directory, String file,  boolean log) {
        this.directory = Paths.get(directory);
        this.file = new File(directory + file);
        this.log = log;
    }

    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();

        if (file.exists()) {
            BruteUtilities.print("Attached to " + file.getName() + ".");
        } else {
            BruteUtilities.print("Unable to locate " + file.getName() + ".");
            return;
        }

        for(;;) {
            WatchKey key;
            try {
                key = directory.register(watcher, ENTRY_MODIFY, ENTRY_CREATE);
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == OVERFLOW) {
                        continue;
                    }

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    if (kind == ENTRY_MODIFY) {
                        Path child = directory.resolve(fileName);
                        if (Files.isSameFile(child.toAbsolutePath(), this.file.toPath())) {
                            if (log) {
                                BruteUtilities.print("Sending attempt to websocket.");
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
