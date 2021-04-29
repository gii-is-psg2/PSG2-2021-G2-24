package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.CausaRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {

	private DonationRepository donationRepo;
	
	@Autowired
	public DonationService(DonationRepository donationRepo) {
		this.donationRepo = donationRepo;
	}
	
	@Transactional
	public Iterable<Donation> findAll(){
		return donationRepo.findAll();
	}
	
	public Authorities getAuthority(String username) {
		// TODO Auto-generated method stub

		return donationRepo.getAuthority(username);
	}
	
	@Transactional
	public Collection<Owner> findOwners() {

		return donationRepo.findOwners();
	}
	
	@Transactional
	public Collection<Causa> findCausas(){
		return donationRepo.findCausas();
	}
	
	@Transactional
	public void save(Donation donation) {
		donationRepo.save(donation);
	}

	

}
