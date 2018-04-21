package RF;

import mpi.*;


public class Main {

    public static final int NR = 5;
    public static final int INF = 99999;


    public static void main(String[] args) {

        int[][] Matrix = new int[][]{
                {0, 2, INF, 10, INF},
                {2, 0, 3, INF, INF},
                {INF, 3, 0, 1, 8},
                {10, INF, 1, 0, INF},
                {INF, INF, 8, INF, 0}
        };
        int[][] resultMatrix = new int[NR][NR];

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();


        for (int k = 0; k < NR; k++) {
            for (int i = rank; i < NR; i += size) {
                for (int j = 0; j < NR; j++) {
                    if (Matrix[i][j] > Matrix[i][k] + Matrix[k][j]) {
                        Matrix[i][j] = Matrix[i][k] + Matrix[k][j];
                    }
                    //System.out.println("Matrix[" + i + "][" + j + "] = " + Matrix[i][j]);
                }
            }
            MPI.COMM_WORLD.Gather(Matrix, rank * NR, NR, MPI.INT, resultMatrix, rank * NR, NR, MPI.INT, 0);
            MPI.COMM_WORLD.Barrier();

            //MPI.COMM_WORLD.Reduce(Matrix, rank * NR, resultMatrix, rank * NR, NR, MPI.INT, MPI.MIN, 0);
            if (rank == 0) {
                for (int i = 0; i < NR; i++) {
                    for (int j = 0; j < NR; j++) {
                        Matrix[i][j] = resultMatrix[i][j];
                    }
                }
                MPI.COMM_WORLD.Bcast(Matrix, 0, NR * NR, MPI.INT, 0);
            }
            MPI.COMM_WORLD.Barrier();
        }

        if (rank == 0){
            for (int i = 0; i < resultMatrix.length; i++) {
                for (int j = 0; j < resultMatrix[i].length; j++) {
                    System.out.print(resultMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }

        MPI.Finalize();
    }
}
