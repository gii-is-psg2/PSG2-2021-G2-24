package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notifser;

	@Autowired
	public NotificationController(NotificationService notifser) {
		this.notifser = notifser;

	}
	
	@GetMapping(path = "/accept")
	public String createnotaccept(ModelMap modelMap) {
		String view = "adoptionrequests/addAdoptionrequest";
		modelMap.addAttribute("adoptionrequest", new AdoptionRequest());
		return view;
	}
	@GetMapping(path = "/decline")
	public String createnotdelete(ModelMap modelMap) {
		String view = "adoptionrequests/addAdoptionrequest";
		modelMap.addAttribute("adoptionrequest", new AdoptionRequest());
		return view;
	}
}
