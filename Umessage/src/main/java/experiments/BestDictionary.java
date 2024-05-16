package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileNotFoundException;

import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;

public class BestDictionary {
    private static final int num = 100;
    private static final File file = new File("corpus/alice.txt");

    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<String, Integer> BST = new BinarySearchTree<>();
        AVLTree<String, Integer> AVL = new AVLTree<>();
        ChainingHashTable<String, Integer> CHT = new ChainingHashTable<>(AVLTree::new);
        HashTrieMap<Character, AlphabeticString, Integer> HTM = new HashTrieMap<>(AlphabeticString.class);
        dicInsert(BST);
        System.out.print("BST insert: " + dicInsert(BST));
        dicFind(BST);
        System.out.println(" find: " + dicFind(BST));
        dicInsert(AVL);
        System.out.print("AVL insert: " + dicInsert(AVL));
        dicFind(AVL);
        System.out.println(" find: " + dicFind(AVL));
        dicInsert(CHT);
        System.out.print("CHT insert: " + dicInsert(CHT));
        dicFind(CHT);
        System.out.println(" find: " + dicFind(CHT));
        HTMInsert(HTM);
        System.out.print("HTM insert: " + HTMInsert(HTM));
        HTMFind(HTM);
        System.out.println(" find: " + HTMFind(HTM));
    }

    public static long dicInsert(Dictionary<String, Integer> dict) throws FileNotFoundException {
        long total = 0;
        for (int i = 0; i < num; i++) {
            Scanner itr = new Scanner(file);
            long start = System.nanoTime();

            while (itr.hasNext()) {
                String diction = itr.next();
                Integer value = dict.find(diction);
                if (value == null) {
                    dict.insert(diction, 1);
                } else {
                    dict.insert(diction, value + 1);
                }
            }

            long end = System.nanoTime();
            total += end - start;
        }
        return total / num;
    }

    public static long dicFind(Dictionary<String, Integer> dict) throws FileNotFoundException {
        long total = 0;
        for (int i = 0; i < num; i++) {
            Scanner itrInput = new Scanner(file);

            HashSet<String> wordSet = new HashSet<>();

            while (itrInput.hasNext()) {
                wordSet.add(itrInput.next());
            }

            Iterator<String> itrSet = wordSet.iterator();

            long start = System.nanoTime();
            while (itrSet.hasNext()) {
                dict.find(itrSet.next());
            }
            long end = System.nanoTime();

            total += end - start;
        }
        return total / num;
    }

    public static long HTMInsert(HashTrieMap<Character, AlphabeticString, Integer> HTM) throws FileNotFoundException {
        long total = 0;
        for (int i = 0; i < num; i++) {
            Scanner itr = new Scanner(file);
            long start = System.nanoTime();

            while (itr.hasNext()) {
                AlphabeticString diction = new AlphabeticString(itr.next());
                Integer findValue = HTM.find(diction);
                if (findValue == null) {
                    HTM.insert(diction, 1);
                } else {
                    HTM.insert(diction, findValue + 1);
                }
            }

            long end = System.nanoTime();
            total += end - start;
        }
        return total/num;
    }

    public static long HTMFind(HashTrieMap<Character, AlphabeticString, Integer> HTM) throws FileNotFoundException {
        long total = 0;
        for (int i = 0; i < num; i++) {
            Scanner itrInput = new Scanner(file);

            HashSet<String> wordSet = new HashSet<>();
            while (itrInput.hasNext()) {
                String diction = itrInput.next();
                AlphabeticString dictionAlph = new AlphabeticString(diction);
                wordSet.add(diction);
                Integer findValue = HTM.find(dictionAlph);
                if (findValue == null) {
                    HTM.insert(dictionAlph, 1);
                } else {
                    HTM.insert(dictionAlph, findValue + 1);
                }
            }

            Iterator<String> itrOutput = wordSet.iterator();

            long start = System.nanoTime();
            while (itrOutput.hasNext()) {
                HTM.find(new AlphabeticString(itrOutput.next()));
            }
            long end = System.nanoTime();

            total += end - start;
        }
        return total / num;
    }
}
