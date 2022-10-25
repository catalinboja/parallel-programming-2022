package ro.ase.ie.programare.paralela;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Market market = new Market(500);
		Thread producator = new ThreadProducator(market);
		Thread client = new ThreadClient(market);
		
		producator.start();
		TimeUnit.SECONDS.sleep(2);
		client.start();
		
		TimeUnit.SECONDS.sleep(10);
		market.inchidePiata();
		
		producator.join();
		client.join();
		
		System.out.println("Piata se inchide");
	}

}
