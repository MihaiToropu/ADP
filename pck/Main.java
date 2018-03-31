
package pck;

import mpi.*;

public class Main {

    public static void main(String[] args) {

        MPI.Init(args);
        int[] sum = new int[1];
        int totalSum = 0;
        int stop = 0;
        int j;
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int vectorSize = 8;
        int sizePerProcess = vectorSize / size;
        int[] buffer = new int[sizePerProcess];
        int[] vector = new int[vectorSize];

        if (0 == rank) {

            for (int i = 0; i < vectorSize; i++) {
                vector[i] = 5 + i;
            }
            if (0 == stop) {
                for (int k = 0; k < sizePerProcess; k++) {
                    totalSum += vector[k];
                }
                stop = 1;
                System.out.println("CPU " + rank + "a adunat " + totalSum);
            }

            for (int i = 1; i < size; i++) {
                j = 0;
                for (int k = i * sizePerProcess; k < i * sizePerProcess + sizePerProcess; k++) {
                    buffer[j] = vector[k];
                    j++;
                }
                MPI.COMM_WORLD.Send(buffer, 0, sizePerProcess, MPI.INT, i, 0);
            }
        }

        if (0 != rank) {
            MPI.COMM_WORLD.Recv(buffer, 0, sizePerProcess, MPI.INT, 0, 0);
            for (int i = 0; i < sizePerProcess; i++) {
                sum[0] += buffer[i];
            }
            System.out.println("CPU " + rank + "a adunat " + sum[0]);
            MPI.COMM_WORLD.Send(sum, 0, 1, MPI.INT, 0, 0);
        } else {
            for (int i = 1; i < 4; i++) {
                MPI.COMM_WORLD.Recv(sum, 0, 1, MPI.INT, i, 0);
                totalSum += sum[0];
            }
            System.out.println("Suma totala este " + totalSum);
        }

        MPI.Finalize();
    }
}