package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "reservas")
@Entity
public class Reserva extends BaseEntity {

	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	

	@NotNull
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	@JoinColumn(name = "start_date")
	private LocalDate startDate;
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	@JoinColumn(name = "ending_date")
	private LocalDate endingDate;
}
