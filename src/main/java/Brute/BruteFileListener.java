package Brute;

import Brute.Metrics.BruteMetricData;
import main.BruteUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class BruteFileListener {

    private Path directory;
    private File file;
    private Boolean log;
    private BruteMetricData metrics;
    private Path path;

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
                    if (path == null) { path = fileName; }
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
                                    metrics.getTimeBasedMetrics()
                                            .populate();
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

    private List<LogFile> parseWholeLog() {
        List<String> logContents = getContentsOfLog();
        List<LogFile> logFileList = new ArrayList<>();

        if (!logContents.isEmpty()) {
            for (String entry : logContents) {
                LogFile file = parseLogEntry(entry);
                assert file != null;
                logFileList.add(file);
            }
            return logFileList;
        }
        return logFileList;
    }

    private LogFile parseLogEntry(String entry) {
        if ( entry.contains(" ") ) {
            String[] parts = entry.split(" ");
            return new LogFile(parts[0], parts[1], parts[2], parts[3]);
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

    public void parseSingleFile(String s) {

    }

    public static class LogFile {
        private String username;
        private String password;
        private String protocol;
        private String hostname;

        public LogFile(String username, String password, String protocol, String hostname) {
            this.username = username;
            this.password = password;
            this.protocol = protocol;
            this.hostname = hostname;
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