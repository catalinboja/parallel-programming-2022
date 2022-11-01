#include <iostream>
#include <thread>
#include <omp.h>
#include <string>

using namespace std;

static bool esteMaiMic(int a, int b) {
	int rez = 0;

	for (int i = 0; i < 3200; i++) {
		float temp = (float)a / i;
		rez += (int)temp;
	}

	return (a <= b);
}

int minim(int* vector, int n) {
	int min = vector[0];
	for (int i = 1; i < n; i++) {
		if (esteMaiMic(vector[i], min)) {
			min = vector[i];
		}
	}
	return min;
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

}