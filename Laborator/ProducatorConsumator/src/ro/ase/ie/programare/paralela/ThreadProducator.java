package ro.ase.ie.programare.paralela;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ThreadProducator extends Thread{

	Market market;
		
	public ThreadProducator(Market market) {
		super();
		this.market = market;
	}

	@Override
	public void run() {
		while(!this.market.esteInchisa()) {
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Random random = new Random();
			int cantitate = random.nextInt(50);
			System.out.println("Producatorul produce " + cantitate);
			try {
				this.market.adaugaProduse(cantitate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
