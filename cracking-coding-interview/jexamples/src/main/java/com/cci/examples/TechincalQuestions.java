package com.cci.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TechincalQuestions {

    public static class CountArrayPairs {

        /**
         * For a list with unique numbers, find all pairs of that list which have a difference of diff
         *
         * @param pairs - the list of pairs
         * @param diff - the diff to find
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
         * Count all solutions to the a^3 + b^3 = c^3 + d^3. What we need to realize there is that this is a simple sum problem, meaning
         * that a,b,c,d must be
         *
         * @param n - the upper boundary for which a,b,c and d are to be cubed
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

        /**
         * Find each position in a long string where any permutation of shortString is found
         *
         * @param longString - a very long string
         * @param shortString - a short string
         * @return - number of valid permutations of short string found in the long string
         */
        public int describePermutationPositions(String longString, String shortString) {
            // sort the input, to make sure it is normalized the same way the window
            // string below will be, this we will make use in equalsIgnoreCase
            shortString = shortString.chars()
                            .sorted()
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();

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
                String sortedString = windowString.chars()
                                .sorted()
                                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                .toString();

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

        /**
         * Find all pairs in an array which sum up to a specific given sum
         *
         * @param input wa- the array of elements
         * @param sum - the sum between two pairs to find
         * @return - number of pairs which match sum
         */
        public int uniquePairsSum(List<Integer> input, int sum) {
            // first we sort the array of unique numbers, this would allow us to
            // do a quick binary search over the entire array
            input = input.stream().sorted().collect(Collectors.toList());
            Object[] entries = input.toArray();

            List<String> results = new ArrayList<>();
            for (Integer entry : input) {
                // we know what value we are looking for, since we know the sum,
                // and the current value, therefore x + current = sum, or x =
                // sum - current, we can lookup the value of x, if it is truly
                // present in the array
                Integer lookup = sum - entry;
                int found = Arrays.binarySearch(entries, lookup);
                if (found != -1) {
                    // the value of the lookup was present in the array, add it
                    results.add(String.format("%d + %d = %d", entry, lookup, sum));
                }
            }

            // print the results and return
            System.out.println(results);
            return results.size();
        }

        /**
         * Find all common elements between two arrays, which are of the same size, number of elements, and are already sorted in the same
         * order
         *
         * @param first - the first array
         * @param second - the seond array
         * @return - number of matching elements between both arrays
         */
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
                } else if (firstElement < secondElement) {
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

    /**
     * Find the permutations of the input string. The number of permutations, is n! (n factorial), where n is the size or length of the
     * string. This is achieved by cutting the last character of the source string, the generating recursive permutations for the cut part,
     * until the cut part becomes of length 1.
     *
     */
    public static final class PermutateInputString {

        private List<String> permutateStringHelper(String input) {
            if (input.length() == 1) {
                // a string input with length 1, has no permutations
                return Arrays.asList(input);
            }
            // pull the last element from the input, and remember it
            String suffix = input.substring(input.length() - 1);

            // the new input string, is the original with the tail cut off
            String prefix = input.substring(0, input.length() - 1);

            // generate list of permutations for the new substring(n - 1)
            List<String> permutations = permutateStringHelper(prefix);

            // we hold the actual final result here
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

    /**
     * Weave the elements of two lists, this process very similar to permutation of a string, weaves two lists into each other, while
     * keeping the relative element order of the elements from each list, meaning that the elements of each list are still in the same order
     * in the weaved permutations as they were in the original array they came from i.e. for {1,2} & {3,4} - {1,3,4,2} {3,4,1,2}, {1,3,2,4}
     * etc. This is achieved by moving the contents of each list into a special prefix list, until the list is empty, generating the weave
     *
     */
    public static final class WeaveTwoLists {

        private void listWeaverHelper(LinkedList<Integer> first, LinkedList<Integer> second, LinkedList<Integer> prefix,
                        List<LinkedList<Integer>> result) {

            if (first.isEmpty() || second.isEmpty()) {
                // in the case where one of the source lists are emtpy, meaning elements were moved to the prefix list, clone the prefix and
                // add to that the first and second lists, the prefix at any given time would contain elements from one, or both arrays, in
                // different order, i.e first elements from first array, then second, or vice-versa, but not mess the order of the elements
                // that come from the same array.
                LinkedList<Integer> cloned = (LinkedList<Integer>) prefix.clone();
                cloned.addAll(first);
                cloned.addAll(second);
                result.add(cloned);
                return;
            }

            // The way this flow works, is by first exhausting the first list, down to the very last element, put everything into the prefix
            // list, and append in the base case, now the recursive stack starts to unwind, elements are returned to the first list, one by
            // one, and for each, we go depth first into the recurisive calls for the second list. Let us take the example {1,2} & {3,4}

            // remove the front element from the first list
            Integer element = first.removeFirst();
            // add that element to the tail of the prefix list
            prefix.addLast(element);
            // drill down, depth first, into the first list first
            listWeaverHelper(first, second, prefix, result);
            // remove the last inserted element to the prefix
            prefix.removeLast();
            // restore the element back into the source list
            first.addFirst(element);

            // remove the front element from the second list
            element = second.removeFirst();
            // add that element to the tail of the prefix list
            prefix.addLast(element);
            // drill down, depth first, into the second list second
            listWeaverHelper(first, second, prefix, result);
            // remove the last inserted element to the prefix
            prefix.removeLast();
            // restore the element back into the source list
            second.addFirst(element);
        }

        public List<LinkedList<Integer>> performListWeave(List<Integer> first, List<Integer> second) {
            List<LinkedList<Integer>> result = new LinkedList<>();
            LinkedList<Integer> one = new LinkedList<>(first);
            LinkedList<Integer> two = new LinkedList<>(second);
            listWeaverHelper(one, two, new LinkedList<>(), result);
            return result;
        }
    }

    public static final class StringNumberConverter {

        private static final Map<Character, Integer> hexNumberMapping;

        static {
            hexNumberMapping = new HashMap<>();
            hexNumberMapping.put('A', 10);
            hexNumberMapping.put('B', 11);
            hexNumberMapping.put('C', 12);
            hexNumberMapping.put('D', 13);
            hexNumberMapping.put('E', 14);
            hexNumberMapping.put('F', 15);
        }

        /**
         * Convert any string of valid numbers / digits and given base into a number in base 10
         *
         * @param number - the number, string of valid digits
         * @param base - the base of the number (up to base 16)
         * @return - the number in base 10
         */
        public long convertStringTo(String number, int base) {
            if (number.length() == 0 || base > 16) {
                return -1;
            }

            long value = 0;
            int size = number.length();

            // loop the string backwardds, since numbers in bases, are usually
            // defined from left to right, direction being most significant to
            // least significant digit.
            for (int i = size - 1; i >= 0; i--) {
                char num = number.charAt(i);
                int n = 0;
                if (base == 16 && hexNumberMapping.containsKey(num)) {
                    // try to convert the hex letter values to numbers usually
                    // from A to F which map to values 10 to 15
                    n = hexNumberMapping.get(num);
                } else {
                    // this might fail but it is okay, we do not care anything
                    // invalid should fail
                    n = Integer.parseInt(String.valueOf(num));
                }
                // the exponent is the position in the string, since the
                // iteration is backwards, invert the value of i
                value += n * Math.pow(base, size - (i + 1));
            }

            // print the result value
            System.out.println(value);
            return value;
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

        // BestConceavableRuntime bcr = new BestConceavableRuntime();
        // bcr.uniquePairsSum(Arrays.asList(0, 1, 2, 3, 4, 7, 10, 11, 12, 13, 15), 13);
        // bcr.findCommonElements(Arrays.asList(13, 27, 35, 40, 49, 55, 59), Arrays.asList(17, 35, 39, 40, 55, 58, 60));

        // StringNumberConverter converter = new StringNumberConverter();
        // converter.convertStringTo("110110", 2);
        // converter.convertStringTo("FA10", 16);
        // converter.convertStringTo("FF", 16);
        // converter.convertStringTo("150", 10);
        // converter.convertStringTo("73", 8);

        // WeaveTwoLists weaver = new WeaveTwoLists();
        // weaver.performListWeave(Arrays.asList(1, 2), Arrays.asList(3, 4));

        return;
    }
}
