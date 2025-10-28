package com.cci.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ArraysAndStrings {

    /**
     * Find each position in a long string where any permutation of a shorter string is found
     *
     * The solution to this problem is to simply sort the the input short string, after that traverse the long string and take a sub string
     * as long as the input short string, sort it, and compare against the short string, if they match then that substring is a permutation
     * of the short string, repeat until the end of the string
     *
     */
    public static class PermutationWithinString {

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

                // unscamble the word window, of shortString.length chars, into
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
            return listOfMatches.size();
        }
    }

    /**
     * Check if a sequence of characters or a string contains only unique characters.
     *
     */
    public static final class UniqueSequenceChecker {

        public boolean isUniqueSequence(String input) {
            // another approach using intermediary structure is to get the distinct chars, in a map or a distinct stream like the example
            // below
            // String distinct = input.chars().distinct().
            // .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            // .toString();

            // sort the string, this can be done with quick sort, to avoid using intermediary structures
            String sorted = input.chars()
                            .sorted()
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();

            // one way to do it is to go through the sorted array and check if a there are any elements which are the same and are
            // sequential
            for (int i = 0; i < input.length() - 1; i++) {
                if (sorted.charAt(i) == sorted.charAt(i + 1)) {
                    // at this point we know that there are duplicate characters in the string
                    // therefore the string does not contain unique characters only
                    return false;
                }
            }
            return true;
        }
    }

    public static class StringCheckPermutation {

        /**
         * Check if a given string is a permutation of another string
         */
        private boolean checkStringPermuatation(String first, String second) {
            // early exit, we know that this can not be true if both have different lengths to begin with
            if (first.length() != second.length()) {
                return false;
            }

            // first collect all unique characters in a map, from the first string, this way we will have a count of all characters
            // contained in the string
            Map<Character, Integer> countMap = new HashMap<>();
            for (int i = 0; i < first.length(); i++) {
                if (!countMap.containsKey(first.charAt(i))) {
                    countMap.put(first.charAt(i), 1);
                } else {
                    Integer count = countMap.get(first.charAt(i));
                    countMap.put(first.charAt(i), count + 1);
                }
            }

            // use the count from the map and go through the second string, each time we meet a char that is present in the map that means
            // that it was present in the first string, decrement the count, if a characters exists in second string, but does not in the
            // first, then we can early exit and say that there is no possible way that the first string can be a permutation of the second
            for (int i = 0; i < second.length(); i++) {
                if (countMap.containsKey(second.charAt(i))) {
                    // decrement the count of each unique character, in the end we have to end up with all counts being 0, meaning that not
                    // only the same chars were present in the second string but also the same number of them as well
                    Integer count = countMap.get(second.charAt(i));
                    if (count == 1) {
                        countMap.remove(second.charAt(i));
                    } else {
                        countMap.put(second.charAt(i), count - 1);
                    }
                } else {
                    return false;
                }
            }

            // check if the count map is empty, if yes, then that means we have removed all characters exactly count times, therefore both
            // strings are permutations of one another
            return countMap.isEmpty();
        }
    }

    /**
     * Convert all spaces in a url string wtih %20
     *
     */
    public static final class ConvertUrlSpace {

        public String convertUrlSpaces(String url, int spaces) {
            if (spaces == 0 || url.length() == 0) {
                return url;
            }
            char[] chars = url.toCharArray();

            // the task requires us to use a char array, which alrady contains the space at the end of the array for the extra characters,
            // to avoid messy calls in tests, convert the input to char array in here and pad it with extra space
            chars = Arrays.copyOf(chars, url.length() + ((spaces * 3) - spaces));

            int i = url.length() - 1;
            int j = chars.length - 1;

            // we have the total number of elements the string would otherwise take, when each space is converted to %20, start from the end
            // boundary of that [j], the other string pointer starts from the end of the original string [i]. Then move / copy char by char,
            // the ones that are not spaces and move i and j together, when a space is reached, we move i by one, by j by 3, because we
            // store at positions j up to j - 2 the %20. The important thing here to realize is that i will always be ahead or at the same
            // position as j, which means that going backwards we will never override or lose data or characters
            while (i >= 0 && j >= 0) {
                if (chars[i] != ' ') {
                    // just copy the element, from / at the pointer positions
                    chars[j] = chars[i];
                    // move both pointers back, we are at a non-space character
                    i--;
                    j--;
                } else {
                    // we are at a space character, the next 3 chars starting from j will be %20, move j back by 3
                    chars[j - 0] = '0';
                    chars[j - 1] = '2';
                    chars[j - 2] = '%';
                    i--;
                    j -= 3;
                }
            }

            return new String(chars);
        }
    }

    public static final class PalindromePermutationChecker {

        /**
         * Check if a given string can be considered a palindrome. Meaning that it has certain permutations of its characters, where it can
         * be read from back to front, the same as in front to back. For example the char sequence oonn - has a permutation noon, which is a
         * palindrom
         *
         * @param input - the sequence to check, for palindromeness
         * @return - true if it has palindrom permutations, false otherwise
         */
        public boolean checkPalindromePermutation(String input) {
            // string of input one, has no permutations or we can say the string is it's own permutation and the string is a palindrome too
            if (input.length() == 1) {
                return true;
            }

            // how to approach this problem, we know that a palindrom must have an even number of characters appearing in it, the only way
            // it can be a plindrome is if the same number of characters appear in the "left" and "right" halfs. This means that if the
            // input string's lendth is even i.e has an even number of characters, then all characters must appear even number of times, 2,
            // 4, 6, etc. If the input string length is not even, then at most one character from those can have a non even count.

            // count all char occurances, and store them in the map
            Map<Character, Integer> countMap = new HashMap<>();
            for (int i = 0; i < input.length(); i++) {
                if (!countMap.containsKey(input.charAt(i))) {
                    countMap.put(input.charAt(i), 1);
                } else {
                    Integer count = countMap.get(input.charAt(i));
                    countMap.put(input.charAt(i), count + 1);
                }
            }

            // check how many non even characters are there hold them in the count, we do not care about the even characters
            // but we must go through and check all the characters found in the original string
            int counter = 0;
            for (var entry : countMap.entrySet()) {
                if (entry.getValue() % 2 != 0) {
                    counter++;
                }
            }

            // when the input length is even, we can not have non-even count of characters, when the input length is non-even, at most one
            // character can have a non-even count in the original string, below we check for exactly those conditions.
            boolean palindrome = false;
            if (input.length() % 2 == 0) {
                palindrome = counter == 0;
            } else {
                palindrome = counter == 1;
            }

            return palindrome;
        }
    }

    /**
     * Check if the operation on one string is one edit away, where an edit is either a replace, insert or remove of a character
     *
     */
    public static final class ComputeEditAction {

        public int checkInsertRemove(String left, String right) {
            if (left.length() == right.length()) {
                return 0;
            }

            int count = 0;
            int maxPtr = 0;
            int minPtr = 0;

            int maxIn = Math.max(left.length(), right.length());
            int minIn = Math.min(left.length(), right.length());

            String maxStr = left.length() == maxIn ? left : right;
            String minStr = left.length() == minIn ? left : right;

            int diff = left.length() - right.length();
            while (maxPtr < maxIn && minPtr < minIn) {
                if (minStr.charAt(minPtr) != maxStr.charAt(maxPtr)) {
                    count++;
                    maxPtr++;
                    continue;
                }
                maxPtr++;
                minPtr++;
            }
            return count;
        }

        public int checkReplaceOnly(String left, String right) {
            if (left.length() != right.length()) {
                return 0;
            }

            int count = 0;
            for (int i = 0; i < left.length(); i++) {
                if (left.charAt(i) != right.charAt(i)) {
                    count++;
                }
            }
            return count;
        }

        public boolean oneEditAway(String left, String right) {
            int insertOrRemove = checkInsertRemove(left, right);
            int replaceOnly = checkReplaceOnly(left, right);
            return insertOrRemove + replaceOnly <= 1;
        }
    }

    /**
     * Given a string of characters, write a compression algorithm that converts any sequential characters in the count they represent, i.e
     * aaabbcc would become a3b2c2
     *
     */
    public static final class StringCompressionConvert {

        public String compressInputString(String input) {
            int count = 1;
            char curr = input.charAt(0);
            StringBuilder result = new StringBuilder();

            // starting from the second element, at index 1, since the first one is already used as initalizing condition, test each
            // consequtive char, increment the count, when we reach s[i] != curr, where curr was the char form the last iteration, dump the
            // current char and it's count to the result, rest the count, and continue.
            for (int i = 1; i < input.length(); i++) {
                if (curr == input.charAt(i)) {
                    count++;
                } else {
                    result.append(curr);
                    result.append(count);
                    count = 1;
                }
                curr = input.charAt(i);
            }

            // there is a gotcha here, the last character/s, will not be appended, imagine this string has only one char, loop will not
            // enter and char+count would not be generated, same issue would happen when we reach the end of the array, with the last char,
            // be it a repeating one, or not, thus manually append the last known value of curr and count.
            result.append(curr);
            result.append(count);


            // according to the task, the compressed string has to be smaller, i.e contain less characters than the input to be considered a
            // valid compression, if that is not the case return the originl, else return the compressed string
            if (result.length() >= input.length()) {
                return input;
            } else {
                return result.toString();
            }
        }
    }

    /**
     * Given a matrix of size m x n, set each row and col to 0, where an element was found to be 0 at [i][j].
     *
     */
    public static final class ZeroMatrixData {

        public int zeroRowCols(int matrix[][]) {
            int size = matrix[0].length;
            List<Integer> rows = new ArrayList<>();
            List<Integer> cols = new ArrayList<>();

            // go through all elements and remember the cols and rows that they occupy, the caveat here is to first find the cols and rows,
            // before starting to zero them out, if instead we directly try to zero out the first elements we find to be zero, we would
            // totally mess up the matrix, and further checks for 0 would be wrong.
            for (int i = 0; i < matrix.length; i++) {
                assert (size == matrix[i].length);
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] == 0) {
                        rows.add(i);
                        cols.add(j);
                    }
                }
            }

            // zero out the rows
            for (var r : rows) {
                for (int i = 0; i < size; i++) {
                    matrix[r][i] = 0;
                }
            }

            // zero out the cols
            for (var c : cols) {
                for (int i = 0; i < matrix.length; i++) {
                    matrix[i][c] = 0;
                }
            }

            // print the resultling matrix
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.print(String.format("[%d] ", matrix[i][j]));
                }
                System.out.println();
            }

            // return the number of initial 0 elements
            return (rows.size() + cols.size()) / 2;
        }
    }

    /**
     * Check if a given string is a rotation of another string, a rotated string is such that the first characteres of it are moved to the
     * end of the string.
     *
     */
    public static final class FindRotatedString {

        public boolean isRotatedString(String orig, String rotated) {
            int i = 0;
            int j = 0;

            int loops = 0;
            int count = 0;

            // loop over the original string, and and check how many consequtive characters from the original match with the
            // rotated string, the caveat here is to just loop around the original, using mod, to make sure all characters
            // are inspected in the correct order. (we do simple wrap around)
            while (i < orig.length()) {
                // if two loops over the rotated string were made, and we still have not found the original string, then we can bail out,
                // there is no match
                if (loops > 2) {
                    break;
                }

                // each time a full loop is made, account for it, a loop is made when the next j would become greater than the original
                // length, and a wrap around would occur
                loops += (j + 1) / orig.length();

                // check against the current pos in original and rotated, if not equal move the orig string pointer j
                // forward, accounting for index loop around, using mod
                if (orig.charAt(i) != rotated.charAt(j)) {
                    // when count is already bigger than 1
                    if (count > 1) {
                        // this case is mandatory to handle, since we have already started 'counting' and we encounter a non-matching
                        // character, that means that the string partially matched, but not the entire sub-string, we bail here
                        break;
                    }
                    j = (j + 1) % orig.length();
                    continue;
                }

                // both chars at the given position match, increment the count, and move both pointers forward, at some
                // point if count == orig.length == rotated.length we know that the rotation is valid
                j = (j + 1) % rotated.length();
                i++;
                count++;
            }

            // reaching this point here if the count is exactly the length of the original sub-string, that means the rotated string
            // contained the orig as a sub-string, somewhere in its representation
            return count == orig.length();
        }
    }

    /**
     * Generate a new array where each element is the product of all other elements in the source array, but the current one
     *
     */
    public static final class ProductOfAllElements {

        public int[] createProductsArray(int[] input) {
            int[] products = new int[input.length];

            int total = 1;
            // compute the product of all array elements first, we assume the array has no 0 elements, we are going to use
            // this product to later on divide it by each element in the array to effectively 'remove' that specific element
            // from the final product
            for (int i = 0; i < input.length; i++) {
                total *= input[i];
            }

            // for each element simply divide the product by the current element, assume array has no dups
            for (int i = 0; i < products.length; i++) {
                products[i] = total / input[i];
            }

            // return the final array which has the products such that each element is only the product of all other
            // elements but the current one from the original array
            return products;
        }
    }

    /**
     * Find out the Nth max element in an array of integers
     *
     */
    public static final class NthMaximumElement {

        public int findNthMaximum(int[] input, int n) {
            // holds the n number of max elements in the array
            Stack<Integer> stack = new Stack<>();
            stack.push(Integer.MIN_VALUE);

            // to simplify the task we simply push into a stack of integers, where the top of the stack always contains the biggest element so far
            for (int i = 1; i < input.length; i++) {
                // it might be good idea to stop after n max elements were already found, there is no reason to continue
                if (stack.size() > n) {
                    break;
                }
                int top = stack.peek();
                // when the top of the stack is smaller than the current element, push the current element onto the
                // stack
                if (top < input[i]) {
                    stack.push(input[i]);
                }
            }

            // at this point we will know if we have enough 'max' elements pushed into the stack, if we do then we can
            // simply pop that many elements from the stack to get the n-th max one. Note comparison against size > n, since
            // the stack always has at least one element - Integer.MIN_VALUE
            if (stack.size() > n) {
                // pop that many elements such that the n-th one ends up at the top of the stack, so we can get it
                for (int i = 0; i < n - 1; i++) {
                    stack.pop();
                }
                // the top of the stack will contain the n-th max element at this point, after the n - 1 pops
                return stack.pop();
            } else {
                // we have not found at least n max elements, we can return 0, or some fallback value here
                return 0;
            }
        }
    }

    /**
     * Find the minimal length of a subarray which sums to a given target sum, from an array of positive integers
     *
     */
    public static final class MinimumSubArraySum {

        public int findSubArraySum(int[] input, int sum) {
            // start with placeholder value for the max length
            int result = Integer.MAX_VALUE;

            // we have to inspect all possible lengths for each element of the array, for the first, second up to the n-th element.
            for (int j = 0; j < input.length; j++) {
                // start off from the current element, including the current length of the sub-array sum and accumulator
                int len = 1;
                int acc = input[j];
                int min = Integer.MAX_VALUE;

                // we calculate the minimum length of the sub-array starting from j + 1, which is going to give us
                // number bigger or equal to the target sum
                for (int i = j + 1; i < input.length; i++) {
                    if (acc >= sum) {
                        // at this point we have found the min length, going further will not help, this array
                        // holds only positive integers, the length will only grow
                        min = Math.min(min, len);
                        break;
                    }
                    // accumulate the total sum currently found
                    acc += input[i];
                    len++;
                }

                // after each sub-array min length sum, compare to the one currently considered the minimum one.
                result = Math.min(result, min);
            }

            // at this point the result will hold the minimum length of whichever sub-array sumed to target with the least
            // amount of elements
            return result;
        }
    }

    /**
     * Remove all duplicate elements from a sorted array of integer elements
     *
     */
    public static final class RemoveDuplicateElements {

        public int[] removeDuplicateElements(int[] input) {
            int s = 0; // pointer into next non duplicate idx
            for (int i = 0; i < input.length - 1; i++) {
                if (input[i] == input[i + 1]) {
                    // go forward when visiting duplicate elements
                    continue;
                } else {
                    // when the next element is different, put it
                    // at the last known non-duplicate pointer pos
                    // and increment the pointer forward
                    input[s + 1] = input[i + 1];
                    s = s + 1;
                }
            }
            // at this point s will point at the last index which
            // was inserted, meaning we can simply add one to it
            // to get the length of the array with unique elements
            return Arrays.copyOf(input, s + 1);
        }
    }

    /**
     * Find which elements, sum up to a given target integer, store out their indices.
     *
     */
    public static final class TwoElementsSums {

        public int[] sumElementsToTarget(int[] input, int target) {
            List<Integer> pairs = new LinkedList<>();
            for (int i = 0; i < input.length; i++) {
                // since we have the target sum, and the current element
                // we can find the second element we need, by subtracting
                // the current element from the target sum
                int result = target - input[i];

                // the result is the element we are going to look for in
                // the sub-array from i - length. We also assume that
                // a pair should exist if the target == input[i], we should
                // look for an element with a value of 0 in the array
                for (int j = i + 1; j < input.length; j++) {
                    if (result == input[j]) {
                        // add the pairs of elements which sum up to target
                        // note that we assume the array does not contain
                        // duplicates, for simplicty, break
                        pairs.add(i);
                        pairs.add(j);
                        break;
                    }
                }
            }

            // return the array of pairs where each two indices are pairs
            return pairs.stream().mapToInt(Integer::intValue).toArray();
        }
    }

    /**
     * Move all zero elements of the array at the end of the array
     *
     */
    public static final class MoveZeroElements {

        public int[] moveZeroElements(int[] input) {
            int s = 0; // pointer into last non zero element in the array
            for (int i = 0; i < input.length - 1; i++) {
                if (input[i] == 0 && input[i + 1] != 0) {
                    // when the current elemnet is a 0 and the next one is not,
                    // swap with the 0th element, and move the current non-zero
                    // element's pointer 's' forward
                    input[s] = input[i + 1];
                    input[i + 1] = 0;
                    s += 1;
                } else if (input[i] != 0) {
                    // when the current element is not a 0, simply move the pointer
                    // forward, since it must not be moved from it's current position
                    s += 1;
                }
            }
            return input;
        }
    }

    /**
     * Rotate an array such that elements at index i go to index i + k.
     *
     */
    public static final class RotateArrayInPlace {

        private int[] reverseArrayElements(int[] input, int start, int end) {
            while (start <= end) {
                int tmp = input[start];
                input[start] = input[end];
                input[end] = tmp;
                start++;
                end--;
            }
            return input;
        }

        public int[] rotateArrayInPlace(int[] input, int k) {
            input = reverseArrayElements(input, 0, input.length - 1);
            input = reverseArrayElements(input, 0, k - 1);
            input = reverseArrayElements(input, k, input.length - 1);
            return input;
        }
    }

    public static void main(String[] args) {
        // UniqueSequenceChecker checker = new UniqueSequenceChecker();
        // checker.isUniqueSequence("ackwabcw");
        // checker.isUniqueSequence("acdtewqb");

        // StringCheckPermutation perm = new StringCheckPermutation();
        // perm.checkStringPermuatation("abcd", "bcda");
        // perm.checkStringPermuatation("abaac", "abaca");
        // perm.checkStringPermuatation("abaac", "baawc");

        // ConvertUrlSpace urler = new ConvertUrlSpace();
        // urler.convertUrlSpaces("Mr John Smith", 2);

        // PalindromePermutationChecker pali = new PalindromePermutationChecker();
        // pali.checkPalindromePermutation("tactcoa");
        // pali.checkPalindromePermutation("nana");
        // pali.checkPalindromePermutation("omm");
        // pali.checkPalindromePermutation("oonn");
        // pali.checkPalindromePermutation("rotor");
        // pali.checkPalindromePermutation("kekw");

        // ComputeEditAction edit = new ComputeEditAction();
        // edit.oneEditAway("pale", "ple");
        // edit.oneEditAway("pales", "pale");
        // edit.oneEditAway("pale", "bale");
        // edit.oneEditAway("pale", "bake");

        // StringCompressionConvert convert = new StringCompressionConvert();
        // convert.compressInputString("aabcccccaaa");
        // convert.compressInputString("abcd");
        // convert.compressInputString("a");

        // ZeroMatrixData matrix = new ZeroMatrixData();
        // int mat[][] = {{0,2,3}, {1,2,0}, {1,2,3}};
        // matrix.zeroRowCols(mat);

        // FindRotatedString rotate = new FindRotatedString();
        // rotate.isRotatedString("waterbottle", "erbottlewat");
        // rotate.isRotatedString("waterbottle", "erbottlevat");
        // rotate.isRotatedString("waterbottle", "erbottllewat");

        // ProductOfAllElements product = new ProductOfAllElements();
        // product.createProductsArray(new int[]{ 3, 1, 5 });
        // product.createProductsArray(new int[]{ 3, 6, 5 });
        // product.createProductsArray(new int[]{ 8, 2, 7 });

        // NthMaximumElement nth = new NthMaximumElement();
        // nth.findNthMaximum(new int[]{ 1, 5, 8, 3, 2, 9, 10 }, 3);
        // nth.findNthMaximum(new int[]{ 8, 9, 1, 2, 5 }, 1);
        // nth.findNthMaximum(new int[]{ 1, 5 }, 3);

        // MinimumSubArraySum max = new MinimumSubArraySum();
        // max.findSubArraySum(new int[] {3, 1, 5, 2, 2, 1, 1 }, 6);
        // max.findSubArraySum(new int[] {3, 1, 5, 2, 8, 10, 1 }, 11);
        // max.findSubArraySum(new int[] {3, 1, 5, 2, 8, 5, 1 }, 15);

        // RotateArrayInPlace rotate = new RotateArrayInPlace();
        // rotate.rotateArrayInPlace(new int[]{ 1, 2, 3, 4, 5, 6, 7, 8 }, 3);

        // RemoveDuplicateElements dups = new RemoveDuplicateElements();
        // dups.removeDuplicateElements(new int[]{ 0, 1, 1, 1, 2, 2, 3, 3, 4 });
        // dups.removeDuplicateElements(new int[]{ 0, 0, 1, 1, 1, 2, 2, 3, 3, 4, 5, 5 });

        // TwoElementsSums sums = new TwoElementsSums();
        // sums.sumElementsToTarget(new int[]{ 2, 7, 11, -6, 15, 9, 0 }, 9);

        // MoveZeroElements zeroes = new MoveZeroElements();
        // zeroes.moveZeroElements(new int[] { 0, 0, 1, 0, 3, 4, 0, 0, 5 });
        // zeroes.moveZeroElements(new int[] { 7, 9, 0, 1, 0, 0, 3, 4, 0, 0, 5 });
        return;
    }
}
