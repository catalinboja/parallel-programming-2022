#include <iostream>
#include <omp.h>
using namespace std;

int main() {
	omp_set_num_threads(20);

	int nrCoreuri = omp_get_num_procs();

	cout << endl << "Nr core-uri " << nrCoreuri;

#pragma omp parallel num_threads(4) if(nrCoreuri > 10)
	{
		int id = omp_get_thread_num();
		cout << endl << "Hello from " << id;
	}

#pragma omp parallel num_threads(2) if(nrCoreuri < 10)
	{
		int id = omp_get_thread_num();
		cout << endl << "Salut from " << id;
	}

	int contor = 0;

	//contor este implicit shared - shared(contor)
#pragma omp parallel num_threads(4)
	{
		for(int i = 0; i <  100000; i++)
			contor += 1;
	}

	cout << endl << "Contor dupa secventa paralela " << contor;

	contor = 100;

#pragma omp parallel num_threads(4) private(contor)
	{
		//contor e local si e implicit 0
		for (int i = 0; i < 100000; i++)
			contor += 1;
		cout << endl << "Contor pentru " << omp_get_thread_num()
			<< " este " << contor;
	}

	cout << endl << "Contor dupa secventa paralela " << contor;

	contor = 99;

#pragma omp parallel num_threads(4) firstprivate(contor)
	{
		//contor e local si e implicit 0
		for (int i = 0; i < 100000; i++)
			contor += 1;
		cout << endl << "Contor pentru " << omp_get_thread_num()
			<< " este " << contor;
	}

	cout << endl << "Contor dupa secventa paralela " << contor;

	int vb1 = 10;
	int vb2 = 20;
	int vb3 = 30;
	int vb4 = 40;

	omp_set_num_threads(20);

#pragma omp parallel shared(vb1) private(vb2,vb3) firstprivate(vb4)
	{
		vb1 += 1;
		vb2 += 1;
		vb3 += 1;
		vb4 += 1;

		//cout << endl << "*vb2 = " << vb2;
		//cout << endl << "*vb3 = " << vb3;
		//cout << endl << "*vb4 = " << vb4;

		printf("\n *vb2 = %d", vb2);
		printf("\n *vb3 = %d", vb3);
		printf("\n *vb4 = %d", vb4);
	}

	cout << endl << "vb1 = " << vb1;
	cout << endl << "vb2 = " << vb2;
	cout << endl << "vb3 = " << vb3;
	cout << endl << "vb4 = " << vb4;

#pragma omp parallel shared(vb1) private(vb2, vb3) firstprivate(vb4) default(none)
	{
		vb1 += 1;
		vb2 += 1;
		vb3 += 1;
		vb4 += 1;
	}

	//critical si barrier
	contor = 0;
#pragma omp parallel num_threads(4)
	{
		for (int i = 0; i < 100000; i++) {
			//printf("\n Iteratia %d din thread-ul %d"
			//	, i, omp_get_thread_num());

#pragma omp critical
			{
				contor += 1;
			}
		}

#pragma omp barrier

		for (int i = 0; i < 100000; i++) {
			//printf("\n Iteratia **%d** din thread-ul %d"
			//	, i, omp_get_thread_num());

#pragma omp critical
			contor += 1;
		}
	}

	cout << endl << "\n Contor final " << contor;
}