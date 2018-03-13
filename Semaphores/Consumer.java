package Semaphores;


import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {

    Semaphore semFull;
    Semaphore semFree;
    Lock lock;

    public Consumer(Semaphore semFree, Semaphore semFull, Lock lock) {
        this.semFree = semFree;
        this.semFull = semFull;
        this.lock = lock;
    }

    @Override
    public void run() {
        int val = 0;

        while (true) {
            try {
                synchronized (lock) {
                    semFull.acquire();
                    val = Main.list.removeFirst();
                    semFree.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumed " + val);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}