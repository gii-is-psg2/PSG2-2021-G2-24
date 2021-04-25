package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adoptionrequests")
public class AdoptionRequestController {

	private final AdoptionRequestService adoptionRequestService;
//	private final UserService userService;
//	private final OwnerService ownerService;

	@Autowired
	public AdoptionRequestController(AdoptionRequestService adoptionRequestService	
//			, UserService userService, OwnerService ownerService
			){
		this.adoptionRequestService = adoptionRequestService;
//		this.userService = userService;
//		this.ownerService = ownerService;

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
		String view = "redirect:/adoptionrequests/list";
		if (result.hasErrors()) {
			modelMap.addAttribute("adoptionrequest", adoptionRequest);
			return "redirect:/adoptionrequests/new";
		} else {
			Boolean stop = false;
			for (Pet pet : this.adoptionRequestService.findPets()) {
				if (pet.getName().equals(petName) && !stop) {
					if (pet.isAdoption()) {
						modelMap.addAttribute("adoptionrequest", adoptionRequest);
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
			// view = reservasList(modelMap);
		}
		return view;
	}

	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {

		List<String> usernames = new ArrayList<String>();
		String username = UserUtils.getUser();
		Authorities authority = adoptionRequestService.getAuthority(username);
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
		List<String> petstostr = new ArrayList<String>();
		List<Pet> pets = new ArrayList<Pet>();
		String username = UserUtils.getUser();
		Authorities authority = adoptionRequestService.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : adoptionRequestService.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					pets = new ArrayList<Pet>();
					for (Pet pet : this.adoptionRequestService.findPets()) {
						if (pet.getOwner().getId() == owner.getId()) {
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

//	@GetMapping("/{id}/details")
//	public ModelAndView adoptionRequestDetails(@PathVariable("ownerId") int ownerId) {
//		System.out.println("holis2");
//		String userName = UserUtils.getUser();
//		Optional<User> userOp = this.userService.findUser(userName);
//		if (!userOp.isPresent()) {
//
//			return new ModelAndView("/login");
//		}
//		User user = userOp.get();
//		ModelAndView mav = new ModelAndView("owners/ownerDetails");
//		Owner owner = this.ownerService.findOwnerById(ownerId);
//		mav.addObject(owner);
//		mav.addObject("userName", owner.getUser().getUsername());
//		mav.addObject("loggedUsername", UserUtils.getUser());
//		mav.addObject("isAdmin", this.userService.isAdmin(user));
//		return mav;
//	}
}
