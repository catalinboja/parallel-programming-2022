#include <iostream>
#include <omp.h>
#include <vector>
#include <string>
using namespace std;

void benchmark(string descriere, long (*pf)(long*, long), 
	long N, long* valori)
{
	cout << endl << descriere;
	double tStart = omp_get_wtime();
	long rezultat = pf(valori, N);
	double tFinal = omp_get_wtime();
	cout << endl << "Rezultat: " << rezultat;
	cout << endl << "Durata: " << tFinal - tStart << " s";
}

bool estePrim(long N) {
	bool estePrim = true;
	for (long i = 2; i <= N / 2; i++) {
		if (N % i == 0) {
			estePrim = false;
			break;
		}
	}
	return estePrim;
}

//varianta secventiala
long getNrPrime(long* valori, long N) {
	long contor = 0;
	for (long i = 0; i < N; i++) {
		if (estePrim(i))
			contor += 1;
	}
	return contor;
}

//varianta paralela
long getNrPrimeParalel(long* valori, long N) {
	long contor = 0;

#pragma omp parallel
	{
		int dimensiuneInterval = N / omp_get_num_threads();
		int id = omp_get_thread_num();
		long indexStart = id * dimensiuneInterval;
		long indexFinal = 
			(id == omp_get_num_threads()-1) ? 
				N : (id + 1) * dimensiuneInterval;
		for (long i = indexStart; i < indexFinal; i++) {
			if (estePrim(i))
#pragma omp critical
				contor += 1;
		}
	}
	return contor;
}

//varianta paralela cu load balancing
// 1/2 din thread-uri verifica numere pare
long getNrPrimeParalelLoadBalancing(long* valori, long N) {
	long contor = 0;
#pragma omp parallel
	{
		int id = omp_get_thread_num();
		for (long i = id; i < N; i+= omp_get_num_threads()) {
			if (estePrim(i))
#pragma omp critical
				contor += 1;
		}
	}
	return contor;
}

//varianta paralela cu load balancing
long getNrPrimeParalelLoadBalancingImpare(long* valori, long N) {
	long contor = 0;

	//ne asiguram ca folosim un nr par de thread-uri
	int nrProcesoare = omp_get_num_procs();
	int nrThreaduri = 
		nrProcesoare % 2 == 0 ? nrProcesoare : nrProcesoare - 1;

#pragma omp parallel num_threads(nrThreaduri)
	{
		int id = omp_get_thread_num();
		for (long i = (2*id)+1; i < N; i += 2* nrThreaduri) {
			if (estePrim(i))
#pragma omp critical
				contor += 1;
		}
	}
	return contor;
}

//varianta paralela cu load balancing fara mutex
long getNrPrimeParalelLoadBalancingImpareFaraMutex(long* valori, long N) {
	long contor = 0;

	//ne asiguram ca folosim un nr par de thread-uri
	int nrProcesoare = omp_get_num_procs();
	int nrThreaduri =
		nrProcesoare % 2 == 0 ? nrProcesoare : nrProcesoare - 1;

	int* rezultate = new int[nrThreaduri*100];
	memset(rezultate, 0, nrThreaduri * 100 * sizeof(int));

#pragma omp parallel num_threads(nrThreaduri)
	{
		int id = omp_get_thread_num();
		for (long i = (2 * id) + 1; i < N; i += 2 * nrThreaduri) {
			if (estePrim(i))
				rezultate[id*100] += 1;
		}
	}

	for (int i = 0; i < nrThreaduri; i++) {
		contor += rezultate[i*100];
	}

	return contor;
}

int main() {
	const long N = 4e5;
	long* valori = new long[N];
	for (long i = 0; i < N; i++) {
		valori[i] = i + 1;
	}

	cout << endl << "Start teste";
	benchmark("Solutie secventiala", getNrPrime, 
		N, valori);
	benchmark("Solutie paralela", getNrPrimeParalel, 
		N, valori);
	benchmark("Solutie paralela cu load balancing",
		getNrPrimeParalelLoadBalancing,
		N, valori);

	//diferenta de 2 nr pentru ca nu verifica 0 si 2
	benchmark("Solutie paralela cu load balancing",
		getNrPrimeParalelLoadBalancingImpare,
		N, valori);

	benchmark("Solutie paralela cu load balancing",
		getNrPrimeParalelLoadBalancingImpareFaraMutex,
		N, valori);
}

