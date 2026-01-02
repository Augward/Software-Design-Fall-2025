
/**
 * Class that represents a log line
 * <p>
 *     The class takes in a line from the input file and splits it apart to find
 *     the level, time, message, and username associated with that line.
 * </p>
 *
 * @author augward
 */
public final class LogEntry {
    // "INFO", "WARN", "ERROR"
    private final String level;
    // XXXX-XX-XX XX:XX:XX
    private final String time;
    // message after type and time
    private final String message;
    // username in INFO
    private final String username;

    // END sentinel to tell analyzers to stop
    public static final LogEntry END = new LogEntry("END XXXX-XX-XX XX:XX:XX END");

    /**
     * The constructor that turns a line from file into a log of data
     *
     * @param line the line from the file
     */
    public LogEntry(String line) {
        String[] parts = line.split(" ", 4);

        this.level = parts[0].toUpperCase();
        this.time = parts[1] + " " + parts[2];
        this.message = parts[3];

        // Finds if the line has a username in it
        String temp = null;
        String[] partsMessage = parts[3].split(" ");
        for (int i = 1; i < partsMessage.length; i++) {
            if (partsMessage[i].startsWith("user") && !(partsMessage[i].startsWith("user:"))) {
                temp = partsMessage[i];
                break;
            }
        }
        this.username = temp;
    }


    /**
     * Getter that returns the logs level
     *
     * @return a String of the given level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Getter that returns the logs time string
     *
     * @return a String of the given time
     */
    public String getTime() {
        return time;
    }

    /**
     * Getter that returns the logs message, may be long
     *
     * @return a String of the given message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter that returns the logs username, null if it doesn't have one
     *
     * @return a String of the given username
     */
    public String getUsername() {
        return username;
    }
}
