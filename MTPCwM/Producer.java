package MTPCwM;

import java.util.Random;


public class Producer extends Thread {


    private final Object condProd;
    private final Object condCons;
    Random rand = new Random();

    public Producer(Object condProd, Object condCons) {
        this.condProd = condProd;
        this.condCons = condCons;
    }

    @Override
    public void run() {
        int value = 0;

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value = rand.nextInt(1000) + 1;
            synchronized (condProd) {
                while (Main.list.size() == Main.capacity) {
                    try {
                        condProd.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Item produced = " + Main.list.add(value));
            }
            synchronized (condCons) {
                condCons.notifyAll();
            }
        }
    }
}
