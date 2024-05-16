package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    public int front;
    public int rear;
    public int size;
    public E[] array;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        front = -1;
        rear = -1;
        size = 0;
        array = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException();
        }
        if (this.size == 0) {
            this.front++;
        }
        size++;
        this.rear = (this.rear + 1) % (this.array.length);
        array[this.rear] = work;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        // size >= one
        return array[front];
    }

    @Override
    public E peek(int i)  {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if ((i < 0) || (i >= this.size())) {
            throw new IndexOutOfBoundsException();
        }
        return array[(this.front + i) % this.array.length];
    }

    @Override
    public E next() {
        if (!hasWork()) { //size <= 0
            throw new NoSuchElementException();
        }
        E temp = this.array[front];
        if (this.size == 1) { // size = 1
            front = -1;
            rear = -1;
        } else {
            this.front = (this.front + 1) % (this.array.length);
        }
        size--;
        return temp;
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if ((i < 0) || (i >= this.size())) {
            throw new IndexOutOfBoundsException();
        }
        this.array[(this.front + i) % this.array.length] = value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        front = -1;
        rear = -1;
        size = 0;
        array = (E[])new Comparable[capacity()];
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int minimumSize = Math.abs(this.size()-other.size());
        Iterator<E> thisitr = this.iterator();
        Iterator<E> otheritr = other.iterator();
        while(thisitr.hasNext() && otheritr.hasNext()){
            int comparison = thisitr.next().compareTo(otheritr.next());
            if (comparison != 0){
                return comparison;
            }
        }
        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            // Your code goes here
            return this.compareTo(other) == 0;
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int total = 0;
        for(int i = 0; i < size; i++) {
            E element = this.peek(i);
            total += element.hashCode()*(int)Math.pow(13, i+1);
        }
        return total;
    }
}