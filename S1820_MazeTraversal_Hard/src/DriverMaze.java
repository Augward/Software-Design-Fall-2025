import java.util.Arrays;

/**
 * Driver Class that utilizes an example maza to test Maze.java's functionality
 *
 * @author augward
 */
public class DriverMaze {
    public static void main(String[] args) {
        // Example grid of chars representing a maze
        char[][] grid = {
                "############".toCharArray(),
                "..#####....#".toCharArray(),
                "#.#.....##.#".toCharArray(),
                "#.#.###.#..#".toCharArray(),
                "#.###...##.#".toCharArray(),
                "#.#...##.#.#".toCharArray(),
                "#.###.#....#".toCharArray(),
                "#.....######".toCharArray(),
                "###.#....###".toCharArray(),
                "###.#.######".toCharArray(),
                "#...#.......".toCharArray(),
                "############".toCharArray(),
        };

        // Runs static method maze traversal with given values and grid
        grid = Maze.mazeTraversal(grid, 0, 10);
        System.out.println("Array of given maze: " + Arrays.deepToString(grid));
    }
}