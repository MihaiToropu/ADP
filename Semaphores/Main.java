package Semaphores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

    public static LinkedList<Integer> list = new LinkedList<>();
    public static int capacity = 5;

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<>();
        Semaphore semFull = new Semaphore(2);
        Semaphore semFree = new Semaphore(2);
        Lock lock = new ReentrantLock();
        Producer p = new Producer(semFree, semFull, lock);
        Consumer c = new Consumer(semFree, semFull, lock);

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