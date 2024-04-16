package com.cci.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            MEMO[n] = calculateFibonacciNumber(n - 1) + calculateFibonacciNumber(n - 2);

            // return the stored value at the position N, it contains the current value of the number at position N in the Fibonacci
            // sequence
            return MEMO[n];
        }

        public int calculateFibonacciNumberIterative(int n) {
            // this is a good idea overall, we can add a basic guard case first, to make sure we do not receive an invalid input, and we
            // know that there is no 0-th Fibonacci sequence number
            if (n <= 0) {
                return 0;
            }
            // we first create our tabultaion table, which is going to store all values of the Fibonacci sequence, this is a common approach
            // when solving these issues, with iterative dynamic programming
            int[] table = new int[n + 1];

            // by default we have to fill it up with some invalid, negative case values, in this case -1 is a good candidate since we know
            // that the Fibonacci sequence has no negative numbers
            Arrays.fill(table, -1);

            // we have to initialize our base case, we know that the first two numbers of the sequence are 0 and 1, and every other number
            // in the sequence can be built on top of those two initial values
            table[0] = 0;
            table[1] = 1;

            // start form the 2-nd fib number and calcualte up until we reach n, note that the size of the array here is actually n + 1,
            // this is because the base case of n[0]
            for (int i = 2; i < table.length; i++) {
                table[i] = table[i - 1] + table[i - 2];
            }

            // return the value at position n in the array, our array is holding n + 1 elements, and the last element is at index n, since
            // we already have a base case value at index 0
            return table[n];
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

    /**
     * Given a target sum, find if any combination of numbers in a sequence of numbers represented as an array can be summed to the target
     * sum, the sequence of numbers is positive
     *
     */
    public static final class CanSumSequence {

        // holds a target sum, and a flag representing if that target sum can be achieved from the target input numbers provided
        private final Map<Integer, Boolean> MEMO = new HashMap<>();

        public boolean canSumSequence(int target, int[] sequence) {
            // optimization case, first check if the target sum is already in the memo, this means that there was at least one call to
            // canSum, that reached this target sum, and computed the result, before we reach this again, we do not need to re-compute it
            // since the sequence is always the same, if we reach this target sum again, we can simply return the cached result value
            // instead.
            if (MEMO.containsKey(target)) {
                return MEMO.get(target);
            }
            // the first base case where the target is negative, meaning that there is no way to sum to the target, since the sequence is
            // only composed of positive numbers.
            if (target < 0) {
                return false;
            }
            // when the target is 0, that means that at this point the sum was reached, therefore we can early return true for this target
            // sum.
            if (target == 0) {
                return true;
            }

            // go thorugh each number in the sequence, and find the new target sum
            for (int i : sequence) {
                // the new target sum is the original - the current number, meaning that with each call our target sum will reduce by each
                // number in the sequence, eventually it will reach 0, or be negative, hitting the 2 base cases, when that happens, we will
                // receive a return of false or true if the sum can be generated from the target sequence
                int newTarget = target - i;

                // the first call which returns true, can short circuit this loop and return early, we care only about if there is at least
                // one combination in the sequence which sums up to the target, we do not care about the actual sequence of numbers, the
                // first one that was found will suffice
                if (canSumSequence(newTarget, sequence)) {
                    // before we early return, we also cache the current newTarget, into the memo object, meaning that we know that for this
                    // target sum there is a sequence of numbers, that will sum/reach this newTarget sum
                    MEMO.put(target, true);
                    return true;
                }
            }

            // we have gone through all numbers in the sequence for the current target sum, and none of them returned true, this is the
            // final base case where we can say that there is no number in the sequence that sums up to the target number, on top of that
            // we also mark that target sum as 'unreacheable' for this given input of sequence numbers
            MEMO.put(target, false);
            return false;
        }

        public boolean canSumSequenceIterative(int target, int[] sequence) {
            // we can put a small base case boundary on our input, to make sure that the function would operate correctly, after all if we
            // have a target sum of 0, we can easily assume that it can be generated by simply not taking any element from the sequence
            if (target == 0) {
                return true;
            }

            // start with a pre-defined array of boolean statuses, note that we again generate an array which is filled with all negative
            // cases, by default
            boolean[] table = new boolean[target + 1];
            Arrays.fill(table, false);

            // set the base postive case as we know, for a target sum of 0, we can say that there can be a sequence of numbers, by simply
            // taking no numbers, we start off from this initial value and build from it
            table[0] = true;

            // loop through all states in the tabulation table
            for (int i = 0; i < table.length; i++) {
                // when we meet a positive case, we can build off of it
                if (table[i] == true) {
                    // we are in a positive case, meaning the sum to target[i] can be generated, below we check starting from `i` which
                    // other sums can be generated by adding `num` to `i`
                    for (int num : sequence) {
                        // sums `i + num` can be generated if the array has enough space to fit the number, remember that the array / table
                        // is created with at most target + 1 elements, meaning that we have to check our bounds first
                        if (i + num < table.length) {
                            // i + num is in range and we mark this sum target as possible
                            table[i + num] = true;
                        }
                    }
                }
            }

            // return the state at the target sum position, remember that we will have one of three cases for this position
            // - nothing ever summed up to target - meaning it will remain `false` from the initializetion above
            // - there was no pair of `i + num` that summed up to target, we either overshot or undershot
            // - there was a pair of `i + num` that exactly summed up to target and the cell was marked true
            return table[target];
        }
    }

    /**
     * Given a target sum, find the first sequence combination of numbers in a sequence of numbers represented as an array that can be
     * summed to the target sum, the sequence of numbers is positive
     *
     */
    public static final class FindSumSequence {

        // unlike the `canSum` problem above, this memo object, stores references to the first sequence that can sum to the target sum,
        // where the target sum is the key in the memo table, and the value is the sequence that sums up to that target sum
        private final Map<Integer, List<Integer>> MEMO = new HashMap<>();

        public List<Integer> findSumSequence(int target, int[] sequence) {
            // optimization case, first check if the target sum is already in the memo, this means that there was at least one call to
            // canSum, that reached this target sum, and computed the result, before we reach this again, we do not need to re-compute it
            // since the sequence is always the same, if we reach this target sum again, we can simply return the cached result value
            // instead.
            if (MEMO.containsKey(target)) {
                return MEMO.get(target);
            }
            // the first base case where the target is negative, meaning that there is no way to sum to the target, since the sequence is
            // only composed of positive numbers. Note that we here return a null, which means this is a negative base case, similar to
            // `false`,
            // however we can not return `false` here, so null will suffice.
            if (target < 0) {
                return null;
            }
            // when the target is 0, that means that at this point the sum was reached, therefore we can early return true for this target
            // sum. Note we return an empty array which can be later used to easily identify the positive base case, versus the negative
            if (target == 0) {
                return new ArrayList<>();
            }

            // go thorugh each number in the sequence, and find the new target sum
            for (int i : sequence) {
                // the new target sum is the original minus the current number, meaning that with each call our target sum will reduce by
                // each
                // number in the sequence, eventually it will reach 0, or be negative, hitting the 2 base cases, when that happens, we will
                // receive a return of false or true if the sum can be generated from the target sequence
                int newTarget = target - i;

                // the first call which returns a non-null list, can short circuit this loop and return early, we care only about if there
                // is at least one combination in the sequence which sums up to the target, the first found current sequence returned below,
                // is for newTarget
                List<Integer> currentSequence = findSumSequence(newTarget, sequence);
                if (!Objects.isNull(currentSequence)) {
                    // the actual result here of currentSequence is for newTarget, however to find the final result sequence for the input
                    // argument of `target`, we have to add the current number `i` too. We extend the currentSequence with the current
                    // number `i`. What that means is newTarget can be reached with `currentSequence`, therefore target can be reached with
                    // a result sequence of (...currentSequence, i). We clone the currentSequence to avoid reference assignment, this is
                    // purely implementation detail, but good to know
                    List<Integer> resultSequence = (List<Integer>) ((ArrayList<Integer>) currentSequence).clone();
                    resultSequence.add(i);

                    // memo the extended resultSequence, remember we memo here for `target` as key, since the resultSequence is for
                    // newTarget + i or in other words = `target`
                    MEMO.put(target, resultSequence);
                    return resultSequence;
                }
            }

            // we have gone through all numbers in the sequence for the current target sum, and none of them returned true, this is the
            // final base case where we can say that there is no number in the sequence that sums up to the target number, on top of that
            // we also mark that target sum as 'unreacheable' for this given input of sequence numbers by storing `null` for the sequence
            MEMO.put(target, null);
            return null;
        }

        public List<Integer> findSumSequenceIterative(int target, int[] sequence) {
            // we can put a small base case boundary on our input, to make sure that the function would operate correctly, after all if we
            // have a target sum of 0, we can easily assume that it can be generated by simply not taking any element from the sequence
            if (target == 0) {
                return new ArrayList<>();
            }

            // start with a pre-defined array of negative states, note that we again generate an array which is filled with all negative
            // values, by default
            List[] table = new List[target + 1];
            Arrays.fill(table, null);

            // set the base postive case as we know, for a target sum of 0, we can say that there can be a sequence of numbers, by simply
            // taking no numbers, we start off from this initial value and build from it
            table[0] = new ArrayList<>();

            // loop through all states in the tabulation table
            for (int i = 0; i < table.length; i++) {
                // when we meet a positive case, we can build off of it
                if (!Objects.isNull(table[i])) {
                    // we are in a positive case, meaning the sum to target[i] can be generated, below we check starting from `i` which
                    // other sums can be generated by adding `num` to `i`
                    for (int num : sequence) {
                        // sums `i + num` can be generated if the array has enough space to fit the number, remember that the array / table
                        // is created with at most target + 1 elements, meaning that we have to check our bounds first
                        if (i + num < table.length) {
                            // i + num is in range what we do is clone the sequence already at table[i] and append the new number that we
                            // have used to generate `i + num`
                            List<Integer> seq = new ArrayList<>(table[i]);
                            seq.add(num);
                            // note that this will override sequences that are already in place, we can optimize this a bit by first
                            // checking if there is already something in table[i + sum] and skip it, to find the first, instead of the last
                            // sequence, but that is implementation detail
                            table[i + num] = seq;
                        }
                    }
                }
            }

            // return the value at the position for target, remember again we can either have valid list here, or nil, if we never managed
            // to generate the target sum whatsoever
            return (List<Integer>) table[target];
        }
    }

    public static final class BestSumSequence {

        private final Map<Integer, List<Integer>> MEMO = new HashMap<>();

        public List<Integer> bestSumSequence(int target, int[] sequence) {
            // optimization case, first check if the target sum is already in the memo, this means that there was at least one call to
            // canSum, that reached this target sum, and computed the result, before we reach this again, we do not need to re-compute it
            // since the sequence is always the same, if we reach this target sum again, we can simply return the cached result value
            // instead.
            if (MEMO.containsKey(target)) {
                return MEMO.get(target);
            }
            // the first base case where the target is negative, meaning that there is no way to sum to the target, since the sequence is
            // only composed of positive numbers. Note that we here return a null, which means this is a negative base case, similar to
            // `false`,
            // however we can not return `false` here, so null will suffice.
            if (target < 0) {
                return null;
            }
            // when the target is 0, that means that at this point the sum was reached, therefore we can early return true for this target
            // sum. Note we return an empty array which can be later used to easily identify the positive base case, versus the negative
            if (target == 0) {
                return new ArrayList<>();
            }

            List<Integer> bestSequence = null;

            // go thorugh each number in the sequence, and find the new target sum
            for (int i : sequence) {
                // the new target sum is the original minus the current number, meaning that with each call our target sum will reduce by
                // each
                // number in the sequence, eventually it will reach 0, or be negative, hitting the 2 base cases, when that happens, we will
                // receive a return of false or true if the sum can be generated from the target sequence
                int newTarget = target - i;

                // the first call which returns a non-null list, can short circuit this loop and return early, we care only about if there
                // is at least one combination in the sequence which sums up to the target, the first found current sequence returned below,
                // is for newTarget
                List<Integer> currentSequence = bestSumSequence(newTarget, sequence);
                if (!Objects.isNull(currentSequence)) {
                    // the actual result here of currentSequence is for newTarget, however to find the final result sequence for the input
                    // argument of `target`, we have to add the current number `i` too. We extend the currentSequence with the current
                    // number `i`. What that means is newTarget can be reached with `currentSequence`, therefore target can be reached with
                    // a result sequence of (...currentSequence, i). We clone the currentSequence to avoid reference assignment, this is
                    // purely implementation detail, but good to know
                    List<Integer> resultSequence = (List<Integer>) ((ArrayList<Integer>) currentSequence).clone();
                    resultSequence.add(i);

                    // make sure to remember the shortest sequence in the bestSequence variable, this way at the end of the function we will
                    // have either the shortest sequence that can sum up to the target, or nil if no sequence exists that sums up to target
                    if (Objects.isNull(bestSequence) || resultSequence.size() < bestSequence.size()) {
                        bestSequence = resultSequence;
                    }
                    // memo the extended resultSequence, remember we memo here for `target` as key, since the resultSequence is for
                    // newTarget + i or in other words = `target`
                    MEMO.put(target, resultSequence);
                }
            }

            // we have gone through all numbers in the sequence for the current target sum, and none of them returned true, this is the
            // final base case where we can say that there is no number in the sequence that sums up to the target number, on top of that
            // we also mark that target sum as 'unreacheable' for this given input of sequence numbers by storing `null` for the sequence
            MEMO.put(target, bestSequence);
            return bestSequence;
        }

        public List<Integer> bestSumSequenceIterative(int target, int[] sequence) {
            // we can put a small base case boundary on our input, to make sure that the function would operate correctly, after all if we
            // have a target sum of 0, we can easily assume that it can be generated by simply not taking any element from the sequence
            if (target == 0) {
                return new ArrayList<>();
            }

            // start with a pre-defined array of negative states, note that we again generate an array which is filled with all negative
            // values, by default
            List[] table = new List[target + 1];
            Arrays.fill(table, null);

            // set the base postive case as we know, for a target sum of 0, we can say that there can be a sequence of numbers, by simply
            // taking no numbers, we start off from this initial value and build from it
            table[0] = new ArrayList<>();

            // loop through all states in the tabulation table
            for (int i = 0; i < table.length; i++) {
                // when we meet a positive case, we can build off of it
                if (!Objects.isNull(table[i])) {
                    // we are in a positive case, meaning the sum to target[i] can be generated, below we check starting from `i` which
                    // other sums can be generated by adding `num` to `i`
                    for (int num : sequence) {
                        // sums `i + num` can be generated if the array has enough space to fit the number, remember that the array / table
                        // is created with at most target + 1 elements, meaning that we have to check our bounds first
                        if (i + num < table.length) {
                            // i + num is in range what we do is clone the sequence already at table[i] and append the new number that we
                            // have used to generate `i + num`
                            List<Integer> seq = new ArrayList<>(table[i]);
                            seq.add(num);
                            // note that this will override sequences that are already in place, we can optimize this a bit by first
                            // checking if there is already something in table[i + sum] and skip it, to find the first, instead of the last
                            // sequence, but that is implementation detail
                            List<Integer> current = (List<Integer>) table[i + num];

                            // before we can replace the sequence with the new one, we have to check if there is alrady something there, or
                            // if the new sequence is 'better', or in other words have less elements than the one currently being there
                            if (Objects.isNull(current) || current.size() > seq.size()) {
                                table[i + num] = seq;
                            }
                        }
                    }
                }
            }

            // return the value at the position for target, remember again we can either have valid list here, or nil, if we never managed
            // to generate the target sum whatsoever
            return (List<Integer>) table[target];
        }
    }

    /**
     * Check if a word can be constructed from a list of dictionary words, where each word from the dictionary can be concatenated to
     * construct the main word, we can reuse the words from the dictionary as many times as we want, the dictionary of words itself, is not
     * mutated
     *
     */
    public static final class CanConstructWord {

        private final Map<String, Boolean> MEMO = new HashMap<>();

        public boolean canConstructWord(String target, String[] dictionary) {
            // the main base case here, every empty word, can be "built" from an array of any words, by simply not picking anything from the
            // array in the first place, this base case is a bit weird, but it helps us work with the string, for example below we leverage
            // a `substring` property, where if the `substring` method is called with beginIndex equal to the length of the string, the
            // returned string is empty "", it is not an exception, or out of bounds error.
            if (target.length() == 0) {
                return true;
            }
            // check if the target string is already in the map, if it is we already have gone through it once, during the top-down
            // recursion, since we drill down first, and then unwind, it is possible to meet the same suffx/target word multiple times,
            // depending on the initial target word and dictionary of words, if it is in the map we will have the result, either we can
            // (true) or we can not (false) construct that particular key/suffix from the dictionary of words.
            if (MEMO.containsKey(target)) {
                return MEMO.get(target);
            }

            for (String word : dictionary) {
                // in case the main word is smaller than the current item from words, then simply skip this one, there is no way the build
                // the shorter main word from a longer word from the words dictionary
                if (target.length() < word.length()) {
                    continue;
                }

                // pull the same number of characters from the main word, as our prefix, when this prefix matches
                String prefix = target.substring(0, word.length());

                // compare the prefix characters with the actual current item word, if they match, call recursively the function with the
                // left of suffix, the rest of the string note, there is a trick here, String.substring when invoked with a value for
                // beginIndex of exactly the string.length, will return emtpy string "", our base case this is how we keep pulling prefixes
                // from the main word, until the main word becomes empty string - "". We can also check if the current item.length is equal
                // to the main word.length, and if yes simply stop there, but we use this special property if String.substring instead
                if (prefix.equalsIgnoreCase(word) || canConstructWord(target.substring(word.length()), dictionary)) {
                    // if we hit this point that means the prefix was exactly equal to the word, and the recursive call also returned `true`
                    // for the `suffix` part of the main word
                    MEMO.put(target, true);
                    return true;
                }
            }

            // in case we do not enter the true branch above, this means we could not build anytning from the words dictionary, therefore
            // return false
            MEMO.put(target, false);
            return false;
        }
    }

    /**
     * An extension of the previous problem, which builds on top and tries to answer the question as to how many different combinations of
     * the dictionary words can we use, to create the final target word, this is basically counting all positive branches which result in
     * the creation of the tharget word
     *
     */
    public static final class CountConstructWord {

        private final Map<String, Integer> MEMO = new HashMap<>();

        public int countConstructWord(String target, String[] dictionary) {
            // when the length of the target word is zero, we can conclude that any dictionary of words can build an empty target word, by
            // simply not taking any words from the dictionary
            if (target.length() == 0) {
                return 1;
            }
            // obtain the result of the memo by key, if we have already stored any result in the memo table, for this key we want to obtain
            // that value, true or false, does no matter if it was already computed for this target word we get it
            if (MEMO.containsKey(target)) {
                return MEMO.get(target);
            }

            int counter = 0;
            for (String word : dictionary) {
                // in case the main word is smaller than the current item from words, then simply skip this one, there is no way the build
                // the shorter main word from a longer word from the words dictionary
                if (target.length() < word.length()) {
                    continue;
                }

                // pull the same number of characters from the main word, as our prefix, when this prefix matches
                String prefix = target.substring(0, word.length());
                if (prefix.equalsIgnoreCase(word)) {
                    // in case the prefix matches, we can then add the count returned by the recursive calls, note that we still do a
                    // top-down dive, we drill till the base cases and then bubble up the recursive call stack.
                    counter += countConstructWord(target.substring(word.length()), dictionary);
                }
            }

            // the value of count will either remain 0, or be incremented based on the result of the recursive calls, in any case we simply
            // store the count value, what it represents in this context is for the current value of `target` how many combinations of the
            // dictionary words exist that make up the target word.
            MEMO.put(target, counter);
            return counter;
        }
    }


    /**
     * Find all sequences of words in the dictionary that can combine to construct the target word, this is an extension of the problem
     * above, however we want to find all, combinations and also return them
     *
     */
    public static final class AllConstructWord {

        private final Map<String, List<String>> MEMO = new HashMap<>();

        public List<String> allConstructWord(String target, String[] dictionary) {
            // this is the positive base case, reaching this point means that we have truncated the target string or word, to be empty, if
            // it is empty that means we have found a sequence to exist that constructs the intial word, kind of
            if (target.length() == 0) {
                return new ArrayList<>();
            }
            if (MEMO.containsKey(target)) {
                return MEMO.get(target);
            }

            // this is in a way the negative case, if none of the branches below suffice, we will return nil, which means there is no way to
            // build the target string / word, from any words in the dictionary, provided above.
            List<String> result = null;
            for (String word : dictionary) {
                // in case the main word is smaller than the current item from words, then simply skip this one, there is no way the build
                // the shorter main word from a longer word from the words dictionary
                if (target.length() < word.length()) {
                    continue;
                }

                // pull the prefix, the size of the prefix is exactly the size of the current word from the dictionary
                String prefix = target.substring(0, word.length());

                // only in case the prefix matches, can we continue with the rest of the main target word, or to be more precise, the suffix
                // is what we use to drill down
                if (prefix.equalsIgnoreCase(word)) {
                    // construct the rest of the word, calling the function recursively, in case this returns a non nil result we know that
                    // for the current prefix and suffix we can construct the target word, note we do this again top-down, we drill down to
                    // the base case, we unwind, and only then can we make the decision if we can construct the word, this is very important
                    // observation
                    List<String> current = allConstructWord(target.substring(word.length()), dictionary);
                    if (!Objects.isNull(current)) {
                        // from the result of the recursive call construct the string of words from the dictionary that can be combined to
                        // make the word
                        StringBuilder builder = new StringBuilder(word);

                        // simply construct the string with some delimiter easily identify the words from the dictionary which were used to
                        // construct the target word.
                        for (String string : current) {
                            builder.append(",");
                            builder.append(string);
                        }

                        // the first time we see the result, it might be nil, meaning we have to initialize it first, with an empty array
                        if (Objects.isNull(result)) {
                            result = new ArrayList<>();
                        }

                        // add the built string of combinations to the final result which we then return eventually, after the body of the
                        // for loop
                        result.add(builder.toString());
                    }
                }
            }

            // at this point, result will either be initialized, or nil, if it was initialized, that means we have found at least one, but
            // maybe more, combinations from word dictionary that make up the target word
            MEMO.put(target, result);

            // this return serves as both a positive and negative case, if we never entered any true branch in the for loop above, it will
            // be nil, otherwise initialized
            return result;
        }
    }

    public static void main(String[] args) {
        // StepClimbPermutations perms = new StepClimbPermutations();
        // perms.calculateStepsPermutations(10);

        // FibonacciSequenceCalc calc = new FibonacciSequenceCalc();
        // calc.calculateFibonacciNumber(18);
        // calc.calculateFibonacciNumberIterative(18);

        // GridPathCounter counter = new GridPathCounter();
        // counter.countGridPaths(5, 5);

        // GridPathFinder finder = new GridPathFinder();
        // int grid[][] = new int[][] {{0, 0, 0, 0, 1}, {0, 1, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 1, 0}, {1, 0, 0, 0, 0}};
        // int grid[][] = new int[][]{
        // { 0, 0, 1},
        // { 1, 0, 0},
        // { 0, 1, 0},
        // };

        // List<String> path = new ArrayList<>();
        // finder.calculateGridPath(grid, grid.length - 1, grid[0].length - 1, path);

        // CanSumSequence can = new CanSumSequence();
        // can.canSumSequence(5, new int[] {4, 2, 3, 1, 5});
        // can.canSumSequenceIterative(5, new int[] {4, 2, 3, 5, 10});

        // FindSumSequence find = new FindSumSequence();
        // find.findSumSequence(5, new int[] {4, 2, 3, 1, 5});
        // find.findSumSequenceIterative(5, new int[] {4, 2, 3, 5});

        // BestSumSequence best = new BestSumSequence();
        // best.bestSumSequence(5, new int[] {1, 2, 3, 4, 6});

        // CanConstructWord constructor = new CanConstructWord();
        // constructor.canConstructWord("abcdef", new String[] {"ab", "abc", "cd", "def", "abcd"});

        // constructor = new CanConstructWord();
        // constructor.canConstructWord("abcdef", new String[] {"ab", "abc", "ef", "abcd"});

        // constructor = new CanConstructWord();
        // constructor.canConstructWord("abef", new String[] {"ab", "abc", "bef", "def", "abcd"});

        // CountConstructWord counter = new CountConstructWord();
        // counter.countConstructWord("abcdef", new String[] {"ab", "abc", "cd", "def", "ef", "abcd"});

        // AllConstructWord all = new AllConstructWord();
        // all.allConstructWord("abcdef", new String[] {"ab", "abc", "cd", "def", "ef", "abcd"});

        // all = new AllConstructWord();
        // all.allConstructWord("abcdef", new String[] {"ab", "cd", "def", "abcd"});
        return;
    }
}
