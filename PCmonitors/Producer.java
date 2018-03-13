package PCmonitors;

import java.util.Random;
import java.util.concurrent.locks.Lock;


public class Producer extends Thread {


    Lock lock;
    private final Object condProd;
    private final Object condCons;
    Random rand = new Random();

    public Producer(Lock lock, Object condProd, Object condCons) {
        this.lock = lock;
        this.condProd = condProd;
        this.condCons = condCons;
    }

    @Override
    public void run() {
        int value = 0;

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value = rand.nextInt(1000) + 1;
            synchronized (condProd) {
                if (Main.list.size() == Main.capacity) {
                    try {
                        condProd.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (condCons) {
                System.out.println("Item produced = " + Main.list.add(value));
                condCons.notify();
            }
        }
    }
}
