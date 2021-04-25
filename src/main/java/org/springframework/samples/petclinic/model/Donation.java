package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaDonacion;
}
