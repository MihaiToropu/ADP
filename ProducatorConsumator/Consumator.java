package ProducatorConsumator;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consumator extends Thread{

    Lock lock = new ReentrantLock();

    public Consumator(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        int val;
        while (true) {
            synchronized (lock) {
                if (Main.list.size() > 0) {
                    lock.lock();
                    try {
                        val = Main.list.removeFirst();
                        System.out.println("Consumer consumed value " + val);
                    } finally {
                        lock.unlock();
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}