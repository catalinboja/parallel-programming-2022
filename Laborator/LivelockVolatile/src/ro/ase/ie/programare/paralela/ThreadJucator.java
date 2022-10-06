package ro.ase.ie.programare.paralela;

public class ThreadJucator extends Thread{
	String nume;
	ThreadTombola tombola;
	int nrNorocos;
	
	public ThreadJucator(String nume, ThreadTombola tombola, int nrNorocos) {
		super();
		this.nume = nume;
		this.tombola = tombola;
		this.nrNorocos = nrNorocos;
	}
	
	@Override
	public void run() {
		System.out.println(this.nume + " are numarul " + this.nrNorocos);
		while(this.nrNorocos != tombola.getNrNorocos() 
				&& !tombola.esteOprita()) {
			//asteapta
		}
		if(!tombola.esteOprita())
		System.out.println(this.nume + "a castigat ****************");
		else {
			System.out.println(this.nume + " incearca la urmatorul joc");
		}
	}
	
	
}
