#include "mpi.h"
#include <stdio.h>
#define NPROCS 8

int main(int argc, char **argv)
{
	int elementeVector = {1, 2, 3, 4};
	
	int rank, new_rank, sendbuf, recvbuf, size,
		ranks1[4] = {0,1,2,3}, ranks2[4] = {4,5,6,7},
		sendbuf[4] = {0,1,2,3}, recvMin[1], recvMax[1];
	MPI_Group world_group, group1, group2;
	MPI_Comm comm1, comm2;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);


	if (size != NPROCS)
	{
		printf("Eroare procese\n");
		MPI_Finalize();
		exit(0);
	}

	// Get the group of processes in MPI_COMM_WORLD
	MPI_Group world_group;
	MPI_Comm_group(MPI_COMM_WORLD, &world_group);

	if (rank < NPROCS / 2)
	{

		MPI_Group_incl(world_group, NPROCS / 2, ranks1, &group1);
	} else {
		MPI_Group_incl(world_group, NPROCS / 2, ranks2, &group2);
	}

	Build intra-communicator for local sub-group
	MPI_Comm_create(MPI_COMM_WORLD, group1, &comm1);
	MPI_Comm_create(MPI_COMM_WORLD, group2, &comm2);


	//The communicator containing the process that initiates the inter-communication	
	if (0 == rank)
	{
		/* Group 1 communicates with group 2. */
		MPI_Intercomm_create(comm1, 0, MPI_COMM_WORLD, 4, 1, &comm2);
	} else if (4 == rank){
		/* Group 2 communicates with group 1. */
		MPI_Intercomm_create(comm2, 4, MPI_COMM_WORLD, 0, 1, &comm1);
	}


	MPI_reduce(&sendbuf, &recvMin, 1, MPI_INT, MPI_MIN, comm1);
	MPI_reduce(&sendbuf, &recvMax, 1, MPI_INT, MPI_MIN, comm2);
	

	MPI_Finalize();
}