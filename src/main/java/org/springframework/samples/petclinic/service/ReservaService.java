package org.springframework.samples.petclinic.service;

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

	@Transactional
	public Iterable<Reserva> findAll() {
		return reservaRepo.findAll();
	}

	public Authorities getAuthority(String username) {
		// TODO Auto-generated method stub

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
		if ((res1.getStartDate().isAfter(res2.getStartDate()) && res1.getStartDate().isBefore(res2.getEndingDate()))
				|| (res1.getEndingDate().isAfter(res2.getStartDate())
						&& res1.getEndingDate().isBefore(res2.getEndingDate()))) {
			return true;
		}
		if ((res2.getStartDate().isAfter(res1.getStartDate()) && res2.getStartDate().isBefore(res1.getEndingDate()))
				|| (res2.getEndingDate().isAfter(res1.getStartDate())
						&& res2.getEndingDate().isBefore(res1.getEndingDate()))) {
			return true;
		}
		if (res1.getStartDate().isEqual(res2.getStartDate()) && res1.getEndingDate().isEqual(res1.getEndingDate())) {
			return true;
		} else {
			return false;
		}
	}
}
