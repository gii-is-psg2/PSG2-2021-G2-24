package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DonationValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Donation donation = (Donation) obj;

		if (donation.getImporteDonacion() <= 0.00) {
			errors.rejectValue("importe", "Value is required", "Value is required");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Donation.class.isAssignableFrom(clazz);
	}
}
