package Brute;

import Brute.Metrics.BruteMetricData;
import main.BruteUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class BruteFileListener {

    private Path directory;
    private File file;
    private Boolean log;
    private BruteMetricData metrics;

    public BruteFileListener(String directory, String file,  boolean log, BruteMetricData metrics) {
        this.directory = Paths.get(directory);
        this.file = new File(directory + file);
        this.log = log;
        this.metrics = metrics;
    }

    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();

        String oldContents;
        if (file.exists()) {
            BruteUtilities.print("Attached to " + file.getName() + ".");
            oldContents = new String(Files.readAllBytes(this.file.toPath()));
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
                            // readAllBytes can throw a OutOfMemoryError if the file is
                            // very large, due to the nature of this application you
                            // should be able to just wipe the file once and while to
                            // avoid hitting the limit.
                            String newContents = new String(Files.readAllBytes(child));
                            if (newContents.length() != 0 && oldContents.length() != 0) {
                                if (!newContents.equals(oldContents)) {
                                    // populate json here.
                                    // metrics.getTimeBasedMetrics().populate();
                                    metrics.getTimeBasedMetrics()
                                            .populate();
                                    // BruteUtilities.print("Detected an attempted BruteForce, updating BruteMetrics.");
                                    oldContents = newContents;
                                }
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