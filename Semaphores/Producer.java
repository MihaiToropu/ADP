package Semaphores;

import java.util.concurrent.Semaphore;


public class Producer extends Thread {

    Semaphore sem;

    public Producer(Semaphore sem) {
        this.sem = sem;
    }

    @Override
    public void run() {
        int value = 0;
        while (true) {
            if (Main.list.size() < Main.capacity) {
                try {
                    sem.acquire();
                    Main.list.add(value);
                    System.out.println("Produced value " + value++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sem.release();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
