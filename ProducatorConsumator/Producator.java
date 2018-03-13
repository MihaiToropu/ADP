package ProducatorConsumator;

import java.util.concurrent.locks.Lock;

public class Producator extends Thread {

    Lock lock;

    public Producator(Lock lock) {
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
            synchronized (lock) {
                if (Main.list.size() < Main.capacity) {
                    System.out.println("Producer produced value " + Main.list.add(value++));
                }
            }
        }
    }
}