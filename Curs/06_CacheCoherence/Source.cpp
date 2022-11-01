#include <iostream>
#include <vector>
#include <thread>
#include <string>
#include <omp.h>

using namespace std;

void counter(long N, int& rezultat) {
	rezultat = 0;
	for (long i = 0; i < N; i++) {
		if (i % 2 == 0) {
			rezultat += 1;
		}
	}
}

void testParalel(long N) {
	int nrProcesoare = omp_get_num_procs();
	int* rezultate = new int[nrProcesoare];
	vector<thread> threaduri;
	double tStart = omp_get_wtime();
	for (int i = 0; i < nrProcesoare; i++) {
		threaduri.push_back(
			thread(counter, N, ref(rezultate[i])));
	}
	for (int i = 0; i < nrProcesoare; i++) {
		threaduri[i].join();
	}
	double tFinal = omp_get_wtime();
	cout << endl << "Durata test " << tFinal - tStart;
}

//solutie care evita cache coherence deoarece 
//	variabilele nu mai sunt pe aceeasi linie de cache
void testParalel2(long N) {
	int nrProcesoare = omp_get_num_procs();
	int* rezultate = new int[nrProcesoare*1000];
	vector<thread> threaduri;
	double tStart = omp_get_wtime();
	for (int i = 0; i < nrProcesoare; i++) {
		threaduri.push_back(
			thread(counter, N, ref(rezultate[i*1000])));
	}
	for (int i = 0; i < nrProcesoare; i++) {
		threaduri[i].join();
	}
	double tFinal = omp_get_wtime();
	cout << endl << "Durata test " << tFinal - tStart;
}

int main() {

	//solutie pentru C/C++
	alignas(1024) int vb1;
	alignas(1024) int vb2;

	const long N = 1e10;
	cout << endl << "Start ...";
	testParalel2(N);
	testParalel(N);

}