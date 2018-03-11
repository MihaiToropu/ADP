package Semaphores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class Main {

    public static LinkedList<Integer> list = new LinkedList<>();
    public static int capacity = 5;

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<>();
        Semaphore sem = new Semaphore(5);
        Producer p = new Producer(sem);
        Consumer c = new Consumer(sem);

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
