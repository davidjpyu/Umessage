package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap heap = new MinFourHeap(comparator);
        int i = 0;
        int len = array.length;
        while (i < len) {
            heap.add(array[i]);
            i++;
            if (heap.size() > k) {
                heap.next();
            }
        }
        for (int index = 0; index < k && index < len; index++) {
            array[index] = (E) heap.next();
        }
        for (int index = k; index < len; index++) {
            array[index] = null;
        }
    }
}
