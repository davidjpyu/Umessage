package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private DicNode front;

    public MoveToFrontList() {
        front = null;
        size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if(this.find(key) == null) {
            DicNode newFront = new DicNode(key, value, front);
            front = newFront;
            size++;
            return null;
        } else {
            V lastValue = (V)front.value;
            front.value = value;
            return lastValue;
        }
    }

    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        if(front == null) {
            return null;
        }
        DicNode current = front;
        if(front.key.equals(key)) {
            return front.value;
        }
        while(current.next != null) {
            if(current.next.key.equals(key)) {
                DicNode chosen = current.next;
                current.next = current.next.next;
                chosen.next = front;
                front = chosen;
                return front.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFLIterator();
    }

    private class MTFLIterator extends SimpleIterator<Item<K, V>> {
        private DicNode current;
        boolean flag;
        // private final WorkList<DicNode> nodes;

        public MTFLIterator() {
            //this.nodes = new ArrayStack<DicNode>();
            if (MoveToFrontList.this.front != null) {
                this.current = MoveToFrontList.this.front;
                /*while (this.current != null) {
                    this.nodes.add(this.current);
                    this.current = this.current.next;
                }*/
                flag = true;
            } else {
                flag = false;
            }

        }

        @Override
        public boolean hasNext() {
            return flag;
        }

        @Override
        public Item<K, V> next() {
            if(!this.hasNext()) {
                throw new NoSuchElementException();
            }
            // Go left until we hit null
            DicNode lastCurrent = current;
            if(this.current.next != null) {
                this.current = this.current.next;
            } else {
                flag = false;
            }
            return lastCurrent;
        }
    }

    public class DicNode extends Item<K, V> {
        public DicNode next; // The children of this node.

        /**
         * Create a new data node.
         *
         * @param key
         *            key with which the specified value is to be associated
         * @param value
         *            data element to be stored at this node.
         */
        @SuppressWarnings("unchecked")
        public DicNode(K key, V value) {
            this(key, value, null);
        }

        public DicNode(K key, V value, DicNode next) {
            super(key, value);
            this.next = next;
        }
    }
}
