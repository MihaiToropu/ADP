package PCmonitors;


import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {

    private final Object condProd;
    private final Object condCons;

    Lock lock;

    public Consumer(Lock lock, Object condProd, Object condCons) {
        this.condProd = condProd;
        this.condCons = condCons;
        this.lock = lock;
    }

    @Override
    public void run() {
        int val = 0;

        while (true) {
            synchronized (condCons) {
                if (Main.list.size() == 0){
                    try {
                        condCons.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (condProd){
                val = Main.list.removeFirst();
                condProd.notify();
            }
            System.out.println("Consumer consumed " + val);

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}