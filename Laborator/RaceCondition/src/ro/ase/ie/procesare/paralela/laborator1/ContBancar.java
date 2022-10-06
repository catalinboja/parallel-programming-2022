package ro.ase.ie.procesare.paralela.laborator1;

public class ContBancar {
	
	double balanta;
	
	public ContBancar(double balanta) {
		super();
		this.balanta = balanta;
	}

	public synchronized void plateste(int suma) {
		System.out.println("Se doreste o plata de " + suma);
		if(suma <= this.balanta) {
			System.out.println("S-a platit suma de " + suma);
			this.balanta -= suma;
			System.out.println("Suma disponibila " + this.balanta);
		}
	}
	
}
