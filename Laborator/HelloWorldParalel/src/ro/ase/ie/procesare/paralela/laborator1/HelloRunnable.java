package ro.ase.ie.procesare.paralela.laborator1;

public class HelloRunnable implements Runnable{

	@Override
	public void run() {
		System.out.println("Hello World din alt thread " + 
	Thread.currentThread().getName());
	}

}
