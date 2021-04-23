package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "donations")
@Data
public class Donation extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToOne
	@JoinColumn(name = "causa_id")
	private Causa causa;
	
	@JoinColumn(name = "importeDonacion")
	@NotNull
	Double importeDonacion;
	
	@JoinColumn(name = "fechaDonacion")	
	@NotNull
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	private LocalDate fechaDonacion;
}
