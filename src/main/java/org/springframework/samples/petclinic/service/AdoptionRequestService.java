package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionRequestService {

	private AdoptionRequestRepository adoptReqrepo;

	@Autowired
	public AdoptionRequestService(AdoptionRequestRepository adoptReqrepo) {
		this.adoptReqrepo = adoptReqrepo;
	}

	@Transactional
	public int restaurantReservationCount() {
		return (int) adoptReqrepo.count();
	}

	@Transactional
	public void save(AdoptionRequest adoptionrequest) {
		adoptReqrepo.save(adoptionrequest);
	}

	@Transactional
	public void delete(AdoptionRequest adoptionrequest) {
		adoptReqrepo.delete(adoptionrequest);
	}

	@Transactional
	public Iterable<AdoptionRequest> findAll() {
		return adoptReqrepo.findAll();
	}

	@Transactional
	public Optional<AdoptionRequest> findById(int id) {
		return adoptReqrepo.findById(id);
	}

	public Authorities getAuthority(String username) {
		// TODO Auto-generated method stub

		return adoptReqrepo.getAuthority(username);
	}

	@Transactional
	public Collection<Owner> findOwners() {

		return adoptReqrepo.findOwners();
	}

	@Transactional
	public Collection<Pet> findPets() {

		return adoptReqrepo.findPets();
	}

	@Transactional
	public List<AdoptionRequest> findActive() {

		return adoptReqrepo.findAdoptionsActive();
	}

}
