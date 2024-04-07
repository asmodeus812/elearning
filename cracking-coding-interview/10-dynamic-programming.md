# Introduction

Dynamic programming problems usually are all about finding a non analytical
solutions to problems. What does that mean ? Well with most other algorithms we
find at most one single solution to a specific problem - sorting an array,
finding an element in an array, swapping array elements, finding tree nodes by
value, on and on. We can see how many of those problems often have one
analytical solution and that is it. However dynamic programming problems are all
about finding `all` possible solutions to a given problem. Or in other words,
dynamic programming is involved when a solution does not have one analytical
solution, or one solution only is not possible

# Principles

The major principles here are as follows

## Approach

-   recursion - very often the most dynamic programming problems are solved by
    leveraging recursion, since it is an easy way to explore many permutations of a
    given set of inputs, it is very useful if we want to solve the problem in a top
    down approach, where the recursion calls will drill down from top to the bottom,
    base cases first, and then ascend the recursive call stack.

-   iterative - when we have some idea of how to build off of the base cases, or
    if it makes more sense to use the base cases to accumulate them to compute
    further

## Types

-   bottom up - this is when we start the problem solving from the base case,
    for example to solve the Fibonacci sequence we will start from the two base
    cases, in this case the first two numbers of the Fibonacci sequence are `1 and
    1`, from this we built the solution from `the bottom to the top`
-   top down - this is the inverse of the bottom up approach where we build the
    solution by starting with some final `end paramter`, and is mostly used with
    recursive approach, where the recursive calls drill down to the base cases
    first, then ascend the call stack. Using Fibonacci as an example, we start with
    the target number N from the sequence and drill down to N - 1 and N - 2 and so
    on, until we reach the base cases for `N = 1 and N = 2`

## Optimizations

-   memoization - very often when using the recursive approach, the same input
    might be visited in subsequent branches of the recursion, it is quite normal to
    store the result of this specific input and when / if it is visited again, to
    reuse the already computed value. When computing Fibonacci sequence, many of the
    numbers are going to be re-computed and overlapping in different branches of the
    recursive descent, we can remember them and re-use them

-   minimization - reducing the number of input arguments to our function, can
    greatly help reduce the space complexity of the call stack frames themselves.

# Problems

## Fibonacci sequence

One of the most prolific and common examples, to demonstrate what dynamic
programming is about, is the Fibonacci sequence. We want to generate the first N
numbers of the Fibonacci sequence, and we are given only the number N, the final
position / index of the number we are looking for of the sequence

The most common solution is to use dynamic programming, the approach is based on two approaches

-   recursively find the current Fibonacci number based on the previous ones.
-   memorization of the already computed numbers, so we do not recompute them again.

```java
static final class Fibonacci {

    final int[] MEMO = new int[100];

    Fibonacci() {
        // initialize the memoization array to be some invalid number that can not exist in a valid Fibonacci sequence, we are going to
        // use this as a flag to check if the number stored at this position or index is already computed
        Arrays.setAll(MEMO, operand -> -1);
    }

    int calculate(int n) {
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
        MEMO[n] = calculate(n - 1) + calculate(n - 1);

        // return the stored value at the position N, it contains the current value of the number at position N in the Fibonacci
        // sequence
        return MEMO[n];
    }
}
```

The complexity of this algorithm is defined for both time and space. For the non optimized version without memoization is as follows

-   Without memoization table, each number of the sequence has to be evaluated every time

```txt
              5
           /     \
         4        3
       /   \    /   \
     3      2  2     1
    / \
   2   1
```

    -   _time complexity_ - O(2^d) - this is defined as the depth of the tree
        formed by the recursive calls, technically we have exactly 1.6^d recursive calls
        but for simplicity we can say - 2^d. Therefore for the example above, the number
        of calls for N = 5, assuming 2^d, will be 2^5 = 32, however the exact calls are
        actually less, as mentioned, it is closer to 1.6, in this casethe calls are ~10.

    -   _space complexity_ - O(d) - the space complexity is based on the number
        of call stack frames occupied during execution, remember that we do not evaluate
        all numbers of the sequence at once, the recursive call stack is at most N deep,
        where N is the target number position from the fib sequence. Meaning we make at
        most N number of recursive calls for each given number from the sequence, where
        N is the position or the index of the number from the Fibonacci sequence.

-   With memoization table, which stores already computed numbers from the sequence, they are reused

```txt
              5
           /     \
         4        3
       /   \
     3      2
    / \
   2   1
```

    -   _time complexity_ - O(d) - with memoization each number of the sequence
        is calculated exactly once, since we have a top down approach, the recursive
        calls will dril down to N = 1 and N = 2, and calculate upwards, to the target N,
        meaning that older, smaller numbers will be cached in the memo table, and this
        is how we get O(n) complexity in run time

    -   _space complexity_ - O(d) - the space complexity is based on the number
        of call stack frames occupied during execution, this will not improve even with
        the memo table, since the recursive calls will still reach the very bottom, base
        case and then ascend, unwind the call stack, the depth of the stack will be N
        here too

We can clearly see from the call stack representation that a lot of the numbers
from the sequence are getting re-computed constantly, we it is easy to see that
they can be cached. For example given our implementation the first branch that
will be computed immediately is `5 -> 4 -> 3 -> ( 2 + 1 )`. Meaning that the
first number of the sequence that is going to be computed is `3` the next is
going to be `4`, the next `5` Since the recursive approach is top down, we first
drill down to the base case and then unwind the recursion, meaning we can easily
cache each value we find to re-use later on when the recursion stack unwinds.

To see why the space complexity is O(d) - we can see that once we reach the
bottom of the recursion in the following call path - `5 -> 4 -> 3 -> ( 2 + 1)`,
stack has at this point consists of 5 calls to the fib function. Then the
recursion stack unwinds, and it will never be deeper than N.

## Grid counter

Another common task is, given a grid of NxM cells. Where N is the number of
rows, and M is the number of cols, find all possible ways to get from the top
left corner (start) to the bot right one (end).

This one, similarly to Fibonacci, is also leveraging recursion and memoization,
in this task we are required to basically compute all possible ways to go from
one start position to another, while we can move only in two directions, which
are right and down, to reach the bottom right (end) coordinates.

In the example below the grid coordinates start from 1,1 and the end coordinates
and size of the grid is defined by the input arguments to the `count` function
R,C

```java
static final class GridPathCounter {

    // hold a string key which represents the count for the specific coordinates of the current row and col, [r,c]. The reason we can
    // use memo here is that once a given coordinate position of row,col is visited, we do not have to visit it again, all paths
    private final Map<String, Integer> MEMO = new HashMap<>();

    public int count(int r, int c) {
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
        int count = count(r - 1, c) + count(r, c - 1);

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
```

The memo approach in this task is rather important, first the key has to be
unique, to make sure we can correctly distinguish between the combination of row
x col pairs, secondly we can not swap those since those coordinates represent
specific unique position pairs.

```java
    // call the path grid counter with a grid of 2 rows and 3 columns
    gridCounter.count(2, 3)
```

```txt
          (2, 3)
        /        \
    (1, 3)       (2, 2)
   /     \       /     \
(0, 3)  (1, 2) (1, 2) (2, 1)
        /    \        /     \
     (0, 2) (1, 1)  (1, 1) (2, 0)
```

If we have a look at an example of 2x3 grid below, we can notice a few nice
properties and make several observations by just looking at the tree
representing the call stack

-   the `count` function is called with the max coordinates (NxM), the recursive
    approach is top down
-   going to the left in the tree means we go up a row (row - 1)
-   going to the right in the tree means we go left a col (col - 1)
-   the pair branch root pair of `(1, 2)`, is contained twice, this is where
    memo will store the first time (coming from the row sub-path) it was visisted
    and simply reference it from the memo table the second time (coming from the col
    sub-path)
-   nodes that contain `0` in one of their pairs are invalid, and indeed grids
    with 0 dimensions are not valid according to our task

##
