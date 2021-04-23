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
@Table(name = "donation")
@Data
public class Donation extends BaseEntity {
	
	@NotNull
	@JoinColumn(name = "nombreDonante_id")
	String nombreDonante;

	@NotNull
	@Column(name = "importeDonacion_id")
	Integer importeDonacion;
		
	@NotNull
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	@Column(name = "fechaDonacion_id")
	private LocalDate fechaDonacion;
}
