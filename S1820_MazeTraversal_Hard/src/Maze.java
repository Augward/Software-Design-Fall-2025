/**
 * Maze solving class that generates useful components of a Maze
 * <p>
 *     A class with no inheritance or composition that takes in a grid
 *     of characters that represent a Maze.  It checks over the grid to
 *     make sure it's a viable maze or discover the resources to fix it.
 * </p>
 *
 * @author augward
 */
public class Maze {
    // Main variables that represent the Maze and its specs.
    private final int size;
    private final char[][] grid;

    // Variables utilized for the recursive maze search methods
    private int startCol = -1;
    private int startRow = -1;
    private boolean endFound = false;

    /**
     * Constructs a Maze with given char grid, checking all slot values.
     *
     * @param grid the double char array with Maze qualities
     */
    public Maze(char[][] grid) {
        try {
            checkValidChar(grid);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid characters in grid");
        }

        int sizeChecked;
        try {
            sizeChecked = checkSquareGrid(grid);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid grid, not square");
            sizeChecked = 12;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid size of grid");
            sizeChecked = 12;
        }

        this.size = sizeChecked;
        this.grid = grid;
    }


    /**
     * Returns the maze's char grid
     *
     * @return double char array grid
     */
    public char[][] getGrid() {
        return this.grid;
    }

    /**
     * Returns the maze's size
     *
     * @return int size of maze
     */
    public int getSize() {
        return this.size;
    }


    /**
     * Checks if the characters in char grid are valid for the maze
     *
     * @param grid the maze holder
     */
    private void checkValidChar(char[][] grid) {
        for (char[] charList : grid) {
            for (char currentChar : charList) {
                if (currentChar != '#' && currentChar != '.' && currentChar != 'x' && currentChar != '0') {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    /**
     * Checks that the grid is a square shape and uniform
     *
     * @param grid the double char array
     * @return the size of the char grid
     */
    private int checkSquareGrid(char[][] grid) {
        int sizeToCheck = grid.length;

        if (sizeToCheck < 2) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (char[] charList : grid) {
            if (charList.length != sizeToCheck) {
                throw new IllegalArgumentException();
            }
        }

        return sizeToCheck;
    }

    /**
     * Checks that location called is within grid bounds
     *
     * @param col the column called
     * @param row the row called
     * @return boolean whether location called is within grid
     */
    private boolean checkInBounds(int col, int row) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Checks if location is at the border
     *
     * @param col the column called
     * @param row the row called
     * @return boolean whether the location is at the border
     */
    private boolean checkIfBorder(int col, int row) {
        return row == 0 || row == size - 1 || col == 0 || col == size - 1;
    }


    /**
     * Helper method used to find an entrance into the maze if the start position given is invalid
     *
     * @return int array of 2 values, a new entrance point
     */
    private int[] findEntrance() {
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                if (checkIfBorder(col, row) && grid[row][col] == '.') {
                    return new int[]{col, row};
                }
            }
        }
        return null;
    }


    /**
     * Prints out char grid like a matrix for live values
     *
     * @return string representation of Maze
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] charList : grid) {
            for (char currentChar : charList) {
                sb.append(currentChar);
                sb.append(" ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }


    /**
     * Static method of maze traversal, can be used without initializing an outside Maze
     *
     * @param grid double array of charters
     * @param currentCol current column
     * @param currentRow current row
     * @return char array of completed maze traversal
     */
    public static char[][] mazeTraversal(char[][] grid, int currentCol, int currentRow) {
        Maze tempConvert = new Maze(grid);
        tempConvert.mazeTraversal(currentCol, currentRow);
        System.out.println("Size of given maze: " + tempConvert.getSize());
        return tempConvert.getGrid();
    }

    /**
     * Recursive mazeTraversal method, searches through maze for exit
     *
     * @param currentCol current column
     * @param currentRow current row
     */
    public void mazeTraversal(int currentCol, int currentRow) {
        boolean startCall = (startCol == -1 && startRow == -1);

        // If this is the first call, it establishes an entrance point
        if (startCall) {
            if (!checkInBounds(currentCol, currentRow) || !checkIfBorder(currentCol, currentRow) || !(grid[currentRow][currentCol] == '.')) {
                int[] entrance = findEntrance();
                if (entrance == null) {
                    System.err.println("No entrances found and invalid start position");
                } else {
                    currentCol = entrance[0];
                    currentRow = entrance[1];
                }
            }

            startCol = currentCol;
            startRow = currentRow;

            System.out.println("Matrix Traversal Start:");
            System.out.print(this);
        }

        // Cancels all other recursive methods by returning when end is found
        if (!endFound) {
                grid[currentRow][currentCol] = 'x';
        } else {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (grid[row][col] == '0') {
                        grid[row][col] = '.';
                    }
                }
            }

            System.out.println("-----------------------");
            System.out.println("Final Maze Path");
            System.out.println(this);
        }

        // Pauses in between each print/action
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }

        // Sets end condition if we found the border/end
        if (checkIfBorder(currentCol, currentRow) && !(currentCol == startCol && currentRow == startRow)) {
            endFound = true;
        }

        // Recursive caller, simple maze search algorithm by checking all 4 corners
        if (!endFound) {
            if (checkInBounds(currentCol, currentRow - 1)) {
                if (grid[currentRow - 1][currentCol] == '.') {
                    System.out.println("-----------------------");
                    System.out.print(this);
                    mazeTraversal(currentCol, currentRow - 1);
                }
            }
            if (checkInBounds(currentCol + 1, currentRow)) {
                if (grid[currentRow][currentCol + 1] == '.') {
                    System.out.println("-----------------------");
                    System.out.print(this);
                    mazeTraversal(currentCol + 1, currentRow);
                }
            }
            if (checkInBounds(currentCol, currentRow + 1)) {
                if (grid[currentRow + 1][currentCol] == '.') {
                    System.out.println("-----------------------");
                    System.out.print(this);
                    mazeTraversal(currentCol, currentRow + 1);
                }
            }
            if (checkInBounds(currentCol - 1, currentRow)) {
                if (grid[currentRow][currentCol - 1] == '.') {
                    System.out.println("-----------------------");
                    System.out.print(this);
                    mazeTraversal(currentCol - 1, currentRow);
                }
            }

            // Prints the current progress to screen
            if (!endFound) {
                System.out.println("-----------------------");
                System.out.print(this);
                grid[currentRow][currentCol] = '0';
            }
        }
    }
}

