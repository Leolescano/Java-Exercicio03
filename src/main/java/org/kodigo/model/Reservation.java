package org.kodigo.model;

import java.util.UUID;

public class Reservation {
	private UUID reservationCode;

	public Reservation() {
		this.reservationCode = UUID.randomUUID();
	}

	public UUID getReservationCode() {
		return reservationCode;
	}
}
