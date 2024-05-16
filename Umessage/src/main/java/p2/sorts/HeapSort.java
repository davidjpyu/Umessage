package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> heapSort = new MinFourHeap<>(comparator);
        for (E work: array) {
            heapSort.add(work);
        }
        int size = heapSort.size();
        for (int i = 0; i < size; i++) {
            array[i] = heapSort.next();
        }
    }
}
