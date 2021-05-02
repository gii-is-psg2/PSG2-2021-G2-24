package org.springframework.samples.petclinic.web;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionRequestResponseService;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
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

@Controller
@RequestMapping("/adoptionrequestresponses")
public class AdoptionRequestResponseController {

	private final AdoptionRequestResponseService adoptionRequestResponseService;
	private final AdoptionRequestService adoptionRequestService;
	private final NotificationService notificationService;
	private final OwnerService ownerService;
	private final PetService petService;
	private List<AdoptionRequestResponse> arrs;

	@Autowired
	public AdoptionRequestResponseController(AdoptionRequestResponseService adoptioReqResser,
			AdoptionRequestService adoptionRequestService, NotificationService notificationService,
			PetService petService, OwnerService ownerService) {
		this.adoptionRequestResponseService = adoptioReqResser;
		this.adoptionRequestService = adoptionRequestService;
		this.notificationService = notificationService;
		this.ownerService = ownerService;
		this.petService = petService;
	}

	private Authorities getAuthorities(String userName) {
		return adoptionRequestResponseService.getAuthority(userName);
	}

	@GetMapping(value = "/{adoptionRequestId}/new")
	public String createAdoptionrequestresponse(@PathVariable("adoptionRequestId") int adoptionRequestId,
			ModelMap modelMap) {
		String add = "adoptionrequestresponses/addAdoptionrequestresponse";
		AdoptionRequestResponse adoptionRequestResponse = new AdoptionRequestResponse();
		Optional<AdoptionRequest> adoptionRequest = adoptionRequestService.findById(adoptionRequestId);
		if (adoptionRequest.isPresent()) {
			adoptionRequestResponse.setAdoptionrequest(adoptionRequest.get());
		}
		modelMap.addAttribute("adoptionrequestresponse", adoptionRequestResponse);
		return add;
	}

	@GetMapping("/ownersresponses")
	public String getOwnersresponsesListed(ModelMap modelMap) {
		String username = UserUtils.getUser();
		String responses = "adoptionrequestresponses";
		Authorities authority = getAuthorities(username);
		List<AdoptionRequestResponse> adrrs = StreamSupport
				.stream(adoptionRequestResponseService.findAll().spliterator(), false).collect(Collectors.toList());
		if (authority.getAuthority().equals("owner")) {
			arrs = new ArrayList<AdoptionRequestResponse>();
			for (AdoptionRequestResponse ars : adrrs) {
				if (ars.getAdoptionrequest().getOwner().getUser().getUsername().equals(username) && ars.isIsactive()) {
					arrs.add(ars);
				}
			}
			modelMap.addAttribute(responses, arrs);
		} else {
			arrs = new ArrayList<AdoptionRequestResponse>();
			for (AdoptionRequestResponse ars : adrrs) {
				if (ars.isIsactive()) {
					arrs.add(ars);
				}
				modelMap.addAttribute(responses, arrs);
			}
		}
		return "adoptionrequestresponses/listOwnersresponses";
	}

	@GetMapping("/list")
	public String getmyresponses(ModelMap modelMap) {
		String username = UserUtils.getUser();
		String responses = "adoptionrequestresponses";
		Authorities authority = getAuthorities(username);
		List<AdoptionRequestResponse> adrrs = StreamSupport
				.stream(adoptionRequestResponseService.findAll().spliterator(), false).collect(Collectors.toList());
		if (authority.getAuthority().equals("owner")) {
			arrs = new ArrayList<AdoptionRequestResponse>();
			for (AdoptionRequestResponse ars : adrrs) {
				if (ars.getOwner().getUser().getUsername().equals(username)) {
					arrs.add(ars);
				}
			}
			modelMap.addAttribute(responses, arrs);
		} else {
			modelMap.addAttribute(responses, adrrs);
		}
		return "adoptionrequestresponses/listAdoptionrequestresponses";
	}

	@PostMapping("/ownersresponses")
	public String getOwnersresponsesListed(@RequestParam("adoptionrequestresponseId") int id,
			@RequestParam("accepted") Boolean accepted) {
		Notification notification = new Notification();
		Optional<AdoptionRequestResponse> adoptionRequestResponseOp = this.adoptionRequestResponseService.findById(id);
		assertTrue(adoptionRequestResponseOp.isPresent());
		AdoptionRequestResponse adoptionRequestResponse = adoptionRequestResponseOp.get();
		notification.setResponse(adoptionRequestResponse);
		if (accepted) {
			AdoptionRequest adoptionRequest = adoptionRequestResponse.getAdoptionrequest();
			Pet pet = adoptionRequest.getPet();
			notification.setMessage(String.format("%s have accepted your adoption petition, take good care of %s ",
					adoptionRequest.getOwner().getFirstName(), pet.getName()));

			pet.setAdoption(false);
			adoptionRequestResponseService.deActiveRequests(adoptionRequest);
			Owner newOwner = adoptionRequestResponse.getOwner();

			pet.setOwner(newOwner);
			petService.savePet(pet);
			adoptionRequestService.save(adoptionRequest);
		} else {
			notification.setMessage(String.format("%s have declined your petition to take care of %s ",
					adoptionRequestResponse.getAdoptionrequest().getOwner().getFirstName(),
					adoptionRequestResponse.getAdoptionrequest().getPet().getName()));
			adoptionRequestResponse.setIsactive(false);
		}
		notificationService.save(notification);
		return "redirect:/adoptionrequestresponses/ownersresponses";
	}

	@PostMapping()
	public String saveAdoptionRequestResponse(@Valid AdoptionRequestResponse arr,
			@RequestParam("owner.user.username") String username, @RequestParam("adoptionrequest") int ar,
			BindingResult result, ModelMap modelMap) {
		String view;
		String message = "message";
		String add = "adoptionrequestresponses/addAdoptionrequestresponse";
		String response = "adoptionrequestresponse";

		if (result.hasErrors()) {
			modelMap.addAttribute(response, arr);
			return add;
		} else {
			Optional<AdoptionRequest> optional = this.adoptionRequestService.findById(ar);
			System.out.println(optional.isPresent());
			assertTrue(optional.isPresent());
			AdoptionRequest a = optional.get();
			arr.setAdoptionrequest(a);
			Owner owner = ownerService.findByUserName(username);
			if (owner.equals(a.getOwner())) {
				modelMap.addAttribute(response, arr);
				modelMap.addAttribute(message, "You cannot adopt your own pet");
				return add;
			}
			arr.setOwner(owner);
			for (AdoptionRequestResponse r : owner.getAdoptionrequestresponses()) {
				if (r.getAdoptionrequest().getId().equals(a.getId())) {
					modelMap.addAttribute(response, arr);
					modelMap.addAttribute(message, "You already sent applied for this adoption");
					return add;
				}
			}
			Set<AdoptionRequestResponse> set = owner.getAdoptionrequestresponses();
			set.add(arr);
			owner.setAdoptionrequestresponses(set);
			arr.setIsactive(true);
			adoptionRequestResponseService.save(arr);
			modelMap.addAttribute(message, "Response successfully saved!");
			view = getmyresponses(modelMap);
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
}
