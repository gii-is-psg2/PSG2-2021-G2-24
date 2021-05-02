package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;

public interface DonationRepository extends CrudRepository<Donation, Integer> {

	@Query("SELECT  auth FROM Authorities auth WHERE auth.user.username LIKE :username")
	public Authorities getAuthority(@Param("username") String username) throws DataAccessException;

	@Query("SELECT causa FROM Causa causa ORDER BY causa.id")
	List<Causa> findCausas() throws DataAccessException;

	@Query("SELECT owner FROM Owner owner ")
	List<Owner> findOwners() throws DataAccessException;

	@Query("SELECT donation FROM Donation donation ORDER BY donation.id")
	List<Donation> findDonations() throws DataAccessException;

}
