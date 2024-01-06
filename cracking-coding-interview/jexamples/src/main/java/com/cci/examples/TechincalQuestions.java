package com.cci.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TechincalQuestions {

    public static class CountArrayPairs {
        /**
         * For a list with unique numbers, find all pairs of that list which have a
         * difference of diff
         *
         * @param pairs - the list of pairs
         * @param diff  - the diff to find
         * @return - the number of pairs
         */
        public int countDiffPairs(List<Integer> pairs, int diff) {
            // One approach mgiht be to sort the array, and then do a lookup with binary
            // search
            // which is log(n) time, basically a constant lookup time
            // pairs.sort((Integer o1, Integer o2) -> {
            // return o1 - o2;
            // });

            // Another approach might be to just put these numbers in a lookup table, and
            // simply
            // have a look at the table, we use this approach below, it is basically the
            // same
            // as far as big O complexity is concerned
            Map<Integer, Integer> allNumbersStore = new HashMap<>();
            for (Integer number : pairs) {
                allNumbersStore.put(number, number);
            }

            Integer count = 0;
            for (Integer leftPairNumber : pairs) {
                // we know the left side of the equasion so we can re-arrange the expression
                // such that leftPairNumber + diff = rightPairNumber, then search for it
                Integer rightPairNumber = leftPairNumber + diff;

                // check if the number was present in the array
                if (allNumbersStore.containsKey(rightPairNumber)) {
                    System.out.println(String.format("%d %d", leftPairNumber, rightPairNumber));
                    // increment the total pair count, this should produce unique pairs, since the
                    // array contains only unique numbers
                    count++;
                }
            }
            // return the count back for validation, print the information to the user
            System.out.println(String.format("Number of pairs: %d ", count));
            return count;
        }
    }

    public static class CubicSumExpression {

        /**
         * Count all solutions to the a^3 + b^3 = c^3 + d^3. What we need to realize
         * there is that this is a simple sum problem, meaning that a,b,c,d must be
         *
         * @param n [TODO:parameter]
         */
        public int countAllSolutions(int n) {
            // We try to collect all sums, of the expression m^3 + n^3, this
            // will give us the sums. In addition to that we map each unique sum
            // to the result of the expression above. This would give all pairs
            // of numbers unique or not, which sum to the same sum.
            Map<Integer, List<String>> allSumStore = new HashMap<>();
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    Integer totalSum = (int) (Math.pow(i, 3) + Math.pow(j, 3));
                    // if the sum already is not present add a new empty list
                    // the sum is integer so it will be unique.
                    if (!allSumStore.containsKey(totalSum)) {
                        allSumStore.put(totalSum, new ArrayList<>());
                    }
                    // append to the list of the mapping for totalSum. The pair
                    // below is the pair of m and n both raised to the 3rd power
                    // summed together
                    allSumStore.get(totalSum).add(String.format("[m=%s,n=%s]", i, j));
                }
            }

            // the final result will contain a list of pairs, for each sum which give us
            // the sum in question, most numbers (up to 1000) have pretty much at most
            // one pair which sums up to e.g. - 737: [[2,9], [9,2]]
            for (Map.Entry<Integer, List<String>> entry : allSumStore.entrySet()) {
                System.out.println(String.format("%d: %s", entry.getKey(), entry.getValue()));
                System.out.println("-----------------------------------------------------");
            }
            return n;
        }
    }

    public static class PermutationWithinString {

        public int describePermutationPositions(String longString, String shortString) {
            // sort the input, to make sure it is normalized the same way the window
            // string below will be, this we will make use in equalsIgnoreCase
            shortString = shortString.chars().sorted().collect(StringBuilder::new,
                    StringBuilder::appendCodePoint,
                    StringBuilder::append).toString();

            List<String> listOfMatches = new ArrayList<>();
            for (int i = 0; i < longString.length(); i++) {
                // take a window of word of at most shortString.length chars, to check if it is
                // part of the long string
                String windowString = longString.substring(i, Math.min(longString.length(), i + shortString.length()));

                // unscrable the word window, of shortString.length chars, into
                // a predictable ordered sequence of chars this way we can be
                // sure that whatever the permutation in windowString is, it
                // will be normalized, by sorting it, to be ready for comparison
                // with shortString
                String sortedString = windowString.chars().sorted().collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append).toString();

                // compare against the short string, which is also sorted
                if (shortString.equalsIgnoreCase(sortedString)) {
                    listOfMatches.add(windowString);
                }
            }

            // print the results and return
            System.out.println(listOfMatches);
            return listOfMatches.size();
        }
    }

    public static class BestConceavableRuntime {

        public int uniquePairsSum(List<Integer> input, int sum) {
            // first we sort the array of unique numbers, this would allow us to
            // do a quick binary search over the entire array
            input = input.stream().sorted().collect(Collectors.toList());
            Object[] entries = input.toArray();

            List<String> results = new ArrayList<>();
            for (Integer entry : input) {
                // we know what value we are looking for, since we know the sum,
                // and the current value, therefore x + current = sum, or x =
                // sum - current we can lookup the value of x, if it is truly
                // present in the array
                Integer lookup = sum - entry;
                int found = Arrays.binarySearch(entries, lookup);
                if (found != -1) {
                    // format and add the pair of values that sum up to the given sum.
                    results.add(String.format("%d + %d = %d", entry, lookup, sum));
                }
            }

            // print the results and return
            System.out.println(results);
            return results.size();
        }

        public int findCommonElements(List<Integer> first, List<Integer> second) {
            // by the task definition this is true
            assert (first.size() == second.size());

            // a generally good solution, given that both are sorted is to just go
            // through the array and do binary search in the second, looking for
            // each of the values from the first, and that is quite good

            // a more optimal solution would be to stick the values from the first
            // array into a map, then iterate the second array and check if the
            // map contains them

            // most optimal solution since both arrays have the same size and they
            // are already sorted is to go through them at the same time, trying
            // to find current value from one in the second one

            int firstIterator = 0;
            int secondInterator = 0;
            List<String> results = new ArrayList<>();
            while (firstIterator < first.size() && secondInterator < second.size()) {
                // extract both elements, remember the arrays are of equal size
                // and are already sorted according to the definition of the
                // task
                Integer firstElement = first.get(firstIterator);
                Integer secondElement = second.get(secondInterator);

                if (firstElement.equals(secondElement)) {
                    // if both pointers point to the same integer value element, then move
                    // both forward and remember the value which was matching
                    firstIterator++;
                    secondInterator++;
                    results.add(String.format("%d", firstElement));
                }
                else if (firstElement < secondElement) {
                // the first element is smaller than the second therefore we move the pointer
                // of the first array forward, only
                    firstIterator++;
                } else {
                    // the second element is smaller than the first therefore we move the pointer
                    // of the second array forward, only
                    secondInterator++;
                }

            }

            // print the results and return
            System.out.println(results);
            return results.size();
        }
    }

    public static final class PermutateInputString {

        private List<String> permutateStringHelper(String input) {
            if (input.length() == 1) {
                // a string input with length 1, has no permutations
                return Arrays.asList(input);
            }
            // pull the last element from the input, and remember it
            String suffix = input.substring(input.length() - 1);

            // the new input string the original with the tail cut off
            String prefix = input.substring(0, input.length() - 1);

            // generate permutations for the substring and enrich each
            List<String> permutations = permutateStringHelper(prefix);

            // hold the actual final result here
            List<String> result = new ArrayList<>();

            // for each permutation of the smaller string, stick last element of
            // the original at both ends first, and then in between the string
            // too. thus the new string result will contain the last character
            // of the original in each position
            for (String perm : permutations) {
                // add at both ends of the permutated string
                result.add(perm + suffix);
                result.add(suffix + perm);

                // add it, in between the permutated strings
                for (int i = 0; i < perm.length() - 1; i++) {
                    String head = perm.substring(0, i + 1);
                    String tail = perm.substring(i + 1, perm.length());
                    result.add(head + suffix + tail);
                }
            }

            // return the result
            return result;
        }

        public int permutateGivenString(String input) {
            List<String> result = permutateStringHelper(input);
            System.out.println(result);
            return result.size();
        }
    }

    public static void main(String[] args) {
        // CountArrayPairs pairs = new CountArrayPairs();
        // pairs.countDiffPairs(Arrays.asList(1, 7, 5, 9 ,2 ,12, 3), 2);

        // CubicSumExpression sums = new CubicSumExpression();
        // sums.countAllSolutions(10);

        // PermutationWithinString perms = new PermutationWithinString();
        // perms.describePermutationPositions("cbabadcbbabbcbabaabccbabc", "abbc");

        // PermutateInputString input = new PermutateInputString();
        // input.permutateGivenString("abcd");

        BestConceavableRuntime bcr = new BestConceavableRuntime();
        bcr.uniquePairsSum(Arrays.asList(0, 1, 2, 3, 4, 7, 10, 11, 12, 13, 15), 13);

        bcr.findCommonElements(Arrays.asList(13, 27, 35, 40, 49, 55, 59), Arrays.asList(17, 35, 39, 40, 55, 58, 60));
        return;
    }
}
