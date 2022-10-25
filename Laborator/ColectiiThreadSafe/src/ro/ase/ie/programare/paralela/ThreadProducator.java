package ro.ase.ie.programare.paralela;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class ThreadProducator extends Thread{
	LinkedBlockingDeque<Integer> coadaMesaje;
	
	
	public ThreadProducator(LinkedBlockingDeque<Integer> coadaMesaje) {
		super();
		this.coadaMesaje = coadaMesaje;
	}

	@Override
	public void run() {
		while(true) {
			Random random = new Random();
			int valoare = random.nextInt(100);
			try {
				
				this.coadaMesaje.put(valoare);
				System.out.println("S-a adaugat valoarea " + valoare);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
