package ro.ase.ie.programare.paralela;

import java.util.concurrent.LinkedBlockingDeque;

public class ThreadProcesator extends Thread{
	
	LinkedBlockingDeque<Integer> coada;

	public ThreadProcesator(LinkedBlockingDeque<Integer> coada) {
		super();
		this.coada = coada;
	};
	
	@Override
	public void run() {
		while(true) {
			try {
				int valoare = coada.take();
				System.out.println("S-a procesat valoarea " + valoare);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
