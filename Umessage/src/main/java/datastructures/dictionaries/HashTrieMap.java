package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import java.util.AbstractMap.SimpleEntry;

import java.util.*;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);

            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator(){
            return new HTMIterator(pointers);
        }

        private class HTMIterator implements Iterator<Entry<A, HashTrieNode>>{
            Iterator<Item<A, HashTrieNode>> itr;

            public HTMIterator(ChainingHashTable<A, HashTrieNode> dic){
                itr = dic.iterator();
            }

            public boolean hasNext(){
                return itr.hasNext();
            }

            public Entry<A, HashTrieNode> next(){
                Item<A, HashTrieNode> itrNode = itr.next();
                return new SimpleEntry<>(itrNode.key, itrNode.value);
            }
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for(A singleKey: key) {
            if(current.pointers.find(singleKey) ==null) {
                current.pointers.insert(singleKey, new HashTrieNode());
            }
            current = current.pointers.find(singleKey);
        }
        V previousValue = current.value;
        if(previousValue == null) {
            size++;
        }
        current.value = value;
        return previousValue;
    }

    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for(A singleKey: key) {
            if(current.pointers.find(singleKey) != null) {
                current = current.pointers.find(singleKey);
            } else {
                return null;
            }
        }
        return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for(A singleKey: key) {
            if(current != null && current.pointers.find(singleKey)!= null) {
                current = current.pointers.find(singleKey);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode current = (HashTrieNode) this.root;
        HashTrieNode lastSaved = current;
        if(key.isEmpty()){
            if(this.find(key) != null) {
                current.value = null;
                size--;
            }
        } else {
            if (this.find(key) != null) {
                A lastKey = key.iterator().next();

                for (A singleKey : key) {
                    if (current.pointers.find(singleKey) != null) {
                        if (current.value != null || current.pointers.size() > 1) {
                            lastKey = singleKey; //r
                            lastSaved = current; //2rd circle
                        }
                        current = current.pointers.find(singleKey);
                    }
                }
                if (current.pointers.size() > 0) {
                    current.value = null;
                } else {
                    lastSaved.pointers.delete(lastKey);
                }
                size--;
            }
        }
    }

    @Override
    public void clear() {

        ((HashTrieNode)root).pointers.clear();
    }
}