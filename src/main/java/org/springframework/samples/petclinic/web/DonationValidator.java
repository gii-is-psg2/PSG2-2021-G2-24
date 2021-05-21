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

		if (donation.getImporteDonacion() < 0.00) {
			errors.rejectValue("importe", "El valor debe ser mayor de 0", "El valor debe ser mayor de 0");
		}

		if (donation.getImporteDonacion().toString() != String.format("%.2f", donation.getImporteDonacion().toString())) {
			errors.rejectValue("importe", "El importe debe escribirse con 2 cifras decimales", "El importe debe escribirse con 2 cifras decimales");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Donation.class.isAssignableFrom(clazz);
	}
}
