package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionRequestRepository;
import org.springframework.samples.petclinic.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NotificationService {
	
	private NotificationRepository notifrepo;

	@Autowired
	public NotificationService(NotificationRepository notifrepo) {
		this.notifrepo = notifrepo;
	}

	@Transactional
	public int notificationcount() {
		return (int) notifrepo.count();
	}

	@Transactional
	public void save(Notification notif) {
		notifrepo.save(notif);
	}

	@Transactional
	public void delete(Notification notif) {
		notifrepo.delete(notif);
	}

	@Transactional
	public Iterable<Notification> findAll() {
		return notifrepo.findAll();
	}

	@Transactional
	public Optional<Notification> findById(int id) {
		return notifrepo.findById(id);
	}
	@Transactional
	public Collection<AdoptionRequestResponse> findAdoptionRequestResponses() {

		return notifrepo.findAdoptionRequestResponses();
	}
}
