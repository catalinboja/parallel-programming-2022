package ro.ase.ie.programare.paralela;

import java.util.Random;

public class ThreadTombola extends Thread{
	
	private int nrNorocExtras;
	private volatile boolean stopJoc = false;
	
	@Override
	public void run() {
		while(!this.stopJoc) {
		   Random random = new Random();
		   try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   this.nrNorocExtras = random.nextInt(50) + 1;
		   System.out.println("Numarul extras este " + this.nrNorocExtras);
		}
		System.out.println("Tombola s-a inchis");
	}
	
	public int getNrNorocos() {
		return this.nrNorocExtras;
	}
	
	public void stopJoc() {
		this.stopJoc = true;
	}
	
	public boolean esteOprita() {
		return this.stopJoc;
	}
	
	
	
	
}
