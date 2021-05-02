package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("causas/donations")
public class DonationController {

	private final DonationService donationSer;
	private final CausaService causaService;
	private final OwnerService ownerService;

	@Autowired
	private DonationValidator donationVal;

	@Autowired
	public DonationController(DonationService donationService, CausaService causaService, OwnerService ownerService) {
		this.donationSer = donationService;
		this.causaService = causaService;
		this.ownerService = ownerService;
	}

	@InitBinder("donation")
	public void initRestaurantReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(donationVal);
	}

	@GetMapping("/list")
	public String donationsList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		Authorities authority = donationSer.getAuthority(username);
		List<Donation> donations = new ArrayList<>();
		if (authority.getAuthority().equals("owner")) {
			Owner owner = ownerService.findByUserName(username);
			for (Causa causa : owner.getCausas()) {
				for (Donation donation : causa.getDonations()) {
					donations.add(donation);
				}
			}

		} else {
			donations = StreamSupport.stream(donationSer.findAll().spliterator(), false).collect(Collectors.toList());
		}
		modelMap.addAttribute("donations", donations);
		return "donations/donationsList";
	}

	@GetMapping(path = "{causaId}/new")
	public String createDonation(ModelMap modelMap, @PathVariable("causaId") int causaId) {
		String view = "donations/createDonation";
		Optional<Causa> causa = causaService.getCausaById(causaId);
		assert causa.isPresent();
		Donation donation = new Donation();
		donation.setCausa(causa.get());
		modelMap.addAttribute("donation", donation);
		return view;
	}

	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {

		List<String> usernames = new ArrayList<>();
		String username = UserUtils.getUser();
		Authorities authority = donationSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner o : donationSer.findOwners()) {
				if (o.getUser().getUsername().equals(username)) {
					usernames.add(o.getUser().getUsername());
				}
			}

		} else if (authority.getAuthority().equals("admin")) {
			for (Owner o : donationSer.findOwners()) {
				usernames.add(o.getUser().getUsername());
			}

		}
		return usernames;
	}

	@ModelAttribute("causas")
	public Collection<String> populateCausas() {
		List<String> causastostr = new ArrayList<>();
		List<Causa> causas = new ArrayList<>();
		String username = UserUtils.getUser();
		Authorities authority = donationSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : donationSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					causas = new ArrayList<>();
					for (Causa causa : this.donationSer.findCausas()) {
						if (causa.getOwner().getId().equals(owner.getId())) {
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

		}
		return causastostr;

	}

	@PostMapping()
	public String saveDonation(@Valid Donation donation, @RequestParam("owner.user.username") String username,
			@RequestParam("CausaId") int causaId, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("donation", donation);
			return "donations/createDonation";
		} else {
			for (Owner owner : donationSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					donation.setOwner(owner);
				}
			}
			Optional<Causa> causaOp = causaService.getCausaById(causaId);
			assert causaOp.isPresent();
			Causa causa = causaOp.get();
			Double donationAct = causa.getTotalDonation() + donation.getImporteDonacion();
			causa.setTotalDonation(donationAct);
			if (causa.getBudgetTarget() <= causa.getTotalDonation()) {
				causa.setClosed(true);
			}
			donation.setFechaDonacion(LocalDate.now());
			donation.setCausa(causa);
			causaService.save(causa);
			donationSer.save(donation);
		}
		return "redirect:/causas/donations/list";
	}

}
