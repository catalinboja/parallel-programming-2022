package ro.ase.ie.programare.paralela;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static boolean estePrim(long numar) {
		boolean estePrim = true;
		for(long i = 2; i <= numar/2; i++) {
			if(numar % i == 0) {
				estePrim = false;
				break;
			}
		}
		return estePrim;
	}
	
	public static int getNrPrime(long limitaInferioara, long limitaSuperioara) {
		int contor = 0;
		for(long i = limitaInferioara; i <limitaSuperioara; i++ ) {
			if(estePrim(i)) {
				contor +=1;
			}
		}
		return contor;
	}

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Start...");
		
		final long N = (long) 3e5;
		
		double tStart = System.currentTimeMillis();
		int nrPrime = getNrPrime(0, N);
		double tStop = System.currentTimeMillis();
		
		System.out.println("Solutie secventiala");
		System.out.println("Nr prime: " + nrPrime);
		System.out.println("Durata: " + (tStop-tStart)/1000);
		
		int nrProcesoare = Runtime.getRuntime().availableProcessors();
		System.out.println("Nr core-uri: " + nrProcesoare);
		
		//varianta cu thread-uri + contor global
		Contor contor = new Contor();
		List<Thread> threaduri = new ArrayList<>();
		for(int i = 0; i < nrProcesoare; i++) {
			int dimInterval = (int) (N / nrProcesoare);
			long limitaI = i*dimInterval;
			long limitaS = (i+1)*dimInterval;
			if(i == nrProcesoare - 1) {
				limitaS = N;
			}
			threaduri.add(new ThreadContor(limitaI, limitaS, contor));
		}
		
		tStart = System.currentTimeMillis();
		for(Thread t : threaduri) {
			t.start();
		}
		for(Thread t: threaduri) {
			t.join();
		}
		tStop = System.currentTimeMillis();
		
		System.out.println("Solutie paralela cu contor global");
		System.out.println("Nr prime: " + contor.getValoare());
		System.out.println("Durata: " + (tStop-tStart)/1000);

		//varianta cu thread-uri care parcurg tot intervalul impreuna
		contor = new Contor();
		threaduri.clear();
		for(int i = 0; i< nrProcesoare; i++) {
			threaduri.add(new ThreadVerificare(contor,2*i+1, N, 2*nrProcesoare));
		}
		
		tStart = System.currentTimeMillis();
		for(Thread t : threaduri) {
			t.start();
		}
		for(Thread t: threaduri) {
			t.join();
		}
		tStop = System.currentTimeMillis();
		
		System.out.println("Solutie paralela cu contor global si fara intervale");
		System.out.println("Nr prime: " + contor.getValoare());
		System.out.println("Durata: " + (tStop-tStart)/1000);
		
		//varianta cu thread-uri care parcurg tot intervalul impreuna
		//contor este independent in fiecare thread
		threaduri.clear();
		for(int i = 0; i< nrProcesoare; i++) {
			threaduri.add(new ThreadVerificareLocala(2*i+1, N, 2*nrProcesoare));
		}
		
		tStart = System.currentTimeMillis();
		for(Thread t : threaduri) {
			t.start();
		}
		for(Thread t: threaduri) {
			t.join();
		}
		tStop = System.currentTimeMillis();
		
		long rezultat = 0;
		for(Thread t : threaduri) {
			ThreadVerificareLocala thread = (ThreadVerificareLocala)t;
			rezultat += thread.getNrPrime();
		}
		
		System.out.println("Solutie paralela cu contor global si fara intervale");
		System.out.println("Nr prime: " + rezultat);
		System.out.println("Durata: " + (tStop-tStart)/1000);
	}

}
