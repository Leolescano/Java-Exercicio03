package org.kodigo.strategy;

import org.kodigo.exception.InvalidCreditCardException;
import org.springframework.stereotype.Component;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy{
	@Override
	public boolean pay(int amount, int creditCardNumber) {
		if(amount <= 0 || String.valueOf(creditCardNumber).startsWith("4000") ||
				creditCardNumber < 4111 || creditCardNumber > 4222) {
			throw new InvalidCreditCardException("Invalid credit card information.");
		}
		System.out.println("Payment successful!");
		return true;
	}
}
