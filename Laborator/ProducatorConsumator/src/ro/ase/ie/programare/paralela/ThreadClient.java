package ro.ase.ie.programare.paralela;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ThreadClient extends Thread{
	Market market;

	public ThreadClient(Market market) {
		this.market = market;
	}
	
	@Override
	public void run() {
		while(!this.market.esteInchisa()) {
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Random random = new Random();
			int cantitate = random.nextInt(100);
			System.out.println("Clientul consuma " + cantitate);
			try {
				this.market.cumparaProduse(cantitate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
