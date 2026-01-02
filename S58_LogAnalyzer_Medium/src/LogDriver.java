import java.io.BufferedReader;
import java.io.IOException;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Driver class that runs the main executors.
 * <p>
 *     Initializes the Executors and thread pools for multitasking.
 *     Also inputs the log file that contains the records needed to observe.
 *     Also controls the termination/shutdowns of the multithreading for
 *     the final printing out of the desired summaries.
 * </p>
 *
 * @author augward
 */
public class LogDriver {

    // private static final String DEFAULT_INPUT1 = "oral_exam2/S58_LogAnalyzer_Medium/resources/sample_log_file.log";
    private static final String DEFAULT_INPUT2 = "oral_exam2/S58_LogAnalyzer_Medium/resources/large_log_file.log";

    public static void main(String[] args) throws InterruptedException {
        Path inputPath = Paths.get(DEFAULT_INPUT2);

        if (!Files.isReadable(inputPath)) {
            System.err.println("Cannot read input file: " + inputPath.toAbsolutePath());
            return;
        }


        // Queue size and batch size
        final int BUFFER_CAPACITY = 10;
        final int BATCH_SIZE = 100;


        // create analyzer instances
        LogInfo info = new LogInfo(BUFFER_CAPACITY);
        LogWarn warn = new LogWarn(BUFFER_CAPACITY);

        Path errorOut = Paths.get("oral_exam2/S58_LogAnalyzer_Medium/resources/errors_only.log");
        LogErrors errors = new LogErrors(BUFFER_CAPACITY, errorOut);


        // start each analyzer
        ExecutorService infoExec = Executors.newCachedThreadPool();
        ExecutorService warnExec = Executors.newCachedThreadPool();
        ExecutorService errorExec = Executors.newCachedThreadPool();

        infoExec.execute(info);
        warnExec.execute(warn);
        errorExec.execute(errors);



        // pool for LogReader tasks
        ExecutorService readersPool = Executors.newCachedThreadPool();


        // read file, create LogEntry objects, batch into 100-line groups
        List<LogEntry> batch = new ArrayList<>(BATCH_SIZE);
        try (BufferedReader br = Files.newBufferedReader(inputPath)) {
            String raw;
            while ((raw = br.readLine()) != null) {

                LogEntry e = new LogEntry(raw);
                batch.add(e);

                if (batch.size() >= BATCH_SIZE) {

                    readersPool.execute(new LogReader(new ArrayList<>(batch), info, warn, errors));
                    batch.clear();
                }
            }

            // rest of the lines less than 100
            if (!batch.isEmpty()) {
                readersPool.execute(new LogReader(new ArrayList<>(batch), info, warn, errors));
                batch.clear();
            }

        } catch (IOException ex) {
            System.err.println("Error reading input file");
            readersPool.shutdownNow();
            infoExec.shutdownNow();
            warnExec.shutdownNow();
            errorExec.shutdownNow();
            return;
        }



        // shutdown reader pools
        readersPool.shutdown();

        if(!readersPool.awaitTermination(3, TimeUnit.MINUTES)) System.err.println("Reader didn't finish");


        // END Sentinels to stop analyzers
        info.submit(LogEntry.END);
        warn.submit(LogEntry.END);
        errors.submit(LogEntry.END);



        // shutdown analyzer executors and wait
        infoExec.shutdown();
        warnExec.shutdown();
        errorExec.shutdown();

        if(!infoExec.awaitTermination(1, TimeUnit.MINUTES)) System.err.println("INFO didn't finish");
        if(!warnExec.awaitTermination(1, TimeUnit.MINUTES)) System.err.println("WARN didn't finish");
        if(!errorExec.awaitTermination(1, TimeUnit.MINUTES)) System.err.println("ERROR didn't finish");


        // print summaries
        System.out.println("\n-- Analysis Complete --");
        printSummary(errors);
        printSummary(info);
        printSummary(warn);

        System.out.println("\nErrors written to: " + errorOut.toAbsolutePath());
    }

    /**
     * Prints the summary of the desired LogAnalyzer
     *
     * @param analyzer the type of LogAnalyzer that is being printed
     */
    private static void printSummary(LogAnalyzer analyzer) {
        System.out.println("\n[" + analyzer.getName() + "] total processed: " + analyzer.getTotalProcessed());

        Map<String,Integer> summary = analyzer.getSummaryMap();
        if (summary == null) {
            System.out.println("No Extra Info for: " + analyzer.getName());
        } else {
            System.out.println("Info:");

            List<String> keys = new ArrayList<>(summary.keySet());
            Collections.sort(keys);

            for (String key : keys) {
                System.out.println("  " + key + " : " + summary.get(key));
            }
        }
    }
}
