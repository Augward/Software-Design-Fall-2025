import java.util.*;

/**
 * Analyzer for INFO level logs
 * <p>
 *     Counts how many times each user logged in.  It extends methods from the
 *     LogAnalyzer class and puts them into a simple map while helping towards
 *     the total processed for the type
 * </p>
 * @author augward
 */
public class LogInfo extends LogAnalyzer {

    private final Map<String,Integer> loginCounts = new HashMap<>();

    /**
     * Creates a LogInfo analyzer with a given buffer capacity
     *
     * @param capacity the queue capacity
     */
    public LogInfo(int capacity) {
        super(capacity);
    }


    /**
     * Retrieves a map of unique data
     *
     * @return hashmap of summary
     */
    @Override
    public Map<String,Integer> getSummaryMap() {
        return Map.copyOf(loginCounts);
    }

    /**
     * Gets the name of the type of analyzer
     *
     * @return String INFO
     */
    @Override
    public String getName() {
        return "INFO";
    }


    /**
     * Processes a single INFO entry and increments a type of user via visits
     *
     * @param entry the entry to be tested if it's logged in
     */
    @Override
    public void processEntry(LogEntry entry) {
        String user = entry.getUsername();
        String message = entry.getMessage();

        if (message.contains("in: user")) {
            loginCounts.merge(user, 1, Integer::sum);
        }
    }

    /**
     * Is called when stopped, nothing need in this level
     */
    @Override
    public void onStop() {}
}
