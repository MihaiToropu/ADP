package ProducatorConsumator;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consumator extends Thread {

    Lock lock;

    public Consumator(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        int val;
        while (true) {
            synchronized (this) {
                if (Main.list.size() > 0) {
                    System.out.println("Consumer consumed value " + Main.list.removeFirst());
                } else {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
