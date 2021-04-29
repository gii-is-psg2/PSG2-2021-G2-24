package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.web.CausaController;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/donations")
public class DonationController {

	private final DonationService donationSer;
	private final UserService userService;


	@Autowired
	private DonationValidator donationVal;

	@Autowired
	public DonationController(DonationService donationSer, UserService userService) {
		this.userService = userService;
		this.donationSer = donationSer;
	}
	
	@InitBinder("donation")
	public void initRestaurantReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(donationVal);
	}
	
	@GetMapping("/list")
	public String donationsList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		// log.info("El username es: " + username);
		Authorities authority = donationSer.getAuthority(username);
		List<Donation> donations = StreamSupport.stream(donationSer.findAll().spliterator(), false).collect(Collectors.toList());
		
		modelMap.addAttribute("donations", donations);
		modelMap.addAttribute("currentDate", LocalDate.now());

		String view = "donations/donationsList";

		return view;
	}

	
	@GetMapping(path = "/new")
	public String createDonation(ModelMap modelMap) {
		String view = "donations/createDonation";
		modelMap.addAttribute("donation", new Donation());
		return view;
	}
	
	
	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {
		
		List<String> usernames= new ArrayList<String>();
		String username = UserUtils.getUser();
		Authorities authority = donationSer.getAuthority(username);
		if(authority.getAuthority().equals("owner")) {
			for(Owner o: donationSer.findOwners()) {
				if(o.getUser().getUsername().equals(username)) {
					usernames.add(o.getUser().getUsername());
				}
			}
			
		}else if (authority.getAuthority().equals("admin")) { 
			for(Owner o: donationSer.findOwners()) {
					usernames.add(o.getUser().getUsername());
			}
			

		}
		return usernames;
	}
	
	@ModelAttribute("causas")
	public Collection<String> populateCausas() {
		List<String> causastostr = new ArrayList<String>();
		List<Causa> causas = new ArrayList<Causa>();
		String username = UserUtils.getUser();
		Authorities authority = donationSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : donationSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					causas = new ArrayList<Causa>();
					for (Causa causa : this.donationSer.findCausas()) {
						if (causa.getOwner().getId() == owner.getId()) {
							causas.add(causa);
						}
					}
				}
			}

		} else {
			causas = StreamSupport.stream(donationSer.findCausas().spliterator(), false).collect(Collectors.toList());
		}
		for (Causa causa : causas) {
			causastostr.add(causa.getName());

		}return causastostr;

	}
	
	@PostMapping()
	public String saveDonation(@Valid Donation donation,@RequestParam("owner.user.username") String username, 
			@RequestParam("causa.name") String causa
			,BindingResult result, ModelMap modelMap) {
		String view="donations/donationsList";
		if(result.hasErrors()) {
	//		log.info("Tiene errores");
			modelMap.addAttribute("donation", donation);
			return "donations/createDonation";
		}else {

			for(Owner owner: this.donationSer.findOwners()) {
				if(owner.getUser().getUsername().equals(username)) {
					donation.setOwner(owner);
				}
				
				for(Causa causaname: this.donationSer.findCausas()) {
					if(causaname.getName().equals(causa)) {
						donation.setCausa(causaname);
					}
			}
			donationSer.save(donation);
			modelMap.addAttribute("message", "Donation successfully saved!");
			view = donationsList(modelMap);
		}
		return view;
	}

}
}