package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;

    private int[] primeList = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853};;
    private Dictionary<K, V>[] pairArr;
    private int capacity;
    private int capacityIndex;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        size = 0;
        capacityIndex = 0;
        capacity = primeList[capacityIndex];
        pairArr = new Dictionary[capacity];
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }

        V returnValue = null;
        int index = key.hashCode() % capacity;
        if (index < 0) {
            index += capacity;
        }
        if(pairArr[index] == null) {
            pairArr[index] = newChain.get();
        }
        returnValue = pairArr[index].insert(key, value);
        if(returnValue == null) {
            size++;
        }

        // resize
        if(size > capacity) {
            capacityIndex++;
            int capacity_new;
            if(capacityIndex < primeList.length) {
                capacity_new = primeList[capacityIndex];
            } else {
                capacity_new = capacity * 2 + 1;
            }
            Dictionary<K, V>[] pairArr_new = new Dictionary[capacity_new];
            Iterator<Item<K, V>> itr = this.iterator();
            while(itr.hasNext()){
                Item<K,V> nextOne = itr.next();
                int index_new = nextOne.key.hashCode() % capacity_new;
                if (index_new < 0) {
                    index_new += capacity_new;
                }
                if(pairArr_new[index_new] == null) {
                    pairArr_new[index_new] = newChain.get();
                }
                pairArr_new[index_new].insert(nextOne.key, nextOne.value);
            }
            capacity = capacity_new;
            pairArr = pairArr_new;
        }
        /////////

        return returnValue;
    }

    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        int index = key.hashCode() % capacity;
        if(index < 0) {
            index += capacity;
        }
        if(pairArr[index] == null) {
            return null;
        } else {
            return pairArr[index].find(key);
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();

    }

    private class CHTIterator extends SimpleIterator<Item<K, V>> {
        // private Iterator<Item<K, V>> current;
        private Item<K, V> currentNode;
        Iterator<Item<K, V>> itr;
        int i;

        public CHTIterator() {
            i = 0;
            while(i < capacity && pairArr[i] == null){
                i++;
            }
            if(i < capacity) {
                // this.current = pairArr[i].iterator();
                itr = pairArr[i].iterator();
                this.currentNode = null;
            }
        }

        @Override
        public boolean hasNext() {
            if (this.itr == null) {
                return false;
            }
            if(!this.itr.hasNext()) {
                i++;
                while(i < capacity && pairArr[i] == null){
                    i++;
                }
                if(i < capacity) {
                    itr = pairArr[i].iterator();
                }
            }
            return itr.hasNext();
        }

        @Override
        public Item<K, V> next() {
            if(!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.currentNode = itr.next();
            return currentNode;
        }

    }
}