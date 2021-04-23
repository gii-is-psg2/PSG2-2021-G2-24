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
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/adoptionrequests")
public class AdoptionRequestController {

	
	private final AdoptionRequestService adoptioReqser;
	
	@Autowired
	public AdoptionRequestController(AdoptionRequestService adoptioReqser  ) {
		this.adoptioReqser = adoptioReqser;
	}
	
	@GetMapping(path = "/new")
	public String createAdoptionrequest(ModelMap modelMap) {
		String view = "adoptionrequests/addAdoptionrequest";
		modelMap.addAttribute("adoptionrequest", new AdoptionRequest());
		return view;
	}
	
	@PostMapping()
	public String saveAdoptionrequest(@Valid AdoptionRequest ar,BindingResult result, ModelMap modelMap) {
		String view="adoptionrequests/listAdoptionRequests";
		if(result.hasErrors()) {
			modelMap.addAttribute("adoptionrequest", ar);
			return "adoptionrequests/addAdoptionrequests";
		}else {
			adoptioReqser.save(ar);
			modelMap.addAttribute("message", "Request successfully saved!");
			//view = reservasList(modelMap);
		}
		return view;
	}
	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {
		
		List<String> usernames= new ArrayList<String>();
		String username = UserUtils.getUser();
		Authorities authority = adoptioReqser.getAuthority(username);
		if(authority.getAuthority().equals("owner")) {
			for(Owner o: adoptioReqser.findOwners()) {
				if(o.getUser().getUsername().equals(username)) {
					usernames.add(o.getUser().getUsername());
				}
			}
		}else if (authority.getAuthority().equals("admin")) { 
			for(Owner o: adoptioReqser.findOwners()) {
					usernames.add(o.getUser().getUsername());
			}
		}
		return usernames;
	}

	@ModelAttribute("pets")
	public Collection<String> populatePets() {
		List<String> petstostr = new ArrayList<String>();
		List<Pet> pets = new ArrayList<Pet>();
		String username = UserUtils.getUser();
		Authorities authority = adoptioReqser.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : adoptioReqser.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					pets = new ArrayList<Pet>();
					for (Pet pet : this.adoptioReqser.findPets()) {
						if (pet.getOwner().getId() == owner.getId()) {
							pets.add(pet);
						}
					}
				}
			}
		} else {
			pets = StreamSupport.stream(adoptioReqser.findPets().spliterator(), false).collect(Collectors.toList());
		}
		for (Pet pet : pets) {
			petstostr.add(pet.getName());
		}return petstostr;

	}
}
