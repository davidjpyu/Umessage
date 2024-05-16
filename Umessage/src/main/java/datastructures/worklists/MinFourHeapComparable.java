package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    int size;
    int capacity;

    public MinFourHeapComparable() {
        data = (E[]) new Comparable[5];
        size = 0;
        capacity = 5;
    }

    @Override
    public boolean hasWork() {
        if (size != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void add(E work) {
        if(size >= capacity) {
            capacity *= 5;
            E[] datanew = (E[]) new Comparable[capacity];
            for(int i = 0; i < size; i++) {
                datanew[i] = data[i];
            }
            data = datanew;
        }
        int hole = percolateUp(size, work);
        data[hole] = work;
        size++;
    }

    private int percolateUp(int hole, E val) {
        while(hole > 0 && val.compareTo(data[(hole-1)/4]) < 0) {
            data[hole] = data[(hole-1)/4];
            hole = (hole-1)/4;
        }
        return hole;
    }

    @Override
    public E peek() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }
        E first = data[0];
        int hole = percolateDown(0, data[size-1]);
        data[hole] = data[size-1];
        size--;
        return first;
    }

    private int percolateDown(int hole, E val) {
        while(4 * hole + 1 < size) {
            int left = 4 * hole + 1;
            int midleft = left + 1;
            int midright = left + 2;
            int right = left + 3;
            int target = left;
            if(size > midleft && data[target].compareTo(data[midleft]) > 0) {
                target = midleft;
            }
            if(size > midright && data[target].compareTo(data[midright]) > 0) {
                target = midright;
            }
            if(size > right && data[target].compareTo(data[right]) > 0) {
                target = right;
            }
            if(data[target].compareTo(val) < 0) {
                data[hole] = data[target];
                hole = target;
            } else {
                break;
            }
        }
        return hole;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        data = (E[]) new Comparable[5];
        size = 0;
        capacity = 5;
    }
}