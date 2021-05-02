package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.CausaService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/causas")
public class CausaController {

	private final CausaService causaSer;
	private final UserService userService;

	@Autowired
	public CausaController(CausaService causaSer, UserService userService) {
		this.userService = userService;
		this.causaSer = causaSer;
	}

	@GetMapping("/list")
	public String causasList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		Optional<User> userOp = userService.findUser(username);
		assert userOp.isPresent();
		User user = userOp.get();
		Boolean isAdmin = userService.isAdmin(user);
		if (isAdmin) {
			modelMap.addAttribute("isAdmin", true);
		} else {
			modelMap.addAttribute("isAdmin", false);
			modelMap.addAttribute("username", username);
		}
		List<Causa> causas = StreamSupport.stream(causaSer.findAll().spliterator(), false).collect(Collectors.toList());

		modelMap.addAttribute("causas", causas);
		return "causes/causesList";
	}

	@GetMapping(path = "/new")
	public String createCausa(ModelMap modelMap) {
		String view = "causes/addCause";
		modelMap.addAttribute("causa", new Causa());
		return view;
	}

	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {

		List<String> usernames = new ArrayList<>();
		String username = UserUtils.getUser();
		Authorities authority = causaSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner o : causaSer.findOwners()) {
				if (o.getUser().getUsername().equals(username)) {
					usernames.add(o.getUser().getUsername());
				}
			}

		} else if (authority.getAuthority().equals("admin")) {
			for (Owner o : causaSer.findOwners()) {
				usernames.add(o.getUser().getUsername());
			}

		}
		return usernames;
	}

	@PostMapping()
	public String saveCausa(@Valid Causa causa, @RequestParam("owner.user.username") String username,
			BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("causas", causa);
			return "redirect:/causas/new";
		} else {
			for (Owner owner : this.causaSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					causa.setOwner(owner);
				}
			}
			causa.setTotalDonation(0.0);
			causa.setClosed(false);
			causaSer.save(causa);
		}
		return "redirect:/causas/list";
	}

	@GetMapping("details/{causaId}")
	public ModelAndView showOwner(@PathVariable("causaId") int causaId) {

		ModelAndView mav = new ModelAndView("causes/causeDetails");
		Optional<Causa> causa = this.causaSer.getCausaById(causaId);
		assert causa.isPresent();
		mav.addObject(causa);
		mav.addObject("causa", causa.get());
		return mav;
	}

}