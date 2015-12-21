package ca.davidgrant.scrapehbc;

import java.math.BigDecimal;

import org.immutables.value.Value;
import org.joda.time.DateTime;

@Value.Immutable
public abstract class Account {
	@Value.Parameter
	public abstract String name();
	@Value.Parameter
	public abstract DateTime timestamp();
	@Value.Parameter
	public abstract BigDecimal balance();
	@Value.Parameter
	public abstract BigDecimal minimumPayment();
}
