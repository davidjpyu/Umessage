package experiments;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import java.util.Random;

public class ChainingCompare {
    private static final int num = 100;

    public static void main(String[] args) {
        Random rand = new Random();
        for (int i = 1000; i < 10000+1; i += 1000){
            int[] arrInput = new int[i];
            for (int j = 0; j < i; j++) {
                arrInput[j] = rand.nextInt(i);
            }
            MTFInsert(arrInput);
            System.out.println("insert size " + i + " for MTF requires " + MTFInsert(arrInput) + " ns");
            BSTInsert(arrInput);
            System.out.println("insert size " + i + " for BST requires " + BSTInsert(arrInput) + " ns");
            AVLInsert(arrInput);
            System.out.println("insert size " + i + " for AVL requires " + AVLInsert(arrInput) + " ns");

            int[] arrOutput = new int[i];
            for (int j = 0; j < i; j++) {
                arrOutput[j] = rand.nextInt(i);
            }

            MTFFind(arrInput, arrOutput);
            System.out.println("find size " + i + " for MTF requires " + MTFFind(arrInput, arrOutput) + " ns");
            BSTFind(arrInput, arrOutput);
            System.out.println("find size " + i + " for BST requires " + BSTFind(arrInput, arrOutput) + " ns");
            AVLFind(arrInput, arrOutput);
            System.out.println("find size " + i + " for AVL requires " + AVLFind(arrInput, arrOutput) + " ns");
        }
    }

    public static long MTFInsert(int[] elements) {
        ChainingHashTable<Integer, String> CHT_MTF = new ChainingHashTable<>(MoveToFrontList::new);

        long total = 0;
        for (int i = 0; i < num; i++) {
            long start = System.nanoTime();

            for(int j = 0; j < elements.length; j++) {
                CHT_MTF.insert(elements[j], "" + elements[j]);
            }
            long end = System.nanoTime();
            total += end - start;
        }

        return total / num;
    }

    public static long MTFFind(int[] elements, int[] elementsFind) {
        ChainingHashTable<Integer, String> CHT_MTF = new ChainingHashTable<>(MoveToFrontList::new);

        long total = 0;
        for (int i = 0; i < num; i++) {

            for(int j = 0; j < elements.length; j++) {
                CHT_MTF.insert(elements[j], "" + elements[j]);
            }

            long start = System.nanoTime();

            for(int j = 0; j < elementsFind.length; j++) {
                CHT_MTF.find(elementsFind[j]);
            }
            long end = System.nanoTime();
            total += end - start;
        }
        return total/num;
    }

    public static long BSTInsert(int[] elements) {
        ChainingHashTable<Integer, String> CHT_BST = new ChainingHashTable<>(BinarySearchTree::new);

        long total = 0;
        for (int i = 0; i < num; i++) {
            long start = System.nanoTime();

            for(int j = 0; j < elements.length; j++) {
                CHT_BST.insert(elements[j], "" + elements[j]);
            }
            long end = System.nanoTime();
            total += end - start;
        }

        return total / num;
    }

    public static long BSTFind(int[] elements, int[] elementsFind) {
        ChainingHashTable<Integer, String> CHT_BST = new ChainingHashTable<>(BinarySearchTree::new);

        long total = 0;
        for (int i = 0; i < num; i++) {

            for(int j = 0; j < elements.length; j++) {
                CHT_BST.insert(elements[j], "" + elements[j]);
            }

            long start = System.nanoTime();

            for(int j = 0; j < elementsFind.length; j++) {
                CHT_BST.find(elementsFind[j]);
            }
            long end = System.nanoTime();
            total += end - start;
        }
        return total/num;
    }

    public static long AVLInsert(int[] elements) {
        ChainingHashTable<Integer, String> CHT_AVL = new ChainingHashTable<>(AVLTree::new);

        long total = 0;
        for (int i = 0; i < num; i++) {
            long start = System.nanoTime();

            for(int j = 0; j < elements.length; j++) {
                CHT_AVL.insert(elements[j], "" + elements[j]);
            }
            long end = System.nanoTime();
            total += end - start;
        }

        return total / num;
    }

    public static long AVLFind(int[] elements, int[] elementsFind) {
        ChainingHashTable<Integer, String> CHT_AVL = new ChainingHashTable<>(AVLTree::new);

        long total = 0;
        for (int i = 0; i < num; i++) {

            for(int j = 0; j < elements.length; j++) {
                CHT_AVL.insert(elements[j], "" + elements[j]);
            }

            long start = System.nanoTime();

            for(int j = 0; j < elementsFind.length; j++) {
                CHT_AVL.find(elementsFind[j]);
            }
            long end = System.nanoTime();
            total += end - start;
        }
        return total/num;
    }
}

