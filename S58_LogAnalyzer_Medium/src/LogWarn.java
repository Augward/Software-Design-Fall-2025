import java.util.*;

/**
 * Analyzer for WARN level logs
 * <p>
 *     Counts how many times each user logged in.  It extends methods from the
 *     LogAnalyzer class and puts them into a simple map while helping towards
 *     the total processed for the type
 * </p>
 * @author augward
 */
public class LogWarn extends LogAnalyzer {

    private final Map<String,Integer> summary = new HashMap<>();

    /**
     * Creates a LogWarn analyzer with a given buffer capacity
     *
     * @param capacity the queue capacity
     */
    public LogWarn(int capacity) {
        super(capacity);
    }


    /**
     * Retrieves a map of unique data
     *
     * @return hashmap of summary
     */
    @Override
    public Map<String,Integer> getSummaryMap() {
        return Map.copyOf(summary);
    }

    /**
     * Gets the name of the type of analyzer
     *
     * @return String WARN
     */
    @Override
    public String getName() {
        return "WARN";
    }


    /**
     * Processes a single WARN entry and increments a type of unauthorized user
     *
     * @param entry the entry to be tested if it's illegally logged in
     */
    @Override
    public void processEntry(LogEntry entry) {
        String message = entry.getMessage();
        if (message.contains("Unauthorized")) {
            summary.merge("Unauthorized logins", 1, Integer::sum);
        }
    }

    /**
     * Is called when stopped, nothing need in this level
     */
    @Override
    public void onStop() {}
}
