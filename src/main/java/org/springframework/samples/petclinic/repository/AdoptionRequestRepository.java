package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;

public interface AdoptionRequestRepository extends CrudRepository<AdoptionRequest, Integer> {

	@Query("SELECT arr FROM AdoptionRequestResponse arr ORDER BY arr.id")
    List<AdoptionRequestResponse> findAdoptionRequestResponse() throws DataAccessException;
}
