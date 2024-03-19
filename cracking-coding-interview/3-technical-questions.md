# Important topics

For each of the topics below make sure you understand how to use and implement
them, on paper and then on a computer.

## Structures

1. Linked lists
2. Trees, Tires and Graphs
3. Stacks & Queues
4. Heaps
5. Vectors / Arrays
6. Hash tables

## Algorithms

1. Breadth-First Search
2. Depth-First Search
3. Binary Search
4. Merge Sort
5. Quick Sort
6. Other Sorting algorithms

## Concepts

1. Bit manipulation
2. Memory (Stack & Heap)
3. Recursion
4. Dynamic programming
5. Big O time & space

# Solving process

Make sure to understand the problem , ask for paper and pen and record the
problem on it, write it down, do not try to memorize it otherwise you will miss
important details. Ask for example, draw and write down it too.

When drawing an example make it as specific to the problem as possible, use the
data you have been given, or make up one yourself, but try to make the example
about the problem, do not draw generic trees, stacks etc. Make it large so you
can see patterns, edge cases etc.

Try to trade off space or time for the other e.g. - store data (hash-table,
vector, list, stack, tree etc.), sort data (mutate the input example, by sorting
it to fit in the problem's solution).

Sometimes it might be useful to write down an implementation that solves the
issue incorrectly, or not completely.

1. Listen - pay very close attention to any information, in the problem
   description, ask for more details, as much as you can think of to narrow down
   the problem at hand, try to make no assumptions about the problem.
2. Example - ask for example, more than one if you have to, be aggressive with
   the questions, to allow for no foul play
3. Brute force - try to solve the problem in the most naive obvious way, that
   comes to mind
4. Optimize - try to apply different approaches to the problem solution
   afterwards, such as dynamiting programming or memorization etc - avoid
   bottlenecks, unnecessary work or duplicated work
5. Walk through - analyze the problem, try to describe it, make sure it makes
   sense, if not, go back to the example and start over, from step 2
6. Implement - make sure whatever you implement is well structured, now that it
7. Test - go through your code and test it, if you have to do any changes to
   adjust the logic go back to step 3

# Compilation & Interpretation

* Compiled Languages: In strictly compiled languages, such as C and C++, source
  code is translated directly into machine code by a compiler before execution.
  The resulting machine code is specific to the target hardware architecture.

  1. Compilation Process: The compilation process involves translating the entire
     source code into machine code in one pass. This generates an executable file
     containing native machine code that can be directly executed by the CPU.
  2. Execution: Strictly compiled languages produce standalone executables that are
     independent of the original source code. The compiled code is executed directly
     by the CPU without the need for an intermediate runtime environment.

* Interpreted Languages: In interpreted languages, such as Python and
  JavaScript, source code is executed line by line by an interpreter at runtime.
  The interpreter reads and executes each statement of the source code
  sequentially.

  1. Interpretation Process: The interpreter reads the source code, parses it into
     intermediate representations (such as bytecode or AST), and executes it
     immediately. There is no separate compilation step to generate machine code.
  2. Execution: Interpreted languages typically require an interpreter to be
     installed on the target system to execute the source code. The interpreter
     translates and executes the source code on-the-fly, without producing
     standalone executables.

## Key & Core Differences

* Compilation vs. Interpretation:
  * Compiler: Translates entire source code into machine code before execution.
  * Interpreter: Executes source code line by line at runtime without prior translation.

* Output:
  * Compiler: Produces standalone executable files containing native machine code.
  * Interpreter: Executes source code directly without generating standalone executables.

* Execution Speed:
  * Compiler: Generally produces faster-executing code as it is optimized for the target hardware architecture.
  * Interpreter: May have slower execution speed compared to compiled languages due to the overhead of interpretation at runtime.

* Portability:
  * Compiler: Generates machine code specific to the target hardware architecture, potentially limiting portability.
  * Interpreter: Source code is platform-independent, and the interpreter translates and executes code on-the-fly, allowing for greater portability.

In summary, strictly compiled languages translate source code into machine code
before execution, resulting in standalone executables, while interpreted
languages execute source code directly at runtime using an interpreter.

## Compiled & Interpreted

Several interpreted languages utilize Just-In-Time (JIT) compilation techniques
to improve performance. Some examples of interpreted languages with JIT
compilation include:

* JavaScript (V8 Engine): JavaScript is commonly used in web development, and
  modern JavaScript engines such as Google's V8 engine (used in Chrome and
  Node.js) employ JIT compilation to dynamically compile frequently executed
  JavaScript code into native machine code for improved performance.

* Python (PyPy): While Python is traditionally interpreted, projects like PyPy
  implement a JIT compiler for Python code. PyPy's JIT compiler (Just-In-Time
  Compiler for Python) dynamically compiles Python bytecode into native machine
  code at runtime, resulting in significant performance improvements over
  traditional interpreters.

* Ruby (YARV): The YARV (Yet Another Ruby VM) interpreter for Ruby includes a
  JIT compiler called MJIT (Method JIT). MJIT dynamically compiles Ruby methods
  into native machine code, providing performance enhancements for Ruby
  applications.

* PHP (HHVM): The HipHop Virtual Machine (HHVM), developed by Facebook, includes
  a JIT compiler for PHP code. HHVM's JIT compiler dynamically compiles PHP
  bytecode into native machine code, offering performance improvements over
  traditional PHP interpreters.

* Lua (LuaJIT): LuaJIT is a Just-In-Time (JIT) compiler for the Lua programming
  language. It dynamically compiles Lua bytecode into highly optimized native
  machine code at runtime, resulting in significant performance gains for Lua
  applications.

* Java (JVM): like HotSpot, incorporate Just-In-Time (JIT) compilation. The JVM
  dynamically compiles frequently executed bytecode sequences into optimized
  native machine code during runtime. This compilation process aims to improve the
  performance of Java applications by translating bytecode into efficient machine
  code

## Java virtual machine

Key features and processes performed by the JVM, include:

1. The Java Virtual Machine (JVM) executes Java bytecode, which is an
   intermediate representation of Java code compiled from source code (.java
   files). Here's an overview of the pipeline for executing Java code on the JVM:

2. Java Source Code Compilation: Java source code files (.java) are compiled
   into Java bytecode files (.class) using the Java compiler (javac). The Java
   compiler translates Java source code into platform-independent bytecode
   instructions, which are stored in .class files.

3. Class Loading: The JVM loads classes (.class files) into memory as they are
   needed during program execution. Class loading involves three steps: loading,
   linking, and initialization. Loading: The class loader reads the bytecode from
   the .class file and creates a representation of the class in memory. Linking:
   The class loader performs verification, preparation (e.g., allocating memory for
   static fields), and resolution (e.g., resolving symbolic references to other
   classes). Initialization: The JVM initializes the class by executing static
   initializers and initializing static fields.

4. Bytecode Verification: Before executing bytecode, the JVM performs bytecode
   verification to ensure that the bytecode adheres to certain safety
   constraints and does not violate the Java language rules. Bytecode verification
   checks for various properties, such as type safety, stack integrity, and proper
   control flow.

5. Just-In-Time (JIT) Compilation: The JVM employs a Just-In-Time (JIT) compiler
   to translate bytecode into native machine code at runtime. When a method is
   called frequently or identified as a hot spot during profiling, the JIT compiler
   compiles the bytecode of the method into optimized native machine code. The
   native machine code is then cached and executed directly by the CPU, bypassing
   interpretation of the bytecode. JIT compilation aims to improve performance by
   dynamically optimizing frequently executed code paths.

6. Execution: The JVM executes the compiled native machine code, which
   corresponds to the bytecode instructions of the Java program. Execution
   involves interpreting bytecode or executing the compiled native machine code,
   depending on whether JIT compilation has been performed for specific code paths.

7. Garbage Collection: The JVM manages memory allocation and deallocation using
   automatic garbage collection. Garbage collection identifies and reclaims
   memory that is no longer in use, preventing memory leaks and ensuring efficient
   memory usage.

```java
    public class Calculator {
        public static int sum(int a, int b) {
            return a + b;
        }
    }
```

````sh
    $ javac Calculator.java
    $ javap -c Calculator
``

```asm

    public class Calculator {
        public static int sum(int, int);
            Code:
            0: iload_0
            1: iload_1
            2: iadd
            3: ireturn
    }
````

The bytecode for the sum method consists of the following instructions:

* iload\_0: Load the value of the first argument (a) onto the stack.
* iload\_1: Load the value of the second argument (b) onto the stack.
* iadd: Add the top two integers on the stack and push the result back onto the stack.
* ireturn: Return the integer result from the method.

```asm
    sum:
        mov eax, DWORD PTR [rsp+0x8]    ; Load the value of the first argument (a)
        add eax, DWORD PTR [rsp+0xc]    ; Add the value of the second argument (b)
        ret                             ; Return the result
```

After JIT compilation, the bytecode instructions are translated into native
machine code. The native machine code for the sum method is represented using
x86 assembly language here. Instructions such as mov and add perform similar
operations to the bytecode instructions but are specific to the native
architecture. The ret instruction is used to return the result from the method.
