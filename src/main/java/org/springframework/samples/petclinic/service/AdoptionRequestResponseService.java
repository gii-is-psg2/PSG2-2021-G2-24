package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.AdoptionRequestResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionRequestResponseService {

	private AdoptionRequestResponseRepository adoptReqresprepo;

	@Autowired
	public AdoptionRequestResponseService(AdoptionRequestResponseRepository adoptReqresprepo) {
		this.adoptReqresprepo = adoptReqresprepo;
	}

	@Transactional
	public int restaurantReservationCount() {
		return (int) adoptReqresprepo.count();
	}

	@Transactional
	public void save(AdoptionRequestResponse adoptionrequestresponse) {
		adoptReqresprepo.save(adoptionrequestresponse);
	}

	@Transactional
	public void delete(AdoptionRequestResponse adoptionrequestresponse) {
		adoptReqresprepo.delete(adoptionrequestresponse);
	}

	@Transactional
	public Iterable<AdoptionRequestResponse> findAll() {
		return adoptReqresprepo.findAll();
	}

	@Transactional
	public Collection<AdoptionRequest> findAdoptionRequests() {

		return adoptReqresprepo.findAdoptionRequests();
	}

	public Authorities getAuthority(String username) {
		return adoptReqresprepo.getAuthority(username);
	}

	@Transactional
	public Collection<Owner> findOwners() {
		return adoptReqresprepo.findOwners();
	}

}
