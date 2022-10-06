package ro.ase.ie.programare.paralela;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Persoana gigel = new Persoana("Gigel");
		Persoana ana = new Persoana("Ana");
		
		Thread tGigel = new Thread(new Runnable() {			
			@Override
			public void run() {
				gigel.saluta(ana);
			}
		});
		Thread tAna = new Thread(new Runnable() {			
			@Override
			public void run() {
				ana.saluta(gigel);
			}
		});
		
		tAna.start();
		tGigel.start();
		
		tAna.join();
		tGigel.join();
		
		System.out.println("Sfarsit intalnire");

	}

}
