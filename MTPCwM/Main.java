package MTPCwM;

import java.util.LinkedList;


public class Main {

    public static LinkedList<Integer> list = new LinkedList<>();
    public static final int capacity = 50;

    public static void main(String[] args) {


        final Object condProd = new Object();
        final Object condCons = new Object();

        Thread[] producer = new Thread[10];
        Thread[] consumer = new Thread[10];


        for (int i = 0; i < 10; i++) {
            producer[i] = new Producer(condProd, condCons);
            consumer[i] = new Consumer(condProd, condCons);
        }

        for (int i = 0; i < 10; i++) {
            producer[i].start();
            consumer[i].start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                producer[i].join();
                consumer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

