#include <thread>
#include <iostream>
#include <vector>
#include <omp.h>
using namespace std;

void hello() {
	cout << endl << "Hello de pe alt thread";
}

void helloCuId(int threadId) {
	cout << endl << "Hello din thread-ul " << threadId;
}

void sumaVector(int* vector, int n, int& rezultat) {
	rezultat = 0;
	for (int i = 0; i < n; i++) {
		rezultat += vector[i];
	}
}

int main() {

	thread t1(hello);
	t1.join();
	thread t2(hello);
	t2.join();

	cout << endl << "-----------------";

	//construim dinamic un vector de thread-uri
	int nrProcesoare = omp_get_num_procs();
	cout << endl << "Nr core-uri: " << nrProcesoare;
	vector<thread> threaduri;
	for (int i = 0; i < nrProcesoare; i++) {
		threaduri.push_back(thread(hello));
	}
	for (int i = 0; i < nrProcesoare; i++) {
		threaduri[i].join();
	}

	cout << endl << "-----------------";

	thread t3(helloCuId, 1);
	t3.join();

	cout << endl << "-----------------";

	int suma = 0;
	int valori[] = { 10,20,30,40 };
	thread t4(sumaVector, valori, 4, ref(suma));
	t4.join();

	cout << endl << "Suma vector " << suma;

	cout << endl << "Sfarsit program";

}