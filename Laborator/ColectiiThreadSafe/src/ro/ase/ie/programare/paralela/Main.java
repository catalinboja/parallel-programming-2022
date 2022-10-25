package ro.ase.ie.programare.paralela;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		//ArrayList nu este thread safe
		//ArrayList<Integer> valori = new ArrayList<>();
		Vector<Integer> valori = new Vector<>();
		
		Thread t1 = new Thread(new Runnable() {	
			@Override
			public void run() {			
				for(int i = 0; i < 500; i++) {
					valori.add(i+1);
				}		
			}
		});
		
		Thread t2 = new Thread(new Runnable() {			
			@Override
			public void run() {
				for(int i = 501; i <=1000; i++) {
					valori.add(i);
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		//colectie concurenta - thread safe - blocanta
		LinkedBlockingDeque<Integer> coadaMesaje = 
				new LinkedBlockingDeque<>(50);
		
		System.out.println(valori);
		System.out.println("Numar valori = " + valori.size());
		
		ThreadProducator producator = new ThreadProducator(coadaMesaje);
		ThreadProcesator procesator = new ThreadProcesator(coadaMesaje);
		
		producator.start();
		//procesator.start();
		
		producator.join();
		procesator.join();
		
	}

}
