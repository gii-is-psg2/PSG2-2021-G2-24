package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Authorities;

public interface AdoptionRequestResponseRepository extends CrudRepository<AdoptionRequestResponse, Integer> {

	@Query("SELECT ar FROM AdoptionRequest ar ORDER BY ar.id")
	List<AdoptionRequest> findAdoptionRequests() throws DataAccessException;

	@Query("SELECT  auth FROM Authorities auth WHERE auth.user.username LIKE :username")
	public Authorities getAuthority(@Param("username") String username) throws DataAccessException;

}
