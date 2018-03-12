package Semaphores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;


public class Producer extends Thread {

    Semaphore semFull;
    Semaphore semFree;
    Lock lock;

    public Producer(Semaphore semFree, Semaphore semFull, Lock lock) {
        this.semFree = semFree;
        this.semFull = semFull;
        this.lock = lock;
    }

    @Override
    public void run() {
        int value = 0;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                semFree.acquire();
                if (Main.list.size() < Main.capacity) {
                    synchronized (lock) {
                        Main.list.add(value);
                        System.out.println("Produced value " + value++);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semFull.release();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
