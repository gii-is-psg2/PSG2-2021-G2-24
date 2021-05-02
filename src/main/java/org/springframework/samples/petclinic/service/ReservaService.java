package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

	private ReservaRepository reservaRepo;

	@Autowired
	public ReservaService(ReservaRepository reservaRepo) {
		this.reservaRepo = reservaRepo;
	}

	@Transactional
	public int restaurantReservationCount() {
		return (int) reservaRepo.count();
	}

	@Transactional
	public void save(Reserva reserva) {
		reservaRepo.save(reserva);
	}

	@Transactional
	public void delete(Reserva reserva) {
		reservaRepo.delete(reserva);
	}

	public Iterable<Reserva> findAll() {
		return reservaRepo.findAll();
	}

	public Authorities getAuthority(String username) {
		return reservaRepo.getAuthority(username);
	}

	@Transactional
	public Collection<Pet> findPets() {

		return reservaRepo.findPets();
	}

	@Transactional
	public Collection<Owner> findOwners() {

		return reservaRepo.findOwners();
	}

	@Transactional
	public Collection<Room> findRooms() {

		return reservaRepo.findRooms();
	}

	@Transactional
	public Optional<Reserva> getReservaById(int id) {
		return this.reservaRepo.findById(id);
	}

	public Boolean bookingSamePet(Reserva reserva) {
		Boolean b = false;
		for (Reserva r : findAll()) {
			if (r.getPet().getId().equals(reserva.getPet().getId()) && !b) {
				b = diasSolapados(reserva, r);
			}
		}
		return b;
	}

	public Boolean alreadyBooked(Reserva reserva) {
		Boolean resultado = false;
		for (Reserva res : findAll()) {
			if (res.getRoom().equals(reserva.getRoom()) && !resultado) {
				resultado = diasSolapados(reserva, res);
			}
		}
		return resultado;
	}

	private Boolean diasSolapados(Reserva res1, Reserva res2) {
		LocalDate fechaInicial1 = res1.getStartDate();
		LocalDate fechaInicial2 = res2.getStartDate();
		LocalDate fechaFinal1 = res1.getEndingDate();
		LocalDate fechaFinal2 = res2.getEndingDate();

		if ((fechaInicial1.isAfter(fechaInicial2) && fechaInicial1.isBefore(fechaFinal2))
				|| (fechaFinal1.isAfter(fechaInicial2) && fechaFinal1.isBefore(fechaFinal2))) {
			return true;
		}
		if ((fechaInicial2.isAfter(fechaInicial1) && fechaInicial2.isBefore(fechaFinal1))
				|| (fechaFinal2.isAfter(fechaInicial1) && fechaFinal2.isBefore(fechaFinal1))) {
			return true;
		}
		if (fechaInicial1.isEqual(fechaInicial2) && fechaFinal1.isEqual(fechaFinal1)) {
			return true;
		}
		return false;
	}
}
