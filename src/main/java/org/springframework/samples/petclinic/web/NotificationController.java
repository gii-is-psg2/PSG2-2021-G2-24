package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notificationService;
	private final OwnerService ownerService;

	@Autowired
	public NotificationController(NotificationService notificationService, OwnerService ownerService) {
		this.notificationService = notificationService;
		this.ownerService = ownerService;
	}

	@GetMapping("/list")
	public String createnotaccept(ModelMap modelMap) {
		String username = UserUtils.getUser();
		Authorities authority = notificationService.getAuthority(username);
		List<Notification> notifications;
		if (authority.getAuthority().equals("owner")) {
			notifications = this.notificationService
					.findNotificationsByOwner(this.ownerService.findByUserName(username).getId());
		} else {
			notifications = StreamSupport.stream(notificationService.findAll().spliterator(), false)
					.collect(Collectors.toList());
		}
		modelMap.addAttribute("notifications", notifications);

		return "Notifications/notificationList";
	}
}
