package org.kodigo.service;

import org.kodigo.exception.InvalidProductException;
import org.kodigo.factory.ReservationFactory;
import org.kodigo.model.Reservation;
import org.kodigo.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	private final ReservationFactory reservationFactory;
	private final PaymentStrategy paymentStrategy;

	@Autowired
	public OrderService(ReservationFactory reservationFactory, PaymentStrategy paymentStrategy) {
		this.reservationFactory = reservationFactory;
		this.paymentStrategy = paymentStrategy;
	}

	public void processOrder(int creditCardNumber, int productCode, int quantity) {
		if(!validateProduct(productCode, quantity)) {
			throw new InvalidProductException("Invalid product information.");
		}
		Reservation reservation = reservationFactory.createReservation();
		if(paymentStrategy.pay(quantity * 100, creditCardNumber)) {
			System.out.println("Order processed successfully with reservation code: " + reservation.getReservationCode());
		} else {
			System.out.println("Order processing failed due to payment issues.");
		}
	}

	private boolean validateProduct(int productCode, int quantity) {
		return quantity > 0 && (productCode < 239 || productCode > 384);
	}
}
