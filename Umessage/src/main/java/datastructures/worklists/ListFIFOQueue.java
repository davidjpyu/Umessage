package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private queueNode<E> queFront;
    private queueNode<E> queEnd;

    private int size;

    public ListFIFOQueue() {
        queFront = null;
        queEnd = null;
        size = 0;
    }

    @Override
    public void add(E work) {
        if(size == 0) {
            queEnd = new queueNode<>(work);
            queFront = queEnd;
        } else {
            queEnd.next = new queueNode<>(work);
            queEnd = queEnd.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }

        return queFront.value;
    }

    @Override
    public E next() {
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }

        size--;
        E lastValue = queFront.value;
        queFront = queFront.next;

        return lastValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        queFront = null;
        queEnd = null;
        size = 0;
    }

    private class queueNode<E>{
        public E value;
        public queueNode<E> next;

        public queueNode(E value) {
            this(value, null);
        }

        public queueNode(E value, queueNode<E> next) {
            this.value = value;
            this.next = next;
        }
    }
}