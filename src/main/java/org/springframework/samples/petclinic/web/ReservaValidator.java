package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReservaValidator implements Validator {

	private final ReservaService reservaService;

	@Autowired
	public ReservaValidator(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Reserva reserva = (Reserva) obj;

		if (reserva.getOwner() == null) {
			errors.rejectValue("owner", "Owner is required", "Owner is required");
		}
		if (reserva.getEndingDate() == null || reserva.getStartDate() == null
				|| reserva.getStartDate().isBefore(LocalDate.now())
				|| reserva.getEndingDate().isBefore(reserva.getStartDate())) {
			errors.rejectValue("endingDate",
					"La fecha final o inicial puede estar nula, ser una fecha anterior a la actual o ser una fecha anterior a la de inicio",
					"La fecha final o inicial puede estar nula, ser una fecha anterior a la actual o ser una fecha anterior a la de inicio");
		}
		if (reserva.getPet() == null) {
			errors.rejectValue("pet", "Value is required", "Value is required");
		}
		if (reserva.getRoom() == null) {
			errors.rejectValue("room", "Value is required", "Value is required");
		}
		if (this.reservaService.alreadyBooked(reserva)) {
			errors.rejectValue("room", "Room booked for another pet", "Room already booked for that time slot");
		}
		if (this.reservaService.bookingSamePet(reserva)) {
			errors.rejectValue("room", "This pet has already booked a room", "This pet has already booked a room");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Reserva.class.isAssignableFrom(clazz);
	}

}
