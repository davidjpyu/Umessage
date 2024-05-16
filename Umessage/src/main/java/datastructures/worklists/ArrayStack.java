package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {

    private E[] array;
    private int size;
    private int capacity;

    public ArrayStack() {
        array = (E[])new Object[10];
        size = 0;
        capacity = 10;
    }

    @Override
    public void add(E work) {
        if(size == capacity) {
            capacity *= 2;
            E[] arrayExpend = (E[])new Object[capacity];
            for(int i = 0; i < size; i++) {
                arrayExpend[i] = array[i];
            }
            array = arrayExpend;
        }
        array[size] = work;
        size++;
    }

    @Override
    public E peek() {
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return array[size - 1];
    }

    @Override
    public E next() {
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E lastValue = array[size - 1];
        size--;
        return lastValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        array = (E[])new Object[10];
        size = 0;
        capacity = 10;
    }
}
