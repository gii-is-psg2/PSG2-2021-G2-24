package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

	@Query("SELECT arr FROM AdoptionRequestResponse arr ORDER BY arr.id")
	List<AdoptionRequestResponse> findAdoptionRequestResponses() throws DataAccessException;

	@Query("SELECT  auth FROM Authorities auth WHERE auth.user.username LIKE :username")
	public Authorities getAuthority(@Param("username") String username) throws DataAccessException;

	@Query("SELECT notification FROM Notification notification WHERE notification.response.owner.id = :id")
	public List<Notification> notificationsByOwner(@Param("id") int ownerId);
}
