package org.kodigo.strategy;

public interface PaymentStrategy {
	boolean pay(int amount, int creditCardNumber);
}
