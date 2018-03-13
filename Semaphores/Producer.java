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
                synchronized (lock) {
                    semFree.acquire();
                    System.out.println("Produced value " + Main.list.add(value++));
                    semFull.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
