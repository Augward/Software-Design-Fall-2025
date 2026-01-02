import java.util.Arrays;
import java.util.List;

/**
 * Driver class of Adjacency Graph class
 * Import java.util Arrays and List
 *
 * @author augward
 */
public class DriverGraph {
    public static void main(String[] args) {
        String filePath = "oral_exam2/S35_GraphAlgos_Medium/resources/words.dat";

        AdjacencyList AL1 = new AdjacencyList(filePath);

        System.out.println();


        // Prints out an array of words from file
        System.out.println("List of Words: " + Arrays.toString(AL1.getWordsList()) + "\n");


        // Easy tasks, no edges, most edges, avg connections
        System.out.println("num nodes with no edges: " + AL1.noEdgeCount());

        String[] mostEdges = AL1.mostEdgesWords();
        System.out.println("nodes with the most edges: " + Arrays.toString(mostEdges) +
                " with " + AL1.getWordsMap().get(mostEdges[0]).length + " vertices each");

        System.out.println("avg num of connections: " + AL1.averageEdgeCount());

        System.out.println();


        // Medium task, DFS and BFS algorithms
        System.out.println("DFS: " + AL1.DFS("omens"));
        System.out.println("BFS: " + AL1.BFS("omens"));

        System.out.println();


        // Finds the largest map of connected words
        List<String> largestSet = AL1.largestSet();
        System.out.println("Size of largest connected set: " + largestSet.size());
        System.out.println("Components of largest set:");
        // Special print similar to doc
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int rollOver = 1;
        for (String word : largestSet) {

            sb.append(word);
            sb.append(", ");
            if (rollOver == 12) {
                sb.append("\n");
                rollOver = 0;
            }
            rollOver++;
        }
        System.out.println(sb);
    }
}