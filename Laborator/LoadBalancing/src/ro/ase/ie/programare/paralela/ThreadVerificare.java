package ro.ase.ie.programare.paralela;

public class ThreadVerificare extends Thread{
	Contor contor;
	long valoareInitiala;
	long limitaSuperioara;
	int dimSalt;
	
	public ThreadVerificare(Contor contor, long valoareInitiala, long limitaSuperioara, int dimSalt) {
		super();
		this.contor = contor;
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
				contor.increment();
			}
			valoareCurenta += dimSalt;
		}
		double tFinal = System.currentTimeMillis();
		System.out.println("Durata thread: " + (tFinal-tStart)/1000);
	}
	
	
}
