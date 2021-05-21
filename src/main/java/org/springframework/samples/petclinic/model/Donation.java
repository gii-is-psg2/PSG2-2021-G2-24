package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "donations")

public class Donation extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@ManyToOne
	@JoinColumn(name = "causa_id")
	private Causa causa;

	@Column(name = "importeDonacion")
	@NotNull
	private Double importeDonacion;

	@Column(name = "fechaDonacion")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaDonacion;

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Causa getCausa() {
		return causa;
	}

	public void setCausa(Causa causa) {
		this.causa = causa;
	}

	public Double getImporteDonacion() {
		return importeDonacion;
	}

	public void setImporteDonacion(Double importeDonacion) {
		this.importeDonacion = importeDonacion;
	}

	public LocalDate getFechaDonacion() {
		return fechaDonacion;
	}

	public void setFechaDonacion(LocalDate fechaDonacion) {
		this.fechaDonacion = fechaDonacion;
	}

}
