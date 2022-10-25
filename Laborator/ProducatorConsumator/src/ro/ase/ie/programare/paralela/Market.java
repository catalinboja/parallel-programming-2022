package ro.ase.ie.programare.paralela;

public class Market {
	private int stoc;
	private final int limitaStoc;
	private volatile boolean seInchide = false;
	
	public Market(int limitaStoc) {
		this.limitaStoc = limitaStoc;
	}
	
	public void inchidePiata() {
		this.seInchide = true;
	}
	
	public boolean esteInchisa() {
		return this.seInchide;
	}
	
	public synchronized void adaugaProduse(int cantitate) throws InterruptedException {
		this.notify();
		if(this.stoc + cantitate > this.limitaStoc) {
			System.out.println(
					"Se depaseste limita stocului. Suspendare producator");
			this.wait();
		}
		else {
			System.out.println("Stocul se completeaza cu " + cantitate);
			this.stoc += cantitate;
		}
	}
	
	public synchronized void cumparaProduse(int cantitate) throws InterruptedException {
		this.notify();
		if(this.stoc - cantitate < 0) {
			System.out.println("Stoc epuizat. Suspendare consumator");
			this.wait();
		} else {
			System.out.println("Stocul scade cu " + cantitate);
			this.stoc -= cantitate;
		}
	}
	
}
