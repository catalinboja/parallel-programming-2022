package ro.ase.ie.programare.paralela;

public class ThreadVerificareLocala extends Thread{
	long valoareInitiala;
	long limitaSuperioara;
	int dimSalt;
	long nrPrime;
	
	public ThreadVerificareLocala(long valoareInitiala, long limitaSuperioara, int dimSalt) {
		super();
		this.valoareInitiala = valoareInitiala;
		this.limitaSuperioara = limitaSuperioara;
		this.dimSalt = dimSalt;
	}
	
	@Override
	public void run() {
		double tStart = System.currentTimeMillis();
		long valoareCurenta = valoareInitiala;
		while(valoareCurenta <= limitaSuperioara) {
			if(Main.estePrim(valoareCurenta)) {
				this.nrPrime+=1;
			}
			valoareCurenta += dimSalt;
		}
		double tFinal = System.currentTimeMillis();
		System.out.println("Durata thread: " + (tFinal-tStart)/1000);
	}
	
	public long getNrPrime() {
		return this.nrPrime;
	}
	
	
}
