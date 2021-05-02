package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "causas")
public class Causa extends NamedEntity {

	@NotNull
	@NotEmpty
	@Column(name = "descriptionCausa_id")
	private String descriptionCausa;

	@NotNull
	@Column(name = "budgetTarget_id")
	private Double budgetTarget;

	@NotNull
	@NotEmpty
	@Column(name = "activenpOrganization_id")
	private String activenpOrganization;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "causa", fetch = FetchType.EAGER)
	private Set<Donation> donations;

	private Double totalDonation;

	private Boolean closed;

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getDescriptionCausa() {
		return descriptionCausa;
	}

	public void setDescriptionCausa(String descriptionCausa) {
		this.descriptionCausa = descriptionCausa;
	}

	public Double getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public String getActivenpOrganization() {
		return activenpOrganization;
	}

	public void setActivenpOrganization(String s) {
		activenpOrganization = s;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}

	public Double getTotalDonation() {
		return totalDonation;
	}

	public void setTotalDonation(Double totalDonation) {
		this.totalDonation = totalDonation;
	}

}
