# Introduction

One of the most important concepts to get right, and understand, it is used to
describe the efficiency of algorithms. Not understanding it can really hurt you,
and might be judged harshly.

# What is it

Big O is also called asymptotic run time. In very crude terms, no matter how big
the constant is, and how slow the linear increase is, linear will at some point
surpass the constant progression.

- O(1) - constant complexity, meaning that no matter the input parameters, the
run time will always take a given constant amount of time
- O(n) - non constant complexity, meaning that the complexity of the run time is
directly correlated to the input arguments or parameters

There are many types of complexity such as log(n) or n^2, or 2^n. Each of which
describes no constant run time complexity. There is a non-fixed list of possible
non-constant complexity run times.

The run time could also have a dependency on multiple variables, instead of just
n, which represents one of the input parameters, we could have n*m, where m is
another input parameter which is in play.

# Big theta and omega

In academia, there is big O, big theta, and big omega.

- big O - in academia that describes an upper bound on the run time. Something
prints values of an array of N items, can be described as O(N) but it could also
be described as O(N^2), O(N^3), O(N^4). The printing is at least as fast as each
of these run times, it will however never be less than, the lowest complexity
run time, in the example above that is O(N).
- big theta - is the same concept but for a lower bound. Where the inverse is
true, meaning that an algorithm would not be slower/more than than O(N)
- big theta - this is when big O and big theta, give a tight bound on the run
time, meaning when they converge to the same run time complexity. This is what
the industry means by big O, and not the academic, big O which only describes an
upper boundary, as mentioned above.

# Best and worst run time

Generally when talking best, worst and expected run times, in the industry we
focus on the expected and worst, where usually the expected is the worst, or in
other words the worst case is not a case where the algorithm fails totally.
There are some cases where the worst case for a given algorithms differs
significantly from the expected one.

Take quick sort, based on the pivot element we choose, and the direction of the
sorting, descending or ascending, if we always take the first element for a
pivot, and we sort in reverse order the array wont be divided in half by the
pivot, rather it will be shrunk down only by a single element, making the
algorithm effectively of linear run time.

# Space complexity

The space complexity is a parallel concept to the run time complexity, meaning
that each algorithm, requires a specific amount of memory or space to execute
in, based on the type of algorithm, that could be constant, or non-constant
space, for example a recursive print of an array of N elements will take
constant space for the entire execution if we use a simple for loop, however it
would take O(N) space if we use recursion since the call stack, and stack frame
function will constantly grow on each call for the next element.

# Drop the constants

It is possible for an O(N) to be faster than O(1), since the big O describes the
rate of increase, however a constant run time does not mean instant execution.
For this reason we drop the constants in run time meaning that the following
are equivalent - O(1*N) ==O(2*N) ==O(3*N) ==O(4*N) == etc. Why is that the case,
the constant time no matter how big in absolute units of time or space, will/is
always constant, and is not correlated to the input parameters.

Big O allows us to describe how the run or space complexity scales, and not to
measure absolute units of time or space. This is crucial to understand and
grasp.

Take for example, an algorithm, that finds the max and min element in an array,
you could go about this two different ways

1. Two for loops, one for the max and one for the min element
2. One loop to find both the max and min element

Well in either case the complexity here is O(N) for time and O(1) for space,
both of these algorithms, scale the same way with N.

# Drop non dominant terms

Since we can drop the constants, we could also take into account that some
complexities might have multiple terms dependent on the input arguments, where
one of them is so much more dominant / bigger than the other could be simply
removed, since it will not affect the complexity scaling in any significant way

1. O(N^2 + N) - O(N^2)
2. O(N^2 + 10*N) - O(N^2)
3. O(N^3 + log2(N)) - O(N^3)
4. O(2^N + 1000*N) - O(2^N)

Now this is only relevant when we talk about the same input parameter
correlation, we can not reduce a complexity of two different terms, for example
the following O(A + B), can not be reduced without having some information about
the parameters beforehand, if we know that A is significantly more dominant
than B, then yes, but that requires additional data or information about our
input data

# Add vs Multiply of complexities

When would one add two complexities or multiply them. The rule is as follows, if
for each of A chunks of work, we do B chunks of work, then we multiply, this is
mostly expressed as nested loops or similar loop like actions. If however the
work done for each chunk of A and each chunk of B is not related, then we add
the run times, this is similar to have loops executed one after the other for
example.

# Amortized time

What this term describes, is complexities where once in a while, a worst case
scenario will occur for sure, and we can not avoid it. Think about a self
re sizing array, where once the array is full, the internal implementation
doubles the old size into a new array and copies the old elements into the new
one. For this algorithm the regular complexity in time is O(1), but, once in a
while it will degrade to O(N), which is what amortized time describes.

todo: more info here

# Log N run times

In most algorithms we usually deal with log with base 2, since the results we have
to go through, halve by two each time we visit them. So taking a sorted array,
to which we apply binary search, where each iteration we halve the array we have
to look through. Taking as an example array with 16 elements, to find the
element we are looking for we have to make at most 4 comparisons. The run time
still scales with the input, but it is not linear, it is faster, instead of 16
comparisons we do 4.

`log2(n) = k == 2^k = n` - the general formula, 2 raised to what power would
result in n.

`log2(16) = 4 == 2^4 = 16` - the example above will look like this, expressed as
a log of base 2 of 16, which is 4.

Why 4, we start with 16 elements, take the mid point, compare and sub-divide
into 2, until we have only 1 element, or have found the element being search

0. 16 -> starting
1. 8 -> divide by 2
2. 4 -> divide by 2
3. 2 -> divide by 2
4. 1 -> divide by 2

When we see a problem space where the solutions divide by two, it is more often
than not a log (of base 2) of n.

If the problem space was divided by 4 or 8 or 16 times instead on each step,
then our log base would be either 4 8 or 16. For example log4(16) will be 2,
meaning that we can find solution in 2 steps instead of 4 (see example above)

Note that the base of the log, does not matter, the complexity will still be log
of some base of the number of input arguments, could be log10(n) or log4(10)
etc. What the log describes is the type of scaling in relation to the input, we
are not looking for which log base produces a smaller / better absolute value.

# Recursive run times

```java
int f(int n) {
    if (n <= 1) {
        return 1;
    }
    return f(n - 1) + f(n - 1);
}
```

Calling the above function with f(4), what is the complexity of this function.
Well for f(4), the function would fall f(3) twice, f(3), would call f(2) twice,
and so forth, so with each level we double the calls, until we reach the bottom.

The number of calls would then be 2^N (where N is the depth of the recursion
tree) - 1. In our case, the depth N is 3, the bottom most level is 3, the level
above it is 2, the one above it is 1, and the final one with the root node,
where we start f(4) is 0. Meaning we would do 2^4 - 1 function calls

To be more detailed. Think about it like that, we have to calculate, how does
our algorithm scale with the change in N. We know that for each number N, the
function f is called twice. And each call goes down the depth of the tree.

What is important to note here is what N is, it is not the number of elements of
an array, of the size of the binary tree which the call stack forms by
definition, or it's depth, what N is, in this case is simply a number, but that
number governs our algorithm and how it scales, so the algorithm scales with an
aboslute number N (an integer, such as 1,2,3...N). To calculate N we do 2
recursive calls to calculate N-1. With each increase in N, the number of calls
doubles.

The branching factor is 2, and the maximum depth is K, so the big O(2^K). That
expresses the scaling, the actual number of function calls (about which we do
not really care is 2^K - 1, in the example above 15 calls to f(n) will be made)

Note that, by coincidence, the depth and the input here match, so N == K,
levels, but do not confuse the depth of the tree, and the input N. In our simple
example the depth scales proportionally with N.

# Examples and tasks
