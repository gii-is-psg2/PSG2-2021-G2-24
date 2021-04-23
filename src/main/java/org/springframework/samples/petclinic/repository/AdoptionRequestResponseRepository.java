package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Owner;

public interface AdoptionRequestResponseRepository extends CrudRepository<AdoptionRequestResponse, Integer>{

	@Query("SELECT ar FROM AdoptionRequest ar ORDER BY ar.id")
	List<AdoptionRequest> findAdoptionRequests() throws DataAccessException;
	
}
