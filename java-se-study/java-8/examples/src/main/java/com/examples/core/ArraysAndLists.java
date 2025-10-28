package com.examples.core;

public class ArraysAndLists {

    public static void main(String[] args) {
        // shocase how we can define the square braces for the array right of the type and left of the identifier of the variable, the left
        // side declaration always has the empty brackes,
        int[] bracesLeftOfIdentifier = new int[100];

        // demonstrate how we can also put the braces on the identifier hname itself, both are expressing the same meaning and that is to
        // define an array of 100 elements named <identifier>
        int bracesRightOfIdentifier[] = new int[100];

        // we can also define the two identifiers as array by simply putting the braces on the type itself, that way every variable defined
        // to the right is an array
        int[] array1, array2;

        // we can also make sure that we define different types by putting the brace right of the identifer that way only specific
        // identifierss are defined as arrays.
        int arr1[], integer, arr2[];

        // to initialize an array with predefined values we can also use the initialization list like shown below, the size is automatically
        // inferred
        int initialized[] = {5, 10, 15};
        int initialized2[] = new int[] {5, 10, 15};

        // we are also alloed to override or re-assign the values of the arrays after the declaration, in this case with new array values,
        // however it is not possible to use the initialization list outside of the declaration step

        // this is a compile time error, we can not use the initialization list here, where the variable is re-assigned
        initialized={200,300,500};

        // shocase a perfectly valid re-assignment of the array reference to a new one, which contains different and more elements than the
        // initial one
        initialized2 = new int[] {200, 300, 500, 1000};

        // this declaration is not valid, and will produce a compile time error the size is always defined on the right side side on the
        // assignment, not the declaration of the identifier
        int[100] invalidArraySize = new int[100];

        // there is one way to iterate over an array, this is using the simplified version of the java for loop which is also built into the
        // language
        for (int k : initialized2) {
            // do something with the array
        }

        // there is another way to iterate over the array elements by using the length of the array and a standard for-i loop
        for (int i = 0; i < initialized2.length; i++) {
            // do something with the array
        }

        // showcase how we can construct a multi dim array, in this case at least the very first dim has to be initialized if we use new
        int multiDimArray[][] = new int[10][];

        // demonstrate how we can also use initialization list, to initialize levels of the multi dim array as well
        int multiDimArray2[][] = {{1, 2, 3}, {4, 5, 6}, {}};
    }
}
