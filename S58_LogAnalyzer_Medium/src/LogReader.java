import java.util.List;

/**
 * Consumes a batch of LogEntry objects and routes each to the proper analyzer
 * <p>
 *     The class doesn't do any analysis, just dispatches logs to their proper analyzer.
 *     Entries are based on type, with Error, Warn, and Info being the main three.
 *     It varifies them and also helps add exceptional cases.
 * </p>
 *
 * @author augward
 */
public class LogReader implements Runnable {

    private final List<LogEntry> entries;
    private final LogInfo infoAnalyzer;
    private final LogWarn warnAnalyzer;
    private final LogErrors errorAnalyzer;

    /**
     * Constructs a LogReader that processes a batch of log entries
     *
     * @param entries the list of LogEntry objects to process
     * @param infoAnalyzer reference to INFO analyzer
     * @param warnAnalyzer reference to WARN analyzer
     * @param errorAnalyzer reference to ERROR analyzer
     */
    public LogReader(List<LogEntry> entries, LogInfo infoAnalyzer, LogWarn warnAnalyzer, LogErrors errorAnalyzer) {
        this.entries = entries;
        this.infoAnalyzer = infoAnalyzer;
        this.warnAnalyzer = warnAnalyzer;
        this.errorAnalyzer = errorAnalyzer;
    }

    /**
     * Iterates through every entry in a batch and sends it to the right Analyzer.
     * Sends it based off switch and checks it true to make sure no double counting.
     * The lower submissions are from conflicting classes, used to count things outside of level
     */
    @Override
    public void run() {
        try {
            for (LogEntry entry : entries) {
                if (entry == null) continue;


                String level = entry.getLevel();
                boolean sentToInfo = false;
                boolean sentToWarn = false;

                // chooses action by level
                switch (level) {
                    case "ERROR" -> errorAnalyzer.submit(entry);
                    case "INFO" -> {
                        infoAnalyzer.submit(entry);
                        sentToInfo = true;
                    }
                    case "WARN" -> {
                        warnAnalyzer.submit(entry);
                        sentToWarn = true;
                    }
                }

                // Makes sure users and unauthorized outside their normal are counted towards their totals
                String message = entry.getMessage();
                if (!sentToInfo && message.contains("logged in:")) {
                    infoAnalyzer.submit(entry);
                }

                if (!sentToWarn && message.contains("Unauthorized")) {
                    warnAnalyzer.submit(entry);
                }

            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
