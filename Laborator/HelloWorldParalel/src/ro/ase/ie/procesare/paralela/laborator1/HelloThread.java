package ro.ase.ie.procesare.paralela.laborator1;

public class HelloThread extends Thread{

	@Override
	public void run() {
		System.out.println("Hello World din thread-ul " + 
	Thread.currentThread().getName());
	}
	
	
}
