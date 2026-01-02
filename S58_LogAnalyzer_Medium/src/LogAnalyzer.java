import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Abstract base class for analyzers
 * <p>
 *     Each analyzer takes in its own types of items and updates several of its fields.
 *     It only increments it overall counter if the field matches the type of analyzer though.
 *     This helps keep the data as overarching as possible while still reaching many aspects.
 * </p>
 *
 * @author augward
 */
public abstract class LogAnalyzer implements Runnable {

    private final ArrayBlockingQueue<LogEntry> queue;
    private volatile int totalProcessed = 0;

    /**
     * Creates an analyzer with a given capacity
     *
     * @param capacity the capacity of the que, max inserts
     */
    public LogAnalyzer(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }


    /**
     * Getter for the number of processed entries
     *
     * @return the int for total processed
     */
    public int getTotalProcessed() {
        return totalProcessed;
    }


    /**
     * Puts a new entry into the analyzers input que
     *
     * @param entry the submittable entry
     * @throws InterruptedException if interrupted while waiting for space
     */
    public void submit(LogEntry entry) throws InterruptedException {
        queue.put(entry);
    }


    /**
     * Main consumer loop, continues until the end of the file/entries
     */
    @Override
    public void run() {
        try {
            while (true) {
                LogEntry entry = queue.take();
                if (entry.getLevel().equals("END")) break;

                processEntry(entry);
                if (entry.getLevel().equals(getName())) {
                    incrementProcessed();
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } finally {
            onStop();
        }
    }

    /**
     * Increase the total Processed count and is synchronized across threads
     */
    private synchronized void incrementProcessed() {
        totalProcessed++;
    }


    // Collection of abstract methods
    public abstract Map<String,Integer> getSummaryMap();
    public abstract String getName();
    public abstract void processEntry(LogEntry entry);
    public abstract void onStop();
}
