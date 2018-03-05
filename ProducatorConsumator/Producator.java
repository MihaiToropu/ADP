package ProducatorConsumator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Producator extends Thread {

    Lock lock = new ReentrantLock();

    public Producator(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        int value = 0;
        while (true) {
            synchronized (lock) {
                if (Main.list.size() == 0) {
                    lock.lock();
                    try {
                        Main.list.add(value);
                        System.out.println("Producer produced value " + value);
                        ++value;
                    } finally {
                        lock.unlock();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}