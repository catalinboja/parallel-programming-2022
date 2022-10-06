package ro.ase.ie.procesare.paralela.laborator1;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello World de pe thread-ul principal");
		
		Thread t1 = new HelloThread();
		Thread t2 = new HelloThread();
		
		Thread t3 = new Thread(new HelloRunnable());
		Thread t4 = new Thread(new HelloRunnable());
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
			
		System.out.println("Final exemplu");
	}

}
