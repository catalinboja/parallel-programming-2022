package ro.ase.ie.programare.paralela;

public class Persoana {

	String nume;

	public Persoana(String nume) {
		super();
		this.nume = nume;
	}
	
	public String getNume() {
		return this.nume;
	}
	
	//deadlock cu synchronized
	public void saluta(Persoana prieten) {
		System.out.println(this.nume + " saluta pe "+ prieten.getNume());
		prieten.raspundeSalut();
	}
	
	//deadlock cu synchronized
	public void raspundeSalut() {
		System.out.println(this.nume + " raspunde cu Hello");
	}
	
	
}
