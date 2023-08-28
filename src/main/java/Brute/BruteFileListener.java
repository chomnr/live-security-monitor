package Brute;

import Brute.Logger.BruteLogger;
import Brute.Logger.LogEntry;
import Brute.Metrics.BruteMetrics;
import Brute.Metrics.BruteMetricsMerger;
import Brute.WebSocket.BruteServer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

                            if (rawLogEntries.isEmpty()) {
                                BruteUtilities.print("Failed to parse log; attempting to reparse.");
                            }
                            // readAllBytes can throw a OutOfMemoryError if the file is
                            // very large, due to the nature of this application you
                            // should be able to just wipe the file once and while to
                            // avoid hitting the limit. Wipe the TRACKER_FILE
                            String newContents = new String(Files.readAllBytes(path));
                            if (!newContents.equals(oldContents) & !rawLogEntries.isEmpty()) {
                                oldContents = newContents;
                                // Latest Entry
                                LogEntry latest = rawLogEntries.get(rawLogEntries.size()-1);
                                if (latest != null) {
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
                                    // Saves to TRACKER_FILE (do not log if password is empty and do not send to websocket).
                                    if (!latest.getPassword().isEmpty() && !latest.getPassword().isBlank()) {
                                        Map<String, Integer> topAttacking = metrics.getMetrics().getGeographicMetrics()
                                                .getAttackOriginByCountry()
                                                .getTopAttackers(10);
                                        bs.broadcast(BruteMetricsMerger.mergeJson(latest, topAttacking));
                                        logger.addLog(latest);
                                        logger.saveLogs();
                                    }
                                } else {
                                    BruteUtilities.print("Detected an abnormality. Empty List?");
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
                logFileList.add(file);
            }
            return logFileList;
        }
        return logFileList;
    }

    private LogEntry parseLogEntry(String entry) {
        if ( entry.contains(" ") ) {
            String[] parts = entry.split(" ");
            if (parts.length >= 5) {
                return null;
            }
            if (parts.length < 4) {
                // Prevents an empty username & password from messing up the data.
                // Forgot if PAM handles this it probably does.
                entry = "  " + entry;
                parts = entry.split(" ");
                if (parts[1].isEmpty() || parts[1].isBlank()) { parts[1] = ""; }
            }
            return new LogEntry(BruteUtilities.unescape_perl_string(parts[0]),
                    BruteUtilities.unescape_perl_string(parts[1]), parts[2],
                    this.metrics.getMetrics().getProtocolBasedMetrics()
                            .getDistributionOfAttackProtocols()
                            .getProtocolByName(parts[3]).toString());
        }
        return null;
    }

    private List<String> getContentsOfLog() {
        try {
            return Files.readAllLines(path, Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Empty file?");
            List<String> dummy = new ArrayList<String>();
            dummy.add("dummy dummy 0.0.0.0 sshd");
            return dummy;
        }
    }
}