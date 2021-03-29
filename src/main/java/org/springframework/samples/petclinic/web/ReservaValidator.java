package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReservaValidator implements Validator {
	
	@Override
	public void validate(Object obj, Errors errors) {
		Reserva reserva = (Reserva) obj;
		
		if (reserva.getOwner() == null) {
			errors.rejectValue("owner", "Owner is required", "Owner is required");
		}
		if (reserva.getEndingDate() == null || reserva.getEndingDate().isBefore(LocalDate.now()) ||
				reserva.getEndingDate().isBefore(reserva.getStartDate())) {
			errors.rejectValue("endingDate", "La fecha final puede estar nula, ser una fecha anterior a la actual o ser una fecha anterior a la de inicio", 
					"La fecha final puede estar nula, ser una fecha anterior a la actual o ser una fecha anterior a la de inicio");
		}
		if (reserva.getPet() == null) {
			errors.rejectValue("pet", "Value is required", "Value is required");
		}
		if (reserva.getRoom() == null) {
			errors.rejectValue("room", "Value is required", "Value is required");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Reserva.class.isAssignableFrom(clazz);
	}

	
}
