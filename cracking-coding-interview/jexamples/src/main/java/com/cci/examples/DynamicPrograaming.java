package com.cci.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicPrograaming {

    /**
     * Compute the N-th number of the Fibonacci sequence
     *
     */
    public static final class FibonacciSequenceCalc {

        private final int[] MEMO = new int[100];

        public FibonacciSequenceCalc() {
            // initialize the memoization array to be some invalid number that can not exist in a valid Fibonacci sequence, we are going to
            // use this as a flag to check if the number stored at this position or index is already computed
            Arrays.setAll(MEMO, operand -> -1);
        }

        public int calculateFibonacciNumber(int n) {
            // the first two numbers of the Fibonacci sequence are both 1, so for N less than or equal to 2 we return 1
            if (n <= 2) {
                return 1;
            }

            // when the number is already computed, we can simply return whatever value is stored at this position in the memo array
            if (MEMO[n] != -1) {
                return MEMO[n];
            }

            // compute the current Fibonacci number n, by computing the previous two numbers of the sequence, remember the current number in
            // the memo array, for future reuse
            MEMO[n] = calculateFibonacciNumber(n - 1) + calculateFibonacciNumber(n - 1);

            // return the stored value at the position N, it contains the current value of the number at position N in the Fibonacci
            // sequence
            return MEMO[n];
        }
    }

    /**
     * Count the total number of paths a player can take from a starting position of [0,0] to reach the ending position of [row,col], where
     * movements can be done only in the four cardinal directions, no diagnoal movement is allowed. The final/end point must be the bottom
     * right corner of the grid, the start is always the top right. We use a top down approach, we start from the end position and compute
     * down to the starting position
     *
     */
    public static final class GridPathCounter {

        // hold a string key which represents the count for the specific coordinates of the current row and col, [r,c]. The reason we can
        // use memo here is that once a given coordinate position of row,col is visited, we do not have to visit it again, all paths
        private final Map<String, Integer> MEMO = new HashMap<>();

        public int countGridPaths(int r, int c) {
            // when either of the coordinates points at an invalid position, then there are no valid paths to reach the end goal, there is
            // no grid where the row or columns can be 0, remember the rows and cols here are defined positions, therefore they must be
            // greater or equal to 1
            if (r == 0 || c == 0) {
                return 0;
            }

            // in a grid of 1x1, there is only 1 path between the start, top left, and the end, bot right, and both are exactly the same
            // grid cell
            if (r == 1 && c == 1) {
                return 1;
            }

            // build a unique key, from the current pair of coordinates, this will ensure we can key the map, correctly and have a unique
            // key - value relation between the coordinates and the count of paths to these specific coordinates
            String key = String.format("%d,%d", r, c);
            if (MEMO.containsKey(key)) {
                return MEMO.get(key);
            }

            // calculate the count paths, from the current position to the previous, possible positions, from the current position of
            // [row,col] we can either go up a row, or to the left in the col coordinates.
            int count = countGridPaths(r - 1, c) + countGridPaths(r, c - 1);

            // remember the count for this specific combination of rows and cols using the unique key, note the key is delimiting the
            // coordinates with a coma, which is a good idea, to ensure uniqueness of pairs. Why does remembering the key work, well since
            // we go in both directions, in this case first in the row the col, it is possible for one branch of these two, to each a
            // specific path first, meaning that when the second branch goes through, and sees the same path i.e coordinates they have been
            // already visisted and computed, this ONLY works, because we are doing a top down approach, where the recursive calls drill
            // down to the bottom, to the base cases and then ascends up, accumulating the count for specific paths.
            MEMO.put(key, count);
            return count;
        }
    }

    /**
     * Find the actual coordinates of each point that leads from the start to end points in a grid, where the end is the bottom right, and
     * the start is the top left. The grid can have invalid cells, which are unavailable to traverse, marked as 1, valid cells are empty,
     * marked as 0.
     *
     * This problem is an extension of the previous one, here instead we also collect the paths, for simplicity the row and column this time
     * are defined as indices instead of positions, but this is simply an implementation detail
     *
     */
    public static final class GridPathFinder {

        public boolean calculateGridPath(int[][] grid, int r, int c, List<String> path) {
            // when the row and columns are invalid, i.e. any position which is outside the allowed range of row and col, in this case row
            // and col are in index space, meaning anything less than 0 is invalid.
            if (r < 0 || c < 0) {
                return false;
            } else if (r == grid.length - 1 && c == grid[r].length - 1) {
                // This is a simple base case to check if we are at the end of the path, since this solution is top down, we start from the
                // end position, in this case the bottom right grid cell, we check that and add for the current path an end marker, it is
                // possible to have a path with only end, but no start, meaning that specific path has no path from end to start.
                path.add("[END]");
            }

            // when the grid cell has an invalid value, meaning it is not allowed, is 1, then simply return false, another base case to
            // ensure correct operation.
            if (grid[r][c] == 1) {
                return false;
            } else {
                // otherwise it means that we are at a valid position, meaning we can simply add the current corrdinates to the path, and
                // continue traversing forward
                path.add(String.format("[%d,%d]", r, c));
            }

            // the row and columns are at the start, top left position, meaning we mark this as start of the path, the two entries in the
            // path list, [start]-[end], signify the start and end of each path, and the coordinate positions in between
            if (r == 0 && c == 0) {
                path.add("[START]");
            }

            // recursively calculate the path, by first going up a row, then to the left in the col directions. It does not really matter
            // which direction we inspect first, row - col or col - row, all valid cells will be visited by the end of the algorithm
            return calculateGridPath(grid, r - 1, c, path) || calculateGridPath(grid, r, c - 1, path);
        }
    }

    public static final class StepClimbPermutations {

        private final int[] MEMO = new int[10];

        public StepClimbPermutations() {
            Arrays.setAll(MEMO, operand -> -1);
        }

        public int calculateStepsPermutations(int n) {
            if (n < 0) {
                return 0;
            } else if (n == 0) {
                return 1;
            } else if (MEMO[n - 1] != -1) {
                return MEMO[n - 1];
            }
            MEMO[n - 1] = calculateStepsPermutations(n - 1) + calculateStepsPermutations(n - 2) + calculateStepsPermutations(n - 3);
            return MEMO[n - 1];
        }
    }

    public static void main(String[] args) {
        // StepClimbPermutations perms = new StepClimbPermutations();
        // perms.calculateStepsPermutations(10);
        //
        // FibonacciSequenceCalc calc = new FibonacciSequenceCalc();
        // calc.calculateFibonacciNumber(18);
        //
        // GridPathCounter counter = new GridPathCounter();
        // counter.countGridPaths(5, 5);

        GridPathFinder finder = new GridPathFinder();
        int grid[][] = new int[][] {{0, 0, 0, 0, 1}, {0, 1, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 1, 0}, {1, 0, 0, 0, 0}};

        // int grid[][] = new int[][]{
        // { 0, 0, 1},
        // { 1, 0, 0},
        // { 0, 1, 0},
        // };

        List<String> path = new ArrayList<>();
        finder.calculateGridPath(grid, grid.length - 1, grid[0].length - 1, path);
        return;
    }
}
