package ca.davidgrant.scrapehbc;

import org.junit.Before;
import org.junit.Test;

public class ScrapePCImplTest {
	private Scraper scrape;
	
	@Before
	public void setUp() {
		scrape = new ScrapeHBCImpl();
	}
	
	@Test
	public void test() {
		Account account = scrape.getBalance("dgrant", "PFY767bF");
		System.out.println("*** Account: " + account.toString());
	}
}
