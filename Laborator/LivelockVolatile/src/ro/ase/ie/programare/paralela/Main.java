package ro.ase.ie.programare.paralela;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		ThreadTombola tombola = new ThreadTombola();
		ThreadJucator gigel = new ThreadJucator("Gigel", tombola, 13);
		ThreadJucator ana = new ThreadJucator("Ana", tombola, 25);
		ThreadJucator bob = new ThreadJucator("Bob", tombola, 40);
		
		tombola.start();
		gigel.start();
		ana.start();
		bob.start();
		
		Thread.sleep(20000);
		
		tombola.stopJoc();
		
		tombola.join();
		gigel.join();
		ana.join();
		bob.join();
		
		System.out.println("Sfarsit joc");
		
	}

}
