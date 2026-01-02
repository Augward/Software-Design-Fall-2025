import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Creates an adjacency list based off a document of given words
 * <p>
 *     A class with no inheritance or composition that takes in a file and
 *     creates a map of all the connected words.  Connected words are ones that
 *     have only one letter difference.  Can also make several types
 *     of lists for varying purposes.
 * </p>
 * Relies on java.io and .util imports
 *
 * @author augward
 */
public class AdjacencyList {
    // The list of words and the map that links them together
    private final String[] wordsList;
    private final HashMap<String, String[]> wordsMap = new HashMap<>();

    /**
     * Constructs an adjacency list and list of words with a given file input
     *
     * @param filePath the String path of a file for .io
     */
    public AdjacencyList(String filePath) {
        List<String> linesList = new ArrayList<>();

        // BuffereReader that reads in each line from the file and adds them to a list
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesList.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        linesList.toArray(new String[0]);

        // Builds the map that represents the adjacency list
        List<String> differLists = new ArrayList<>();
        for (String firstList : linesList) {
            for (String secondList : linesList) {
                if (differByOne(firstList, secondList)) {
                    differLists.add(secondList);
                }
            }
            wordsMap.put(firstList, differLists.toArray(new String[0]));
            differLists.clear();
        }
        wordsList = linesList.toArray(new String[0]);
    }


    /**
     * Returns the list of words
     *
     * @return String words List
     */
    public String[] getWordsList() {
        return wordsList;
    }

    /**
     * Returns a map of words and their neighbors
     *
     * @return Hashmap wordsMap
     */
    public HashMap<String, String[]> getWordsMap() {
        return wordsMap;
    }


    /**
     * Prints out adjacency list from the hashmap in a table style
     *
     * @return string representation of an adjacency list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String word : wordsList) {
            sb.append(word);
            sb.append(" | ");
            sb.append(Arrays.toString(wordsMap.get(word)));
            sb.append("\n");
        }

        return sb.toString();
    }


    /**
     * Method used to check if two words are neighbors, (differ char by 1)
     *
     * @param firstList first word to check
     * @param secondList second word to check against
     * @return whether the two words are neighbors
     */
    private static boolean differByOne(String firstList, String secondList) {
        int differCount = 0;
        for (int i = 0; i < firstList.length(); i++) {
            if (firstList.charAt(i) != secondList.charAt(i)) {
                differCount++;
            }
        }

        return differCount == 1;
    }


    /**
     * Checks how many words have no edges or neighbors
     *
     * @return int count no words with no edges
     */
    public int noEdgeCount() {
        int noEdges = 0;
        for (String word : wordsList) {
            if (wordsMap.get(word).length == 0) {
                noEdges++;
            }
        }

        return noEdges;
    }


    /**
     * Finds the words that have the greatest number of edges
     *
     * @return array of words that have the highest number of edges
     */
    public String[] mostEdgesWords() {
        int mostEdges = 0;
        // Finds the max number of edges in list
        for (String word : wordsList) {
            if (wordsMap.get(word).length > mostEdges) {
                mostEdges = wordsMap.get(word).length;
            }
        }

        // Finds the words that have the max number of edges
        List<String> mostEdgesList = new ArrayList<>();
        for (String word : wordsList) {
            if (wordsMap.get(word).length == mostEdges) {
                mostEdgesList.add(word);
            }
        }

        return mostEdgesList.toArray(new String[0]);
    }


    /**
     * Adds up all edge counts and finds the average
     *
     * @return the average neighbor count per word
     */
    public double averageEdgeCount() {
        double totalEdges = 0.0;
        double totalVertices = wordsList.length;

        for (String word : wordsList) {
            totalEdges += wordsMap.get(word).length;
        }

        return totalEdges / totalVertices;
    }


    /**
     * Runs a DFS from a starting word
     * Completed by using resources such as Wiki's algorithms
     *
     * @param start starting word for the network
     * @return a List of words reached by DFS algorithm
     */
    public List<String> DFS(String start) {
        List<String> order = new ArrayList<>();

        if (!wordsMap.containsKey(start)) {return order;}

        Deque<String> stack = new ArrayDeque<>();
        Set<String> discovered = new HashSet<>();

        stack.push(start);
        while (!stack.isEmpty()) {
            String v = stack.pop();

            if (discovered.contains(v)) {continue;}

            discovered.add(v);
            order.add(v);

            String[] neighbors = wordsMap.get(v);

            for (String neighbor : neighbors) {
                stack.push(neighbor);
            }
        }
        return order;
    }


    /**
     * Runs a BFS from a starting word
     * Completed by using resources such as Wiki's algorithms
     *
     * @param root starting word for the network
     * @return a List of words reached by BFS algorithm
     */
    public List<String> BFS(String root) {
        List<String> order = new ArrayList<>();

        if (!wordsMap.containsKey(root)) {return order;}

        Queue<String> queue = new ArrayDeque<>();
        Set<String> explored = new HashSet<>();

        explored.add(root);
        queue.add(root);

        while (!queue.isEmpty()) {
            String v = queue.remove();
            order.add(v);

            String[] neighbors = wordsMap.get(v);

            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    explored.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }


    /**
     * Returns the largest spanning network of connected words
     *
     * @return List of words that are all connected
     */
    public List<String> largestSet() {
        // Creates a map of words that haven't been searched.
        HashMap<String, Boolean> searched = new HashMap<>();
        for (String word : wordsList) {
            searched.put(word, false);
        }

        List<String> largestSet = new ArrayList<>();
        int size = 0;

        // Loop that skips already searched words and builds the biggest connected network
        for (String word : wordsList) {
            if (searched.get(word) == true) {continue;}

            List<String> currentSet = DFS(word);

            for (String subWord : currentSet) {
                searched.put(subWord, true);
            }

            if  (currentSet.size() > size) {
                largestSet = currentSet;
                size = currentSet.size();
            }
        }
        return largestSet;
    }
}
