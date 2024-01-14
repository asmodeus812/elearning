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
brand new string which is a copy of the original with the mutation applied. Keep
that in mind and if the problem revolves around mutating a String, use the
correct data structure for the language, for java that would be StringBuilder.

1. Permutation of a string - the problem here is usually how to find all
   permutations of a given string, the solution is usually recursive, the way it
   works, is by cutting the last or the first character from a string, and
   generating all permutations for that reduced string. Drill down until the
   input string is of length 1, then in the post recursive calls, stuff the cut
   character in the end, mid and start of each permutated string in the list of
   permutations

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
