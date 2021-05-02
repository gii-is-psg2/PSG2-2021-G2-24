package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

	private NotificationRepository notificationRepository;

	@Autowired
	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Transactional
	public int notificationcount() {
		return (int) notificationRepository.count();
	}

	@Transactional
	public void save(Notification notif) {
		notificationRepository.save(notif);
	}

	@Transactional
	public void delete(Notification notif) {
		notificationRepository.delete(notif);
	}

	@Transactional
	public Iterable<Notification> findAll() {
		return notificationRepository.findAll();
	}

	@Transactional
	public Optional<Notification> findById(int id) {
		return notificationRepository.findById(id);
	}

	@Transactional
	public Collection<AdoptionRequestResponse> findAdoptionRequestResponses() {

		return notificationRepository.findAdoptionRequestResponses();
	}

	@Transactional
	public Authorities getAuthority(String username) {
		return notificationRepository.getAuthority(username);
	}

	@Transactional
	public List<Notification> findNotificationsByOwner(int ownerId) {
		return notificationRepository.notificationsByOwner(ownerId);
	}

}
