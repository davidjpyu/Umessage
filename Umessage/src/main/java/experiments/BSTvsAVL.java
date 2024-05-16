package experiments;
import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

public class BSTvsAVL {
    private static final int num = 1000;

    public static void main(String[] args) {
        for (int i = 10; i <= 100; i += 100) { // prep
            BSTworsttime(i);
        }
        for (int i = 100; i <= 1000; i += 100) {
            System.out.println("BST for size "+i+ " is " + BSTworsttime(i) + " ms");
        }

        for (int i = 10; i <= 100; i += 100) { // prep
            AVLworsttime(i);
        }
        for (int i = 100; i <= 1000; i += 100) {
            System.out.println("AVL for size "+i+ " is " + AVLworsttime(i) + " ms");
        }
    }

    public static long BSTworsttime(int size) {
        BinarySearchTree<Integer, String> BST = new BinarySearchTree<>();

        long totalTime = 0;

        for (int i = 0; i < num; i++) {
            long start = System.nanoTime();
            for (int j = 0; j < size; j++) {
                BST.insert(j, "" + j);
            }
            for (int k = size; k > size - size/10; k--) {
                BST.find(k);
            }
            long end = System.nanoTime();
            totalTime += end - start;
        }
        return totalTime/num;
    }

    public static long AVLworsttime(int size) {
        AVLTree<Integer, String> AVL = new AVLTree<>();

        long totalTime = 0;

        for (int i = 0; i < num; i++) {
            long start = System.nanoTime();
            for (int j = 0; j < size; j++) {
                AVL.insert(j, "" + j);
            }
            for (int k = size; k > size - size/10; k--) {
                AVL.find(k);
            }
            long end = System.nanoTime();
            totalTime += end - start;
        }
        return totalTime/num;
    }
}
