
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/adoptionrequests")
public class AdoptionRequestController {

	private final AdoptionRequestService adoptionRequestService;

	@Autowired
	public AdoptionRequestController(AdoptionRequestService adoptioReqser) {
		this.adoptionRequestService = adoptioReqser;
	}
	
	private Authorities getAuthorities(String userName) {
		return adoptionRequestService.getAuthority(userName);
	}

	@GetMapping("/list")
	public String getAdoptionRequestListed(ModelMap modelMap) {
		List<AdoptionRequest> adoptions = adoptionRequestService.findActive();

		modelMap.addAttribute("adoptionRequests", adoptions);
		return "adoptionrequests/adoptionRequestList";
	}

	@GetMapping(path = "/new")
	public String createAdoptionrequest(ModelMap modelMap) {
		String view = "adoptionrequests/addAdoptionrequest";
		modelMap.addAttribute("adoptionrequest", new AdoptionRequest());
		return view;
	}

	@PostMapping()
	public String saveAdoptionrequest(@Valid AdoptionRequest adoptionRequest,
			@RequestParam("owner.user.username") String username, @RequestParam("pet.name") String petName,
			BindingResult result, ModelMap modelMap) {
		String adoptionReq = "adoptionrequest";
		String view = "redirect:/adoptionrequests/list";
		if (result.hasErrors()) {
			modelMap.addAttribute(adoptionReq, adoptionRequest);
			return "redirect:/adoptionrequests/new";
		} else {
			Boolean stop = false;
			for (Pet pet : this.adoptionRequestService.findPets()) {
				if (pet.getName().equals(petName) && !stop) {
					if (pet.isAdoption()) {
						modelMap.addAttribute(adoptionReq, adoptionRequest);
						modelMap.addAttribute("message", "this pet is already in adoption");
						return "adoptionrequests/addAdoptionrequest";
					}
					adoptionRequest.setPet(pet);
					pet.setAdoption(true);
					stop = true;
				}
			}
			for (Owner owner : this.adoptionRequestService.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					adoptionRequest.setOwner(owner);
				}
			}
			adoptionRequestService.save(adoptionRequest);
			modelMap.addAttribute("message", "Request successfully saved!");
		}
		return view;
	}

	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {

		List<String> usernames = new ArrayList<>();
		String username = UserUtils.getUser();
		Authorities authority = getAuthorities(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner o : adoptionRequestService.findOwners()) {
				if (o.getUser().getUsername().equals(username)) {
					usernames.add(o.getUser().getUsername());
				}
			}
		} else if (authority.getAuthority().equals("admin")) {
			for (Owner o : adoptionRequestService.findOwners()) {
				usernames.add(o.getUser().getUsername());
			}
		}
		return usernames;
	}

	@ModelAttribute("pets")
	public Collection<String> populatePets() {
		List<String> petstostr = new ArrayList<>();
		List<Pet> pets = new ArrayList<>();
		String username = UserUtils.getUser();
		Authorities authority = getAuthorities(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : adoptionRequestService.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					pets = new ArrayList<>();
					for (Pet pet : this.adoptionRequestService.findPets()) {
						if (pet.getOwner().getId().equals(owner.getId())) {
							pets.add(pet);
						}
					}
				}
			}
		} else {
			pets = StreamSupport.stream(adoptionRequestService.findPets().spliterator(), false)
					.collect(Collectors.toList());
		}
		for (Pet pet : pets) {
			petstostr.add(pet.getName());
		}
		return petstostr;
	}
}

