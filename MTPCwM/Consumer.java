package MTPCwM;



public class Consumer extends Thread {

    private final Object condProd;
    private final Object condCons;


    public Consumer(Object condProd, Object condCons) {
        this.condProd = condProd;
        this.condCons = condCons;
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
                condProd.notifyAll();
            }
            System.out.println("Consumer consumed " + val);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}