#include <iostream>
#include <thread>
#include <omp.h>
#include <string>
#include <vector>
#include <mutex>

using namespace std;

//definim un mutex global
mutex mutexGlobal;

static bool esteMaiMic(int a, int b) {
	int rez = 0;

	for (int i = 0; i < 3200; i++) {
		float temp = (float)a / i;
		rez += (int)temp;
	}

	return (a <= b);
}

//solutia secventiala
int minim(int* vector, int n) {
	int min = vector[0];
	for (int i = 1; i < n; i++) {
		if (esteMaiMic(vector[i], min)) {
			min = vector[i];
		}
	}
	return min;
}

void getMinimInterval(int* valori, int indexStart, int indexFinal, int& rezultat) {
	rezultat = valori[indexStart];
	for (int i = indexStart; i < indexFinal; i++) {
		if (esteMaiMic(valori[i], rezultat)) {
			rezultat = valori[i];
		}
	}
}

void getMinimIntervalCuMutex(int* valori, int indexStart, int indexFinal, int& rezultat) {
	rezultat = valori[indexStart];
	for (int i = indexStart; i < indexFinal; i++) {
		mutexGlobal.lock();
		if (esteMaiMic(valori[i], rezultat)) {
			//posibil race condition
			//mutexGlobal.lock();
			rezultat = valori[i];
			//mutexGlobal.unlock();
		}
		mutexGlobal.unlock();
	}
}

//solutie paralela cu race condition
int minimParalelCuRaceCondition(int* valori, int n) {
	int nrProcesoare = omp_get_num_procs();
	vector<thread> threaduri;

	int minimVector;
	int dimInterval = n / nrProcesoare;
	for (int i = 0; i < nrProcesoare; i++) {
		int indexStart = i*dimInterval;
		int indexFinal =  (i == nrProcesoare-1) ? n : (i + 1) * dimInterval;
		
		threaduri.push_back(
			thread(getMinimInterval, valori, 
				indexStart, indexFinal, ref(minimVector)));
	}

	for (int i = 0; i < nrProcesoare; i++) {
		threaduri[i].join();
	}
	return minimVector;

}

//solutie paralela cu mutex
int minimParalelCuMutex(int* valori, int n) {
	int nrProcesoare = omp_get_num_procs();
	vector<thread> threaduri;

	int minimVector;
	int dimInterval = n / nrProcesoare;
	for (int i = 0; i < nrProcesoare; i++) {
		int indexStart = i * dimInterval;
		int indexFinal = (i == nrProcesoare - 1) ? n : (i + 1) * dimInterval;

		threaduri.push_back(
			thread(getMinimIntervalCuMutex, valori,
				indexStart, indexFinal, ref(minimVector)));
	}

	for (int i = 0; i < nrProcesoare; i++) {
		threaduri[i].join();
	}
	return minimVector;

}

//solutie paralela cu minime locale
int minimParalelCuMinimeLocale(int* valori, int n) {
	int nrProcesoare = omp_get_num_procs();
	vector<thread> threaduri;

	int* minimeLocale = new int[nrProcesoare];

	int dimInterval = n / nrProcesoare;
	for (int i = 0; i < nrProcesoare; i++) {
		int indexStart = i * dimInterval;
		int indexFinal = (i == nrProcesoare - 1) ? n : (i + 1) * dimInterval;

		threaduri.push_back(
			thread(getMinimInterval, valori,
				indexStart, indexFinal, ref(minimeLocale[i])));
	}

	for (int i = 0; i < nrProcesoare; i++) {
		threaduri[i].join();
	}
	int minimVector = minimeLocale[0];
	for (int i = 0; i < nrProcesoare; i++) {
		if (minimVector > minimeLocale[i])
			minimVector = minimeLocale[i];
	}
	return minimVector;

}

void benchmark(
	string numeTest, int (*functie)(int*, int), int* valori, int n)
{
	cout << endl << numeTest;
	double tStart = omp_get_wtime();
	int rezultat = functie(valori, n);
	double tFinal = omp_get_wtime();
	cout << endl << "Rezultat = " << rezultat;
	cout << endl << "Durata = " << (tFinal - tStart) << "s";
}

int* generareVector(int N) {
	int* vector = new int[N];
	for (int i = 0; i < N; i++) {
		vector[i] = N - i;
	}
	return vector;
}

int main() {

	const int N = 1e6;
	int* vector = generareVector(N);

	benchmark("Test solutie secventiala", minim, vector, N);
	benchmark("Test solutie paralela cu race condition", minimParalelCuRaceCondition, vector, N);
	benchmark("Test solutie paralela cu mutex", minimParalelCuMutex, vector, N);
	benchmark("Test solutie paralela cu minime locale", minimParalelCuMinimeLocale, vector, N);

}