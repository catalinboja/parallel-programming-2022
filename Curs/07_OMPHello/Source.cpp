#include <iostream>
#include <omp.h>
using namespace std;


int main() {

	int nrProcesoare = omp_get_num_procs();

	std::cout << endl << "Nr core-uri " << nrProcesoare;

	int idMainThread = omp_get_thread_num();
	std::cout << endl << "ID main thread = " << idMainThread;



#pragma omp parallel 
	{
		int idThread = omp_get_thread_num();
		printf("\n Hello world %d!", idThread);
	}

	int nrThreaduri = omp_get_num_threads();

	std::cout << endl << "Numar thread-uri active = " << nrThreaduri;

	omp_set_num_threads(4);


	bool rulamParalel = omp_in_parallel();
	std::cout << endl << "Rulam in paralel: " << rulamParalel;


#pragma omp parallel
	{
		int id = omp_get_thread_num();

		if (id == 0) {
			int nrThreaduri = omp_get_num_threads();
			std::cout << endl << "Numar thread-uri active = " << nrThreaduri;

			bool rulamParalel = omp_in_parallel();
			std::cout << endl << "Rulam in paralel: " << rulamParalel;
		}

		nrThreaduri = omp_get_num_threads();
		std::cout << endl << "Numar thread-uri active = " << nrThreaduri;

		printf("\n Bye %d!", id);
	}

	int nrMaxThreaduri = omp_get_max_threads();

	cout << endl << "Numar maxim thread-uri: " << nrMaxThreaduri;

	std::cout << endl << "Sfarsit exemplu";
}