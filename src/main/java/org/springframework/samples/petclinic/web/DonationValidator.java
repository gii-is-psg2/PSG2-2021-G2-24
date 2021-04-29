package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DonationValidator implements Validator {
	

	private final DonationService donationService;
	
	@Autowired
	public DonationValidator(DonationService donationService) {
		this.donationService = donationService;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Donation donation = (Donation) obj;

		if (donation.getImporteDonacion()<= 0.00) {
			errors.rejectValue("importe", "Value is required", "Value is required");
		}


	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Donation.class.isAssignableFrom(clazz);
	}

	
}
