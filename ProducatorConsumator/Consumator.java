package ProducatorConsumator;


import java.util.concurrent.locks.Lock;

public class Consumator extends Thread {

    Lock lock;

    public Consumator(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        int val = 404;

        while (true) {
            synchronized (lock) {
                if (Main.list.size() > 0) {
                    val = Main.list.removeFirst();
                }
            }
            System.out.println("Consumer consumed value " + val);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}