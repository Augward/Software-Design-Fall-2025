import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class AdjacencyTest {

    private static final String PROJECT_PATH = "oral_exam2/S35_GraphAlgos_Medium/resources/words.dat";

    private static final String[] WORDS = {
            "aargh", "abaca", "abaci", "aback", "abaft", "abase",
            "abash", "abate", "abbey", "abbot", "abeam", "abend",
            "abets", "abhor", "abide", "abled", "abler", "abode",
            "abort", "about", "above", "absit", "abuse", "abuts",
            "abuzz", "abyss", "ached", "aches", "achoo", "acids",
            "acing", "acked", "acmes", "acned", "acnes", "acorn",
            "acres", "acrid", "acted"
    };

    private Path resolveWordFile() throws IOException {
        Path projectFile = Paths.get(PROJECT_PATH);
        if (Files.exists(projectFile)) {
            return projectFile;
        }

        Path tmp = Files.createTempFile("adjacency_test_words", ".dat");
        Files.write(tmp, java.util.Arrays.asList(WORDS));
        return tmp;
    }



    @Test
    void testConstructorAndGetters() throws IOException {
        Path file = resolveWordFile();
        // In case file is not found
        boolean isTemp = !file.toAbsolutePath().toString().endsWith(PROJECT_PATH);

        try {
            AdjacencyList ALT = new AdjacencyList(file.toString());

            assertNotNull(ALT.getWordsList());
            assertEquals(39, ALT.getWordsList().length);
            assertNotNull(ALT.getWordsMap());
            assertEquals(ALT.getWordsList().length, ALT.getWordsMap().size());

            assertTrue(ALT.getWordsMap().containsKey("abaca"));
            assertTrue(ALT.getWordsMap().containsKey("acted"));

        } finally {
            // delete temp if we created one
            if (isTemp) Files.deleteIfExists(file);
        }
    }



    @Test
    void testEdgesAndStats() throws IOException {
        Path file = resolveWordFile();
        boolean isTemp = !file.toAbsolutePath().toString().endsWith(PROJECT_PATH);

        try {
            AdjacencyList ALT = new AdjacencyList(file.toString());

            String[] neighbors = ALT.getWordsMap().get("abaca");
            assertNotNull(neighbors);
            assertEquals(2, neighbors.length);

            int noEdges = ALT.noEdgeCount();
            assertEquals(15, noEdges);

            String[] mostEdges = ALT.mostEdgesWords();
            assertEquals("[ached, aches, acned, acnes]", Arrays.toString(mostEdges));

            double avg = ALT.averageEdgeCount();
            assertTrue(1 < avg && avg < 2);
        } finally {
            if (isTemp) Files.deleteIfExists(file);
        }
    }



    @Test
    void testTraversal() throws IOException {
        Path file = resolveWordFile();
        boolean isTemp = !file.toAbsolutePath().toString().endsWith(PROJECT_PATH);

        try {
            AdjacencyList ALT = new AdjacencyList(file.toString());

            List<String> dfs = ALT.DFS("abaca");
            System.out.println(dfs);
            List<String> bfs = ALT.BFS("abaca");
            System.out.println(bfs);

            assertNotNull(dfs);
            assertNotNull(bfs);
            assertFalse(dfs.isEmpty());
            assertFalse(bfs.isEmpty());

            assertTrue(dfs.contains("abaci"));
            assertTrue(dfs.contains("aback"));
            assertTrue(bfs.contains("abaci"));
            assertTrue(bfs.contains("aback"));

            Set<String> dfsSet = new HashSet<>(dfs);
            Set<String> bfsSet = new HashSet<>(bfs);
            assertEquals(dfsSet, bfsSet);

            List<String> largest = ALT.largestSet();
            System.out.println(largest);
            assertEquals("[ached, acted, acned, acnes, acres, acmes, aches, acked]", largest.toString());
        } finally {
            if (isTemp) Files.deleteIfExists(file);
        }
    }



    @Test
    void testString() throws IOException {
        Path file = resolveWordFile();
        boolean isTemp = !file.toAbsolutePath().toString().endsWith(PROJECT_PATH);

        try {
            AdjacencyList ALT = new AdjacencyList(file.toString());
            String s = ALT.toString();

            assertNotNull(s);
            assertTrue(s.contains("aargh"));
            assertTrue(s.contains("acted"));
            assertTrue(s.contains("|"));
        } finally {
            if (isTemp) Files.deleteIfExists(file);
        }
    }
}
