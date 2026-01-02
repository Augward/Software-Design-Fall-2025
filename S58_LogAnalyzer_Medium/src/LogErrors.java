import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Analyzer for ERROR level logs
 * <p>
 *     Counts how many times each user logged in.  It extends methods from the
 *  *     LogAnalyzer class and puts them into a simple map while helping towards
 *  *     the total processed for the type by writing to an outside file.
 * </p>
 * @author augward
 */
public class LogErrors extends LogAnalyzer {

    private final Path outFile;
    private final List<LogEntry> collected = new ArrayList<>();

    /**
     * Creates a LogError analyzer with a given buffer capacity
     *
     * @param capacity the queue capacity
     * @param outFile the file where it sends found errors
     */
    public LogErrors(int capacity, Path outFile) {
        super(capacity);
        this.outFile = outFile;
    }


    /**
     * Retrieves a map of unique data, but no data so null
     *
     * @return hashmap of summary, null since none
     */
    @Override
    public Map<String,Integer> getSummaryMap() {
        return null;
    }

    /**
     * Gets the name of the type of analyzer
     *
     * @return String ERROR
     */
    @Override
    public String getName() {
        return "ERROR";
    }


    /**
     * Processes a single ERROR entry and adds it to the file
     *
     * @param entry the entry added to the file to print
     */
    @Override
    public void processEntry(LogEntry entry) {
        collected.add(entry);
    }

    /**
     * Is called when stopped, basically writes all entries at the end in time order
     */
    @Override
    public void onStop() {

        try (BufferedWriter w = Files.newBufferedWriter(outFile, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (LogEntry entry : collected) {
                String time = entry.getTime();
                String line = entry.getLevel() + " " + time + " " + entry.getMessage();
                w.write(line);
                w.newLine();
            }
            w.flush();
        } catch (IOException ex) {
            System.err.println("Failed to write errors file");
        }
    }
}
