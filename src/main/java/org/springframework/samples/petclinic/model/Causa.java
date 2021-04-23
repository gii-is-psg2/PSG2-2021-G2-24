package org.springframework.samples.petclinic.model;



import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



import lombok.Data;


@Entity
@Data
@Table(name = "causas")

public class Causa extends NamedEntity {
	
	
	@NotNull
	@Column(name = "descriptionCausa_id")
	private String descriptionCausa;

	@NotNull
	@Column(name = "budgetTarget_id")
	private Double budgetTarget;
	
	@NotNull
	@Column(name = "ActivenpOrganization_id")
	private String ActivenpOrganization;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "causa")
	private Set<Donation> donations;
	
	@Column(name = "total_donations")
	public static Double totalDonation(Set<Donation> donations) {		
		return donations.stream().mapToDouble(d->d.getImporteDonacion()).sum();
		
	}
	

}
