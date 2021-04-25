package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.service.AdoptionRequestResponseService;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
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
	private List<AdoptionRequestResponse> arrs;
	@Autowired
	public AdoptionRequestResponseController(AdoptionRequestResponseService adoptioReqResser, AdoptionRequestService adoptionRequestService) {
		this.adoptionRequestResponseService = adoptioReqResser;
		this.adoptionRequestService = adoptionRequestService;
	}
	
	@GetMapping(value = "/{adoptionRequestId}/new")
	public String createAdoptionrequestresponse(@PathVariable("adoptionRequestId") int adoptionRequestId,ModelMap modelMap) {
		String view = "adoptionrequestresponses/addAdoptionrequestresponse";
		AdoptionRequestResponse adoptionRequestResponse =  new AdoptionRequestResponse();
		adoptionRequestResponse.setAdoptionrequest(adoptionRequestService.findById(adoptionRequestId).get());
		modelMap.addAttribute("adoptionrequestresponse",adoptionRequestResponse);
		return view;
	}
	
	@GetMapping("/ownersresponses")
	public String getOwnersresponsesListed(ModelMap modelMap) {
		String username = UserUtils.getUser();
		//	log.info("El username es: " + username);
			Authorities authority = adoptionRequestResponseService.getAuthority(username);
			List<AdoptionRequestResponse> adrrs = StreamSupport.stream(adoptionRequestResponseService.findAll().spliterator(), false)
					.collect(Collectors.toList());
			if (authority.getAuthority().equals("owner")) {
				for (AdoptionRequestResponse ars : adrrs) {
					if (ars.getAdoptionrequest().getOwner().getUser().getUsername().equals(username)) {
						 arrs = new ArrayList<AdoptionRequestResponse>();
						arrs.add(ars);
					}
				}
				modelMap.addAttribute("adoptionrequestresponses", arrs);
			} else {
				modelMap.addAttribute("adoptionrequestresponses", adrrs);
			}

			String view = "adoptionrequestresponses/listOwnersresponses";

			return view;
	}
	
	@GetMapping("/list")
	public String getmyresponses(ModelMap modelMap) {
		String username = UserUtils.getUser();
		//	log.info("El username es: " + username);
			Authorities authority = adoptionRequestResponseService.getAuthority(username);
			List<AdoptionRequestResponse> adrrs = StreamSupport.stream(adoptionRequestResponseService.findAll().spliterator(), false)
					.collect(Collectors.toList());
			if (authority.getAuthority().equals("owner")) {
				for (AdoptionRequestResponse ars : adrrs) {
					if (ars.getOwner().getUser().getUsername().equals(username)) {
						 arrs = new ArrayList<AdoptionRequestResponse>();
						arrs.add(ars);
					}
				}
				modelMap.addAttribute("adoptionrequestresponses", arrs);
			} else {
				modelMap.addAttribute("adoptionrequestresponses", adrrs);
			}

			String view = "adoptionrequestresponses/listAdoptionrequestresponses";

			return view;
	}
	
	@PostMapping()
	public String saveAdoptionrequest(@Valid AdoptionRequestResponse arr,@RequestParam("owner.user.username") String username,@RequestParam("adoptionrequest") int ar,BindingResult result, ModelMap modelMap) {
		String view="adoptionrequestresponses/listAdoptionrequestresponses";
		if(result.hasErrors()) {
			modelMap.addAttribute("adoptionrequest", arr);
			return "adoptionrequests/addAdoptionrequests";
		}else {
			for(Owner owner: this.adoptionRequestResponseService.findOwners()) {
				if(owner.getUser().getUsername().equals(username)) {
					arr.setOwner(owner);
				}
			}
			AdoptionRequest a = this.adoptionRequestService.findById(ar).get();
			arr.setAdoptionrequest(a);
			adoptionRequestResponseService.save(arr);
			modelMap.addAttribute("message", "Response successfully saved!");
			view = getmyresponses(modelMap);
		}
		return view;
	}
	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {
		
		List<String> usernames= new ArrayList<String>();
		String username = UserUtils.getUser();
		Authorities authority = adoptionRequestService.getAuthority(username);
		if(authority.getAuthority().equals("owner")) {
			for(Owner o: adoptionRequestService.findOwners()) {
				if(o.getUser().getUsername().equals(username)) {
					usernames.add(o.getUser().getUsername());
				}
			}
		}else if (authority.getAuthority().equals("admin")) { 
			for(Owner o: adoptionRequestService.findOwners()) {
					usernames.add(o.getUser().getUsername());
			}
		}
		return usernames;
	}

}
