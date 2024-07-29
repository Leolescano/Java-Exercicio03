package org.kodigo.factory;

import org.kodigo.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationFactory {
	public Reservation createReservation() {
		return new Reservation();
	}
}