package ro.ase.ie.procesare.paralela.laborator1;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ContBancar contBancar = new ContBancar(1000);
		Thread t1 = new ThreadClient("Gigel", contBancar);
		Thread t2 = new ThreadClient("Ana", contBancar);
		
		t2.start();
		t1.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Final simulare");
	}

}
