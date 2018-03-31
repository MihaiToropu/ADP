package Leader;

import mpi.*;

import java.util.Random;


public class Main {
    public static void main(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int[] buffer = new int[2];
        int[] result = new int[2];
        int leader;

        Random random = new Random();
        int randomNumber = 0;
        int stop = 0;


        if (0 != rank) {
            buffer[0] = rank;
            buffer[1] = random.nextInt(10);
            System.out.println("Rank = " + rank + " number is " + buffer[1]);
            MPI.COMM_WORLD.Send(buffer, 0, 2, MPI.INT, 0, 0);
        } else {
            if (0 == stop) {
                randomNumber = random.nextInt(10);
                stop = 1;
                result[0] = 0;   //rank
                result[1] = randomNumber;       //randomNumber
                System.out.println("Rank = " + rank + " number is " + result[1]);
            }
            for (int i = 1; i < size; i++) {
                MPI.COMM_WORLD.Recv(buffer, 0, 2, MPI.INT, i, 0);
                if (buffer[1] > result[1]) {
                    result[0] = buffer[0]; //rank
                    result[1] = buffer[1]; //random Number of rank
                }
            }
            leader = result[0];
            System.out.println("Rank leader = " + result[0]);
            System.out.println("Biggest number = " + result[1]);
            System.out.println("I am rank " + rank + " and rank " + leader + " is my leader");
            for (int i = 1; i < size; i++) {
                MPI.COMM_WORLD.Send(result, 0, 2, MPI.INT, i, 0);
            }
        }

        if (0 != rank) {
            MPI.COMM_WORLD.Recv(result, 0, 2, MPI.INT, 0, 0);
            leader = result[0];
            System.out.println("I am rank " + rank + " and rank " + leader + " is my leader");
        }


        MPI.Finalize();
    }
}
