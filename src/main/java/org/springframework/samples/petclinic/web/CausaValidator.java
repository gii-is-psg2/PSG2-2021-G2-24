package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CausaValidator implements Validator {
	

	private final CausaService causaService;
	
	@Autowired
	public CausaValidator(CausaService causaService) {
		this.causaService = causaService;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Causa causa = (Causa) obj;
		
		if (causa.getOwner() == null) {
			errors.rejectValue("owner", "Owner is required", "Owner is required");
		}

		if (causa.getActivenpOrganization() == null) {
			errors.rejectValue("organization", "Value is required", "Value is required");
		}
		
		if (causa.totalDonation> 0.00) {
			errors.rejectValue("totalDonation", "Value is required", "Value is required");
		}
		if (causa.getBudgetTarget() == null) {
			errors.rejectValue("budgetTarget", "Value is required", "Value is required");
		}
		if (causa.getDescriptionCausa() == null) {
			errors.rejectValue("budgetTarget", "Value is required", "Value is required");
		}
		if (causa.getName() == null) {
			errors.rejectValue("budgetTarget", "Value is required", "Value is required");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Causa.class.isAssignableFrom(clazz);
	}

	
}
