# Introduction

One of the most used structures in programming problems, questions with strings
usually revolve around working with string permutations, or checking for sub
strings in a given string. Questions with arrays, revolve around traversing
arrays, maybe mutating them by shifting elements left or right to make space for
new elements or remove existing ones, sorting and searching is often also
performed on static or dynamic arrays more often than on structures like linked
lists for example.

# Strings

Most all languages that implement strings are providing immutable string
objects, what that means is that that each mutation on the string produces a
brand-new string, which is a copy of the original with the mutation applied. Keep
that in mind and if the problem revolves around mutating a String, use the
correct data structure for the language, for java that would be StringBuilder.

## Permutation of a string

The problem here is usually how to find all permutations of a given string, the
solution is usually recursive, the way it works, is by cutting the last or the
first character from a string, and generating all permutations for that reduced
string. Drill down until the input string is of length 1, then in the post
recursive calls, stuff the cut character in the end, mid and start of each
permutated string in the list of permutations

```java
    List<String> permutate(String input) {
        if (input.length() == 1) {
            // a string input with length 1, has no permutations
            return Arrays.asList(input);
        }
        // pull the last element from the input, and remember it
        String suffix = input.substring(input.length() - 1);

        // the new input string, is the original with the tail cut off
        String prefix = input.substring(0, input.length() - 1);

        // generate list of permutations for the new substring(n - 1)
        List<String> permutations = permutate(prefix);

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
```

## Substring permutation

Another interesting problem, which might look like a tough one is to check if a
long string contains a permutation of another shorter string within itself. What
we need to realize here is that we can 'undo' the permutation in a predictable
way, how ? Well by simply sorting the input short string. Then when we traverse
the longer string, we take a sub-string of it, as long as the short string, sort
that as well and we compare both, if they match then it is indeed a permutation.

This problem is an extension of the clasic sub-string problem where we are asked
to check if a given shorter string is a sub-string of another longer string.

Here instead of linear, the complexity is `O(n * slog(s))`. Where N is the length
of the long string, and S is the length of the short string. If we know that N
is sufficiently bigger than S then we can drop this non-dominant term S. Meaning
the complexity will converge to only O(N)

```java
int subpermutation(String longString, String shortString) {
    // sort the input, to make sure it is normalized the same way the window
    // string below will be, this we will make use in equalsIgnoreCase
    shortString = shortString.chars()
                    .sorted()
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

    List<String> listOfMatches = new ArrayList<>();
    for (int i = 0; i < longString.length(); i++) {
        // take a sub-string of the original just as long as the input short string, which we will sort below
        // account for the fact that the left over of long-string might be less than short-string length
        String windowString = longString.substring(i, Math.min(longString.length(), i + shortString.length()));

        // unscamble the sub-string, of shortString.length chars, into
        // a predictable ordered sequence of chars this way we can be
        // sure that whatever the permutation in windowString is, it
        // will be normalized, by sorting it, to be ready for comparison
        String sortedString = windowString.chars()
                        .sorted()
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        // compare against the short string, which is also sorted.
        if (shortString.equalsIgnoreCase(sortedString)) {
            listOfMatches.add(windowString);
        }
    }

    // return the number of matches
    return listOfMatches.size();
}
```

The catch in this problem is to realize that a permutation can be converted to a
normalized, predictable sequence, by sorting the items. And to also realize that
if the sub-string extracted from the long-string contains a character originally
not present in the input short-string, then the comparison would catch that and
correctly report that these two cannot be permutations of one-another

## Unique characters

Another problem in string space, might revolve around checking if a string has
only unique characters. This problem similarly to the one above, can be
normalized to make the task easier. Generally speaking when working with
strings, we should either think about 'sorting' the string or 'hashing' the
string.

To check if a string contains only unique characters we would simply sort the
string, and check if each pair of characters are different. The sorting will
position the characters such that if we have repeating ones they would be next
to one another, meaning that we can compare the `char[i] with char[i + 1]`. If
we traverse the entire string without finding a matching pair we can simply
conclude that the string contains only unique characters.

Another solution is to put all characters in a hash map, count each occurance
and check if we have a character that occurs more than once. This solution
trades in space, for time complexity.

```java
boolean unique(String input) {
    // sort the string, this can be done with quick sort, to avoid using intermediary structures, and wasting space,
    String sorted = input.chars()
                    .sorted()
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

    // one way to do it is to go through the sorted array and check if a there are any elements which are the same and are sequential
    for (int i = 0; i < input.length() - 1; i++) {
        if (sorted.charAt(i) == sorted.charAt(i + 1)) {
            // at this point we know that there are duplicate characters in the string
            // therefore the string does not contain unique characters only
            return false;
        }
    }
    return true;
}
```

## Permutation check

This is a common problem we already saw above, where we check if a given string is
a permutation of another. This is a more generalized solution. Which instead of
sorting (which will also work) we employ another approach, namely, storing one
string (char wise) into a hash map and traversing the other string.

The approach is simple, we store one of the string's characters in a hash map,
for each character is a key, and the value is the occurance count of that character.

Then we traverse the second string, we check if the current character is
contained in the hash map, if yes we subtract from the count, in the end if we
end up with and empty hash map or all characters have a count of 0, we know that
the same exact characters are contained in both strings, so that means that both
strings are permutations of the same characters. If while traversing the second
string we meet a character that is not contained in the hash map, we can early
exit because the second string contains a character originally not present in
the first string.

```java
boolean permutation(String first, String second) {
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

    // check if the count map is empty, if yes, then that means we have removed all characters exactly count times, therefore both strings are permutations of one another
    return countMap.isEmpty();
}
```

## Palindrome check

Yet another common problem is working with palindromes, words or strings which
are spelt backwards and forwards the same. A problem involving a palindrome
might ask of us to check if any permutations of a given string can produce a
palindrome.

To solve this issue we can simply take into account the fact that to have a
palindrome we must meet the following conditions

-   the input string is of even length - therefore each character MUST occur even
    number of times (e.g. `a a b b c c` -> `a b c c b a`)
-   the input string is of odd length - therefore only one character CAN occur
    odd number of times, the rest must be even (e.g. `a a b b c c c` -> `a b c c
c b a`)

To make this check easy we can simply put all characters in a map and count them
up, then we check if our string is of even length, all counts must be even,
otherwise we can allow for at most one and only one character to have odd number
of occurances in the string.

```java
boolean palindrome(String input) {
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

    // check how many non even characters are there hold them in the count, we do not care about the even characters, what we mean is that if we find 0 odd character occurances, therefore they are all even.
    int counter = 0;
    for (var entry : countMap.entrySet()) {
        // account only for the odd ones
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
```

## Wraparound substring

A modification of the traditional sub-string check, where we try to find if a
given sequence of characters is a sub-string inside another string, however
while they are sequential, some characters might wrap around to the start
of the string.

For example, say we are looking for the sub-string `waffle` inside the given
string `a waffle was found on the ground`. In this traditional example, the full
sub-string is found in the same sequence in the string starting at range /
positions with indices `[1-6]`.

However let us take the same string but do a slight 'rotation' on it, and now we
have `fle was found on the ground a wa` - The same sequence of `waffle` is still
present however part of it is at the end of the string, another part is at the
beginning.

Another example where the input string is the same length as the sub-string,
which is really just the same as the general case.

- `waterbottle` <-> `erbottlewat` - is contained completely, but wraps around
- `waterbottle` <-> `orbottlewat` - is not contained, a mismatching character

To solve this issue we have to simply modify the sub-string algorithm, instead
of looping through the string from start to finish, we loop through it with wrap
around. The main thing to consider is that we might end up looping infinitely,
to stop that we have to keep track of how many times we loop through the source
string, the one inside which we are looking for a sub-string match. Worse
case scenario is we loop through it at most 2 times, which is O(2\*N) which is
O(N), we can drop the constants.

```java
public boolean substrting(String orig, String rotated) {
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
```

Since we are looking for a sequential sub-string, the moment we start
incrementing the count, and we notice a non-matching character, we can early
bail out, the sub-string sequence is broken, i.e. not contained.

# Arrays
