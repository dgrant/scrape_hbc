package ca.davidgrant.scrapehbc;

import java.util.logging.Level;

public class Main {
	public static void main(String[] args) {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		Scraper scrapeHBC = new ScrapeHBCImpl();
		Account accountHBC = scrapeHBC.getBalance("XXXX", "XXXX");
		
		System.out.println("*** Account: " + accountHBC.toString());
	}
}
