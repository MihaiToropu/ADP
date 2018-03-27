
package pck;

import mpi.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Double> myList = new ArrayList<Double>();
        myList.add(5.0);
        myList.add(6.1);
        myList.add(7.2);
        myList.add(8.3);
        myList.add(9.5);
        myList.add(10.6);
        myList.add(11.2);
        myList.add(12.9);


        MPI.Init(args);
        Double[] sum = new Double[1];
        Double totalSum = 0.0;
        int stop = 0;
        int rank = MPI.COMM_WORLD.Rank();
        System.out.println("THIS IS THE RANK " + rank);
        int size = MPI.COMM_WORLD.Size();
        System.out.println("THIS IS THE SIZE: 4 = " + size);
        int sizePerProcess = myList.size() / 4;
        int lowerBound = rank * sizePerProcess;
        int upperBound = lowerBound + sizePerProcess;

        if (0 == rank) {
            if (0 == stop) {
                for (int k = 0; k < sizePerProcess; k++) {
                    totalSum += myList.get(k);
                }
            }
        }

        if (rank != 0) {
            lowerBound = rank * sizePerProcess;
            upperBound = lowerBound + sizePerProcess;
            System.out.println("LowerBound is " + lowerBound);
            System.out.println("upperBound is " + upperBound);
            for (int i = lowerBound; i < upperBound; i++) {

                sum[0] =+ myList.get(i);
            }
            System.out.println("CPU " + rank + "a adunat " + sum[0]);
            MPI.COMM_WORLD.Send(sum[0], 0, 1, MPI., 0, 0);
        }

        if (0 == rank) {
            System.out.println("Suma totala este " + totalSum);
            for (int i = 1; i < 4; i++) {
                MPI.COMM_WORLD.Recv(sum[0], 0, 1, MPI.DOUBLE, i, 0);
                totalSum += (Double)sum[0];
            }
        }
        MPI.Finalize();
    }
}