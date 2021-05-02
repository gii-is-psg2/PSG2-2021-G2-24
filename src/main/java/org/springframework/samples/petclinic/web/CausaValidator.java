package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Causa;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CausaValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Causa causa = (Causa) obj;

		if (causa.getActivenpOrganization() == null || causa.getActivenpOrganization().isEmpty()) {
			errors.rejectValue("organization", "Organization name required", "Organization name required");
		}
		if (causa.getBudgetTarget() == null || causa.getBudgetTarget() > 0.0) {
			errors.rejectValue("budgetTarget", "A budget target is required", "A budget target is required");
		}
		if (causa.getDescriptionCausa() == null || causa.getDescriptionCausa().isEmpty()) {
			errors.rejectValue("budgetTarget", "A description is required", "A description is required");
		}
		if (causa.getName() == null || causa.getName().isEmpty()) {
			errors.rejectValue("budgetTarget", "Name required", "Name required");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Causa.class.isAssignableFrom(clazz);
	}
}

//src/test/java/org/springframework/samples/petclinic/model/ValidatorTests.java
