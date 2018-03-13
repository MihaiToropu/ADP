package Semaphores;


import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {

    Semaphore semFull;
    Semaphore semFree;
    Lock lock;
    Random rand = new Random();

    public Consumer(Semaphore semFree, Semaphore semFull, Lock lock) {
        this.semFree = semFree;
        this.semFull = semFull;
        this.lock = lock;
    }

    @Override
    public void run() {
        int randomNumber;
        int val = 0;

        while (true) {
            try {
                semFull.acquire();
                randomNumber = rand.nextInt(4);
                synchronized (lock) {
                    while (Main.list.size() > randomNumber) {
                        lock.lock();
                        val = Main.list.remove(randomNumber);
                        System.out.println("Consumed " + val);
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semFree.release();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}