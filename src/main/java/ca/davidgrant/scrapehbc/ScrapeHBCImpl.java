package ca.davidgrant.scrapehbc;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import ca.davidgrant.scrapehbc.ImmutableAccount.Builder;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class ScrapeHBCImpl implements Scraper {
	private HtmlUnitDriver driver;
	
	public ScrapeHBCImpl() {
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38, true);
	}

	@Override
	public Account getBalance(String userName, String password) {
		driver.get("http://www.hbc.com/eservice");
		
//		System.out.println("*** Sending username");
		WebElement usernameElement = driver.findElementByCssSelector("#user");
		usernameElement.sendKeys(userName);
		
//		System.out.println("*** Sending password");
		WebElement passwordElement = driver.findElementByCssSelector("#pass");
		passwordElement.sendKeys(password);
		
//		System.out.println("*** Clicking login button");
		WebElement loginButtonElement = driver.findElementByCssSelector("#loginAction");
		loginButtonElement.click();
		
//		System.out.println("*** Page source=\n" + driver.getPageSource());

		Builder accountBuilder = ImmutableAccount.builder();
		List<WebElement> findElementsByClassName = driver.findElementsByClassName("titletextactsummary");
		for (WebElement webElement : findElementsByClassName) {
			WebElement columnleftheader = webElement.findElement(By.className("columnleftheader"));
			String script = "return arguments[0].innerHTML";

			String key = (String) ((JavascriptExecutor) driver).executeScript(script, columnleftheader);
//			System.out.print("key=" + key + " ");
			
			WebElement columncenterheader = webElement.findElement(By.className("columncenterheader"));
		    String value = (String) ((JavascriptExecutor) driver).executeScript(script, columncenterheader);
//			System.out.println("value=" + value);

			if (key.startsWith("Account Number")) {
				accountBuilder.name(value);
			} else if (key.startsWith("Balance as of")) {
				accountBuilder.balance(parseDecimal(value));
			} else if (key.startsWith("Minimum Payment Due")) {
				accountBuilder.minimumPayment(parseDecimal(value));
			} else {
				throw new RuntimeException("Shouldn't get here");
			}
		}
		accountBuilder.timestamp(DateTime.now());
		driver.quit();
		return accountBuilder.build();
	}
	
	private BigDecimal parseDecimal(String decimal) {
		while (decimal.charAt(0) == '$' || decimal.charAt(0) == ' ') {
			decimal = decimal.substring(1);
		}
		return new BigDecimal(decimal);
	}
}
