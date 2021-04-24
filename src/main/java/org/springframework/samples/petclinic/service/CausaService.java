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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausaService {

	private CausaRepository causaRepo;

	@Autowired
	public CausaService(CausaRepository causaRepo) {
		this.causaRepo = causaRepo;
	}

	@Transactional
	public int CausationCount() {
		return (int) causaRepo.count();
	}

	@Transactional
	public void save(Causa Causa) {
		causaRepo.save(Causa);
	}

	@Transactional
	public void delete(Causa Causa) {
		causaRepo.delete(Causa);
	}

	@Transactional
	public Iterable<Causa> findAll() {
		return causaRepo.findAll();
	}

	public Authorities getAuthority(String username) {
		// TODO Auto-generated method stub

		return causaRepo.getAuthority(username);
	}


	@Transactional
	public Collection<Owner> findOwners() {

		return causaRepo.findOwners();
	}


	@Transactional
	public Optional<Causa> getCausaById(int id) {
		return this.causaRepo.findById(id);
	}
	
	@Transactional
	public Collection<Donation> findDonations() {

		return causaRepo.findDonations();
	}
	



}
