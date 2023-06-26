package Brute;

import Brute.Logger.BruteLogger;
import Brute.Logger.LogEntry;
import Brute.Metrics.BruteMetrics;
import Brute.WebSocket.BruteServer;
import com.google.gson.Gson;

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
    private BruteLogger logger;
    private List<LogEntry> rawLogEntries;

    public BruteFileListener(String directory, String file, BruteMetrics metrics, BruteLogger logger) {
        this.directory = Paths.get(directory);
        this.file = new File(directory + file);
        this.metrics = metrics;
        this.logger = logger;
    }


    // TODO: REFRACTOR THIS ENTIRE THING.
    @SuppressWarnings("unchecked")
    public void listen(BruteServer bs) throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Gson gson = new Gson();

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
                            rawLogEntries = parseWholeLog();
                            // readAllBytes can throw a OutOfMemoryError if the file is
                            // very large, due to the nature of this application you
                            // should be able to just wipe the file once and while to
                            // avoid hitting the limit. Wipe the TRACKER_FILE
                            String newContents = new String(Files.readAllBytes(path));
                            if (!newContents.equals(oldContents)) {
                                oldContents = newContents;
                                // Latest Entry
                                LogEntry latest = rawLogEntries.get(rawLogEntries.size()-1);

                                // We do not want empty fields but still log the rest of the data.
                                if (!latest.getUsername().isBlank() &&
                                        !latest.getPassword().isBlank() &&
                                        !latest.getHostname().isBlank() &&
                                        !latest.getProtocol().isBlank()) {
                                    if (!latest.getUsername().isEmpty() ||
                                            !latest.getPassword().isEmpty() ||
                                            !latest.getHostname().isEmpty() ||
                                            !latest.getProtocol().isEmpty()) {
                                        //CredentialBasedMetrics
                                        metrics.getMetrics().getCredentialBasedMetrics().populate(latest.getUsername(), latest.getPassword());
                                    }
                                }

                                // GeographicMetrics
                                metrics.getMetrics().getGeographicMetrics().populate(latest.getHostname());
                                latest.setCountry(metrics.getMetrics().getGeographicMetrics().getAttackOriginByCountry()
                                        .getCountryByIp(latest.getHostname()));

                                // TimeBasedMetrics
                                metrics.getMetrics().getTimeBasedMetrics().populate();

                                // ProtocolBasedMetrics
                                metrics.getMetrics().getProtocolBasedMetrics().populate(latest.getProtocol());

                                metrics.saveMetrics();
                                rawLogEntries = parseWholeLog();
                                // Saves to TRACKER_FILE (do not log if password is empty). or send to websocket.
                                if (!latest.getPassword().isEmpty() && !latest.getPassword().isBlank()) {
                                    bs.broadcast(gson.toJson(latest));
                                    logger.addLog(latest);
                                    logger.saveLogs();
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
            if (parts.length < 4) {
                // Prevents an empty username & password from messing up the data.
                // Forgot if PAM handles this it probably does.
                entry = "  " + entry;
                parts = entry.split(" ");
                if (parts[1].isEmpty() || parts[1].isBlank()) { parts[1] = ""; }
            }
            return new LogEntry(parts[0], parts[1], parts[2],
                    this.metrics.getMetrics().getProtocolBasedMetrics()
                            .getDistributionOfAttackProtocols()
                            .getProtocolByName(parts[3]).toString());
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
}