package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

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
	public Optional<AdoptionRequestResponse> findById(int id) {
		return  adoptReqresprepo.findById(id);
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
	public Collection<AdoptionRequest> findAdoptionRequestResponses() {

		return adoptReqresprepo.findAdoptionRequests();
	}

	public Authorities getAuthority(String username) {
		return adoptReqresprepo.getAuthority(username);
	}
	
	public void deActiveRequests(AdoptionRequest adoptionRequest) {
		for(AdoptionRequestResponse adoptionRequestResponse:adoptionRequest.getAdoptionRequestResponse()){
			adoptionRequestResponse.setIsactive(false);
			adoptReqresprepo.save(adoptionRequestResponse);
		}
	}
}	
