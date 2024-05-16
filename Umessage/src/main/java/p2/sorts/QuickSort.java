package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        helper(array, comparator, 0, array.length);
    }

    private static <E> void helper(E[] array, Comparator<E> comparator, int low, int high) {
        if ((high - low) >= 1) {
            //pivot
            E pivot = array[high - 1];
            //pointer initia
            int pointer = low - 1;
            //traverse
            for (int j = low; j < high - 1; j++) {
                if (comparator.compare(array[j], pivot) <= 0) { // c+0ompare with the pivot and if smaller swap with the pointer +1
                    pointer++;
                    //swap
                    E temp = array[j];
                    array[j] = array[pointer];
                    array[pointer] = temp;
                }
            }
            // swap the pivot with the pointer
            E temp2 = array[pointer + 1];
            array[pointer + 1] = array[high - 1];
            array[high - 1] = temp2;

            helper(array, comparator, low, pointer + 1);
            helper(array, comparator, pointer + 2, high);
        }
    }
}