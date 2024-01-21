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

## Sub

# Lists

List interweaving, a very important to understand algorithm, which weaves two
lists into each other, effectively creating all possible permutations between
the elements of two lists. However the relevant positions or order of elements
in each list is retained the same, i.e. if one of the list is {1,2}, the order
of elements after the weaving, will be such that 1 will always come before 2, no
matter where the 1 and 2 are in the final result / weaved list

```java
    void weave(LinkedList<Integer> first, LinkedList<Integer> second, LinkedList<Integer> prefix, List<LinkedList<Integer>> result) {
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

        // remove the front element from the first list
        Integer element = first.removeFirst();
        // add that element to the tail of the prefix list
        prefix.addLast(element);
        // drill down, depth first, into the first list first
        weave(first, second, prefix, result);
        // remove the last inserted element to the prefix
        prefix.removeLast();
        // restore the element back into the source list
        first.addFirst(element);

        // remove the front element from the second list
        element = second.removeFirst();
        // add that element to the tail of the prefix list
        prefix.addLast(element);
        // drill down, depth first, into the second list second
        weave(first, second, prefix, result);
        // remove the last inserted element to the prefix
        prefix.removeLast();
        // restore the element back into the source list
        second.addFirst(element);
    }
```

How does it work, we have to take a look at an example, lets take the following
two lists {1,2} and {3,4}, and represent the recursive function calls in a stack
order

The way this flow works, is by first exhausting the first list, down to the very
last element, put everything into the prefix list, and append in the base case,
now the recursive stack starts to unwind, elements are returned to the first
list, one by one, and for each, we go depth first into the recurisive calls for
the second list. Let us take the example {1,2} & {3,4}

```txt
    weave({1,2}, {3,4}, {}, {})
    weave({2}, {3,4}, {1}, {})
    weave({}, {3,4}, {1,2}, {})
            1,2,3,4
        weave({2}, {4}, {1,3}, {})
        weave({2}, {}, {1,3,4}, {})
            1,3,4,2
```
