package ro.ase.ie.programare.paralela;

import java.util.concurrent.atomic.AtomicInteger;

public class Contor {
	AtomicInteger valoare = new AtomicInteger();
	
	public void increment() {
		this.valoare.incrementAndGet();
	}
	
	public int getValoare() {
		return this.valoare.get();
	}
	
	public void adauga(int valoare) {
		this.valoare.addAndGet(valoare);
	}
}
