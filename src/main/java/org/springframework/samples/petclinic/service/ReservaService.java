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
	
	private  ReservaRepository reservaRepo; 

	@Transactional
	public int restaurantReservationCount() {
		return (int)reservaRepo.count();
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
	@Autowired
	public ReservaService(ReservaRepository reservaRepo) {
		this.reservaRepo = reservaRepo;
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

}
