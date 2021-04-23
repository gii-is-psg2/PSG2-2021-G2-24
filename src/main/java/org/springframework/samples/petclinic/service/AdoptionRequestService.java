package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
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
	
}
