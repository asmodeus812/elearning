package com.cci.examples;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.StyledEditorKit.ForegroundAction;

public class ArraysAndStrings {

    public static final class UniqueSequenceChecker {

        /**
         * Check if a sequence of characters or a string contains only unique characters.
         *
         * @param input - the char sequence input
         * @return - true if unique, false otherwise
         */
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
                    System.out.println(String.format("Is %s not unique sequence", input));
                    return false;
                }
            }

            System.out.println(String.format("Is %s unique sequence", input));
            return true;
        }
    }


    public static class StringCheckPermutation {

        /**
         * Check if a given string is a permutation of another string
         *
         * @param first - a possible permutation of the source
         * @param second - a source string against to check
         * @return - true if permutatio, false otherwise
         */
        private boolean checkStringPermuatation(String first, String second) {
            if (first.length() != second.length()) {
                System.out.println(String.format("%s not a permutation of %s ", first, second));
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
                    countMap.put(second.charAt(i), count - 1);
                } else {
                    // early exit, we have hit a completely new character which was not in the first string, there is nothing more to do
                    System.out.println(String.format("%s not a permutation of %s ", first, second));
                    return false;
                }
            }

            // in the end we have to end up with all counts being 0, that would signal that the first is a permutation of the second one,
            // otherwise just return false and break out of the function
            for (var entry : countMap.entrySet()) {
                if (entry.getValue() != 0) {
                    System.out.println(String.format("%s not a permutation of %s ", first, second));
                    return false;
                }

            }

            // reaching this point means we have a valid permutation between the two inputs
            System.out.println(String.format("%s is a permutation of %s ", first, second));
            return true;
        }
    }

    public static final class ConvertUrlSpace {

        /**
         * Convert all spaces in a url string wtih %20
         *
         * @param url - the input string, for which all spaces must be converted
         * @param spaces - the initial number of spaces present in the input string
         * @return - the resulting string
         */
        public String convertUrlSpaces(String url, int spaces) {
            if (spaces == 0 || url.length() == 0) {
                System.out.println(String.format("Nothing to convert url: %s", url));
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

            String result = new String(chars);
            System.out.println(String.format("Converted url: %s", result));
            return result;
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

            System.out.println(String.format("String %s %s palindrome", input, palindrome ? "is" : "is not"));
            return palindrome;
        }
    }

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
            boolean distance = insertOrRemove + replaceOnly <= 1;

            System.out.println(String.format("String %s is %d edits away from %s", left, insertOrRemove + replaceOnly, right));
            return distance;
        }
    }

    public static final class StringCompressionConvert {

        /**
         * Given a string of characters, write a compression algorithm that converts any sequential characters in the count they represent,
         * i.e aaabbcc would become a3b2c2
         *
         * @param input - the string to compress
         */
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
                System.out.println(String.format("%s string would not become smaller", input));
                return input;
            } else {
                String compressed = result.toString();
                System.out.println(String.format("Compressed string is %s", compressed));
                return compressed;
            }
        }
    }

    public static final class ZeroMatrixData {

        /**
         * Given a matrix of size m x n, set each row and col to 0, where an element was found to be 0 at [i][j].
         *
         * @param matrix - the input matrix
         * @return - the total num of 0 elements
         */
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

    public static final class FindRotatedString {

        /**
         * Check if a given string is a rotation of another string, a rotated string is such that the first characteres of it are moved to the end of the string.
         *
         * @param orig - the original string, to use as a template
         * @param rotated - the rotated to check against
         * @return - true if rotated, false otherwise
         */
        public boolean isRotatedString(String orig, String rotated) {
            if(orig.length() != rotated.length()) {
                System.out.println(String.format("The %s string is NOT a rotated string of %s", rotated, orig));
                return false;
            }

            int i = 0;
            int j = 0;

            int loops = 0;
            int count = 0;

            // loop over the rotate string, and and check how many consequtive characters from the original match with the
            // rotated string, the caveat here is to just loop around the original, using mod, to make sure all characters
            // are inspected in the correct order.
            while (i < orig.length()) {
                // check if j is at the end of it's pass, if so, then mark that one loop is completed
                if((j + 1) >= orig.length()) {
                    loops++;
                }
                // if j somehow loops around the original string two times, and nothing was found, then there is no chance the
                // string is a rotation
                if(loops >= 2) {
                    count = 0;
                    break;
                }
                // check against the current pos in original and rotated, if not equal move the orig string pointer j
                // forward, accounting for index loop around, using mod
                if(orig.charAt(j) != rotated.charAt(i)) {
                    j = (j + 1) % orig.length();
                    continue;
                }
                // both chars at the given position match, increment the count, and move both pointers forward, at some
                // point if count == orig.length == rotated.length we know that the rotation is valid
                j = (j + 1) % orig.length();
                i++;
                count++;
            }

            // counts are the same, since we found count number of sequential matches, therefore the rotated string is
            // indeed a rotated representation of the original, otherwise, count is not the same, therefore it is not
            // rotated representation of the orignal
            if(count == orig.length()) {
                System.out.println(String.format("The %s string is a rotated string of %s", rotated, orig));
            } else {
                System.out.println(String.format("The %s string is NOT a rotated string of %s", rotated, orig));
            }
            return count == orig.length();
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
        return;
    }
}
