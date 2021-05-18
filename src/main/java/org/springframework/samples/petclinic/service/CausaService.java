package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
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
	public int causationCount() {
		return (int) causaRepo.count();
	}

	@Transactional
	public void save(Causa causa) {
		causaRepo.save(causa);
	}

	@Transactional
	public void delete(Causa causa) {
		causaRepo.delete(causa);
	}

	public Iterable<Causa> findAll() {
		return causaRepo.findAll();
	}

	public Authorities getAuthority(String username) {
		return causaRepo.getAuthority(username);
	}

	@Transactional
	public Collection<Owner> findOwners() {

		return causaRepo.findOwners();
	}

	@Transactional
	public Optional<Causa> getCausaById(int id) throws DataAccessException {
		return causaRepo.findById(id);
	}

	@Transactional
	public Collection<Donation> findDonations(int causaId) {

		return causaRepo.findDonations();
	}

	public Boolean sameName(Causa causa) {
		Boolean resultado = false;
		for (Causa res : findAll()) {
			if (res.getName().equals(causa.getName()) && !resultado) {
				resultado = mismaCausa(causa, res);
			}
		}
		return resultado;
	}

	private Boolean mismaCausa(Causa cau1, Causa cau2) {
		if (cau1.getId().equals(cau2.getId())) {
			return false;
		} else {
			if (cau1.getName().equals(cau2.getName())) {
				return true;
			}
		}
		return false;
	}
}