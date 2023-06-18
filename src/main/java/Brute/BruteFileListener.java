package Brute;

import Brute.Metrics.BruteMetricData;
import Brute.Metrics.BruteMetrics;
import com.fasterxml.jackson.databind.JsonNode;
import main.BruteUtilities;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class BruteFileListener {

    private Path directory;
    private File file;
    private BruteMetrics metrics;
    private Path path;
    private List<LogEntry> logEntries;

    public BruteFileListener(String directory, String file, BruteMetrics metrics) {
        this.directory = Paths.get(directory);
        this.file = new File(directory + file);
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
                        path = directory.resolve(fileName);
                        if (Files.isSameFile(path.toAbsolutePath(), this.file.toPath())) {
                            logEntries = parseWholeLog();
                            // readAllBytes can throw a OutOfMemoryError if the file is
                            // very large, due to the nature of this application you
                            // should be able to just wipe the file once and while to
                            // avoid hitting the limit.
                            String newContents = new String(Files.readAllBytes(path));
                            if (!newContents.equals(oldContents)) {
                                oldContents = newContents;
                                // Latest Entry
                                LogEntry latest = logEntries.get(logEntries.size()-1);
                                // GeographicMetrics
                                metrics.getMetrics().getGeographicMetrics().populate(latest.getHostname());
                                // TimeBasedMetrics
                                metrics.getMetrics().getTimeBasedMetrics().populate();
                                metrics.saveMetrics();
                                logEntries = parseWholeLog();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<LogEntry> parseWholeLog() {
        List<String> logContents = getContentsOfLog();
        List<LogEntry> logFileList = new ArrayList<>();

        if (!logContents.isEmpty()) {
            for (String entry : logContents) {
                LogEntry file = parseLogEntry(entry);
                assert file != null;
                logFileList.add(file);
            }
            return logFileList;
        }
        return logFileList;
    }

    private LogEntry parseLogEntry(String entry) {
        if ( entry.contains(" ") ) {
            String[] parts = entry.split(" ");
            return new LogEntry(parts[0], parts[1], parts[2], parts[3]);
        }
        return null;
    }

    private List<String> getContentsOfLog() {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static class LogEntry {
        private String username;
        private String password;
        private String hostname;
        private String protocol;

        public LogEntry(String username, String password, String hostname, String protocol) {
            this.username = username;
            this.password = password;
            this.hostname = hostname;
            this.protocol = protocol;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getProtocol() {
            return protocol;
        }

        public String getHostname() {
            return hostname;
        }
    }
}