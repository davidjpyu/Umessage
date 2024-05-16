package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.worklists.ArrayStack;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // TODO: Implement me!


    public class AVLNode extends BSTNode {
        public int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }

    public AVLTree() {
        super();
    }

    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V lastValue = null;
        ///// copy from BST
        BSTNode prev = null;
        BSTNode current = this.root;

        int child = -1;
        ArrayStack<BSTNode> storePath = new ArrayStack<BSTNode>();


        while (current != null) {
            int direction = Integer.signum(key.compareTo(current.key));

            // We found the key! Record last value and replace
            if (direction == 0) {
                lastValue = current.value;
                current.value = value;
                return lastValue;
            }
            else {
                storePath.add(current);
                // direction + 1 = {0, 2} -> {0, 1}
                child = Integer.signum(direction + 1);
                prev = current;
                current = current.children[child];
            }
        }
        //////// copy ends

        // Add new elements to
        current = new AVLNode(key, value);
        if (this.root == null) {
            this.root = current;
        }
        else {
            assert(child >= 0); // child should have been set in the loop
            // above
            prev.children[child] = current; // add over
        }
        this.size++;
        int storeSize = storePath.size();
        for(int i = 0; i < storeSize; i++) {
            AVLNode currentNode = (AVLNode) storePath.next();
            AVLNode leftNode = (AVLNode) currentNode.children[0];
            AVLNode rightNode = (AVLNode) currentNode.children[1];
            if ((leftNode != null && leftNode.height == currentNode.height) || rightNode.height == currentNode.height) {
                currentNode.height++;
            }

            // problem node is currentNode
            int leftHeight;
            int rightHeight;

            if(leftNode == null && rightNode == null){
                leftHeight = -1;
                rightHeight = -1;
            } else if (leftNode == null) {
                leftHeight = -1;
                rightHeight = rightNode.height;
            } else if(rightNode == null){
                leftHeight = leftNode.height;
                rightHeight = -1;
            } else {
                leftHeight = leftNode.height;
                rightHeight = rightNode.height;
            }
            if(Math.abs(leftHeight - rightHeight) > 1) {
                int leftleftHeight = -1;
                int leftrightHeight = -1;
                int rightleftHeight = -1;
                int rightrightHeight = -1;

                if(leftNode != null && leftNode.children[0] != null){
                    leftleftHeight = ((AVLNode)leftNode.children[0]).height;
                }

                if(leftNode != null && leftNode.children[1] != null){
                    leftrightHeight = ((AVLNode)leftNode.children[1]).height;
                }

                if(rightNode != null && rightNode.children[0] != null){
                    rightleftHeight = ((AVLNode)rightNode.children[0]).height;
                }

                if(rightNode != null && rightNode.children[1] != null){
                    rightrightHeight = ((AVLNode)rightNode.children[1]).height;
                }

                    /*
                    AVLNode leftleftNode = (AVLNode)leftNode.children[0]; //// replace back!!!!!
                    AVLNode leftrightNode = (AVLNode)leftNode.children[1];
                    AVLNode rightleftNode = (AVLNode)rightNode.children[0];
                    AVLNode rightrightNode = (AVLNode)rightNode.children[1];
                     */

                if(leftHeight > rightHeight && leftleftHeight > leftrightHeight) { // left left
                    currentNode.children[0] = leftNode.children[1];
                    leftNode.children[1] = currentNode;

                    if(storePath.hasWork()) {
                        AVLNode prevNode = (AVLNode) storePath.next();
                        if (prevNode.children[0] == currentNode) {
                            prevNode.children[0] = leftNode;
                        } else {
                            prevNode.children[1] = leftNode;
                        }
                    } else {
                        root = leftNode;
                    }
                    currentNode.height -= 2;

                } else if (leftHeight < rightHeight && rightleftHeight < rightrightHeight) { // right right
                    currentNode.children[1] = rightNode.children[0];
                    rightNode.children[0] = currentNode;

                    if(storePath.hasWork()) {
                        AVLNode prevNode = (AVLNode) storePath.next();
                        if (prevNode.children[0] == currentNode) {
                            prevNode.children[0] = rightNode;
                        } else {
                            prevNode.children[1] = rightNode;
                        }
                    } else {
                        root = rightNode;
                    }
                    currentNode.height -= 2;
                } else if (leftHeight > rightHeight && leftleftHeight < leftrightHeight) { // left right
                    AVLNode midpoint = (AVLNode)leftNode.children[1];
                    currentNode.children[0] = leftNode.children[1].children[1];
                    leftNode.children[1] = leftNode.children[1].children[0];
                    midpoint.children[0] = leftNode;
                    midpoint.children[1] = currentNode;

                    if(storePath.hasWork()) {
                        AVLNode prevNode = (AVLNode) storePath.next();
                        if (prevNode.children[0] == currentNode) {
                            prevNode.children[0] = midpoint;
                        } else {
                            prevNode.children[1] = midpoint;
                        }
                    } else {
                        root = midpoint;
                    }
                    leftNode.height--;
                    midpoint.height++;
                    currentNode.height -= 2;

                } else { // right left
                    AVLNode midpoint = (AVLNode)rightNode.children[0];
                    currentNode.children[1] = rightNode.children[0].children[0];
                    rightNode.children[0] = rightNode.children[0].children[1];
                    midpoint.children[1] = rightNode;
                    midpoint.children[0] = currentNode;

                    if(storePath.hasWork()) {
                        AVLNode prevNode = (AVLNode) storePath.next();
                        if (prevNode.children[0] == currentNode) {
                            prevNode.children[0] = midpoint;
                        } else {
                            prevNode.children[1] = midpoint;
                        }
                    } else {
                        root = midpoint;
                    }
                    rightNode.height--;
                    midpoint.height++;
                    currentNode.height -= 2;
                }
                break;
            }
        }
        return null;
    }

}
