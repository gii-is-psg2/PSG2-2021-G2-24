package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Owner;
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
	public Collection<AdoptionRequestResponse> findAdoptionRequestResponse() {

		return adoptReqrepo.findAdoptionRequestResponse();
	}
	@Transactional
	public Collection<AdoptionRequestResponse> findAdoptionRequestResponsebyAdoptionRequest(AdoptionRequest adoptionRequest) {
		List<AdoptionRequestResponse> adoptRRfilter = new ArrayList<AdoptionRequestResponse>();
		List<AdoptionRequestResponse> adoptRR = adoptReqrepo.findAdoptionRequestResponse();
		for(AdoptionRequestResponse arr : adoptRR ) {
			if(arr.getAdoptionrequest().getId() == adoptionRequest.getId()) {
				adoptRRfilter.add(arr);
			}
		}
		return adoptRRfilter;
	}
	
}
