package ro.ase.ie.procesare.paralela.laborator1;

import java.util.Random;

public class ThreadClient extends Thread{

	String nume;
	ContBancar contBancar;
	
	public ThreadClient(String nume, ContBancar contBancar) {
		super();
		this.nume = nume;
		this.contBancar = contBancar;
	}

	@Override
	public void run() {
		
		while(this.contBancar.balanta > 0) {
			Random random = new Random();
			int suma = random.nextInt(10);
			System.out.println(this.nume + " doreste o plata de " + suma);
			this.contBancar.plateste(suma);
		}

	}
	
	
	
	
	
}
