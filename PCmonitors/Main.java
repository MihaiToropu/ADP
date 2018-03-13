package PCmonitors;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

    public static LinkedList<Integer> list = new LinkedList<>();
    public static final int capacity = 5;

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<>();

        Lock lock = new ReentrantLock();
        final Object condProd = new Object();
        final Object condCons = new Object();
        Producer p = new Producer(lock, condProd, condCons);
        Consumer c = new Consumer(lock, condProd, condCons);


        threadList.add(p);
        threadList.add(c);

        for (Thread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
