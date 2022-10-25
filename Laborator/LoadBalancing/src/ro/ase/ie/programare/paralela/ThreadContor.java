package ro.ase.ie.programare.paralela;

public class ThreadContor extends Thread{

	long limitaInferioara;
	long limitaSuperioara;
	Contor contor;
	

	public ThreadContor(long limitaInferioara, long limitaSuperioara, Contor contor) {
		super();
		this.limitaInferioara = limitaInferioara;
		this.limitaSuperioara = limitaSuperioara;
		this.contor = contor;
	}


	@Override
	public void run() {
		double tStart = System.currentTimeMillis();
		int nrPrime = Main.getNrPrime(limitaInferioara, limitaSuperioara);
		this.contor.adauga(nrPrime);
		double tFinal = System.currentTimeMillis();
		System.out.println(
				"Durata Thread " + Thread.currentThread().getName() + 
				": " + (tFinal - tStart)/1000);
		
	}
}
