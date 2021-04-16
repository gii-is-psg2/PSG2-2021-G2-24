package org.springframework.samples.petclinic.model;



import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;



import lombok.Data;



@Table(name = "causas")
@Entity
@Data
public class Causa extends BaseEntity {
	
	@NotNull
	@JoinColumn(name = "nameCausa_id")
	String nameCausa;
	
	@NotNull
	@JoinColumn(name = "descriptionCausa_id")
	String descriptionCausa;

	@NotNull
	@JoinColumn(name = "budgetTarget_id")
	Integer budgetTarget;
	
	@NotNull
	@JoinColumn(name = "ActivenpOrganization_id")
	String ActivenpOrganization;
	

}
