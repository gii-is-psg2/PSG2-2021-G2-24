package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Room;

public interface ReservaRepository extends CrudRepository<Reserva, Integer> {

	@Query("SELECT  auth FROM Authorities auth WHERE auth.user.username LIKE :username")
	public Authorities getAuthority(@Param("username") String username) throws DataAccessException;

	@Query("SELECT pet FROM Pet pet ORDER BY pet.id")
	List<Pet> findPets() throws DataAccessException;

	@Query("SELECT owner FROM Owner owner ORDER BY owner.id")
	List<Owner> findOwners() throws DataAccessException;

	@Query("SELECT room FROM Room room ORDER BY room.id")
	List<Room> findRooms() throws DataAccessException;
}
