package experiments;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import datastructures.worklists.CircularArrayFIFOQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashingFunctionsExp {

    public static void main(String[] args) throws FileNotFoundException {
        goodHashWithInput(false);
        goodHashWithInput(true);

        badHashWithInput(false);
        badHashWithInput(true);

    }


    public static void goodHashWithInput(boolean real) throws FileNotFoundException {
        ChainingHashTable<AlphabeticString, Integer> goodHash = new ChainingHashTable<>(MoveToFrontList::new);

        File file = new File("corpus/spoken.corpus");
        Scanner itr = new Scanner(file);

        int times = 0;
        while (itr.hasNext() && times < 30){
            times++;
            // String line = itr.nextLine();
            // Scanner itrIn = new Scanner(line);
            int sizeOfLineinsert = 0;
            int sizeOfLinefind = 0;
            long startinsert = System.nanoTime();
            while(itr.hasNext() && sizeOfLineinsert < times) {
                sizeOfLineinsert++;
                char[] word = itr.next().toCharArray();
                CircularArrayFIFOQueue<Character> charArray = new CircularArrayFIFOQueue<>(word.length);
                for (char i : word) {
                    charArray.add(i);
                }
                goodHash.insert(new AlphabeticString(charArray), word.length);

            }
            long endinsert = System.nanoTime();

            // line = itr.nextLine();
            // itrIn = new Scanner(line);
            long startfind = System.nanoTime();
            while (itr.hasNext() && sizeOfLinefind < times) {
                sizeOfLinefind++;

                char[] word_new = itr.next().toCharArray();
                CircularArrayFIFOQueue<Character> charArray_new = new CircularArrayFIFOQueue<>(word_new.length);
                for (char j : word_new) {
                    charArray_new.add(j);
                }
                goodHash.find(new AlphabeticString(charArray_new));
            }
            long endfind = System.nanoTime();
            if(real) {
                System.out.println("good hash insert size : " + sizeOfLineinsert + " uses " + (endinsert - startinsert) + " ns");
                System.out.println("good hash find size : " + sizeOfLinefind + " uses " + (endfind - startfind) + " ns");
            }
        }
    }

    public static void badHashWithInput(boolean real) throws FileNotFoundException {
        ChainingHashTable<AlphabeticString, Integer> badHash = new ChainingHashTable<>(MoveToFrontList::new);

        File file = new File("corpus/spoken.corpus");
        Scanner itr = new Scanner(file);

        int times = 0;
        while (itr.hasNext() && times < 30) {
            times++;
            //String line = itr.nextLine();
            //Scanner itrIn = new Scanner(line);
            long startinsert = System.nanoTime();
            int sizeOfLineinsert = 0;
            int sizeOfLinefind = 0;
            while (itr.hasNext()  && sizeOfLineinsert < times) {
                sizeOfLineinsert++;
                char[] word = itr.next().toCharArray();
                CircularArrayFIFOQueueBad<Character> charArray = new CircularArrayFIFOQueueBad<>(word.length);
                for (char i : word) {
                    charArray.add(i);
                }
                badHash.insert(new AlphabeticString(charArray), word.length);
            }
            long endinsert = System.nanoTime();

            //line = itr.nextLine();
            //itrIn = new Scanner(line);
            long startfind = System.nanoTime();
            while (itr.hasNext()  && sizeOfLinefind < times) {
                sizeOfLinefind++;

                char[] word_new = itr.next().toCharArray();
                CircularArrayFIFOQueueBad<Character> charArray_new = new CircularArrayFIFOQueueBad<>(word_new.length);
                for (char j : word_new) {
                    charArray_new.add(j);
                }
                badHash.find(new AlphabeticString(charArray_new));
            }
            long endfind = System.nanoTime();
            if(real) {
                System.out.println("bad hash insert size : " + sizeOfLineinsert + " uses " + (endinsert - startinsert) + " ns");
                System.out.println("bad hash find size : " + sizeOfLinefind + " uses " + (endfind - startfind) + " ns");
            }
        }
    }
}
