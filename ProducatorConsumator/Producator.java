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
            synchronized (this) {
                if (Main.list.size() == 0 || Main.list.size() <= Main.capacity) {
                    //lock.lock();

                    System.out.println("Producer produced value " + value);
                    Main.list.add(value);
                    ++value;
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
    }

}
