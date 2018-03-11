package Semaphores;


import java.util.concurrent.Semaphore;

public class Consumer extends Thread {

    Semaphore sem;

    public Consumer(Semaphore sem) {
        this.sem = sem;
    }

    @Override
    public void run() {
        int val = 1;
        while (true) {
            if (Main.list.size() > 0){
                try{
                    sem.acquire();
                    val = Main.list.removeFirst();
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sem.release();
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumed " + val);
        }
    }
}