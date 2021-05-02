/*
  * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final OwnerService ownerService;
	private final UserService userService;
	private final ReservaService reservaSer;

	@Autowired
	public OwnerController(OwnerService ownerService, UserService userService, ReservaService reservaSer) {
		this.ownerService = ownerService;
		this.userService = userService;
		this.reservaSer = reservaSer;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/owners/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			this.ownerService.saveOwner(owner);
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping(value = "/owners/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwners";
	}

	@GetMapping(value = "/owners")
	public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

		if (owner.getLastName() == null) {
			owner.setLastName("");
		}

		Collection<Owner> results = this.ownerService.findOwnerByLastName(owner.getLastName());
		if (results.isEmpty()) {
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		} else if (results.size() == 1) {
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		} else {
			model.put("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Optional<User> userOp = this.userService.findUser(UserUtils.getUser());
		assertTrue(userOp.isPresent());
		User user = userOp.get();
		if (!(owner.getUser().getUsername().equals(UserUtils.getUser())) && !this.userService.isAdmin(user)) {
			return "redirect:/oups";
		}
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
			@PathVariable("ownerId") int ownerId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			Optional<User> userOp = this.userService.findUser(UserUtils.getUser());
			assertTrue(userOp.isPresent());
			User user = userOp.get();
			if (!this.userService.isAdmin(user) && !(owner.getUser().getUsername().equals(UserUtils.getUser()))) {
				return "redirect:/oups";
			} else {
				owner.setId(ownerId);
				this.ownerService.saveOwner(owner);
				return "redirect:/owners/{ownerId}";
			}
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * 
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		String userName = UserUtils.getUser();
		Optional<User> userOp = this.userService.findUser(userName);
		if (!userOp.isPresent()) {

			return new ModelAndView("/login");
		}
		User user = userOp.get();
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = this.ownerService.findOwnerById(ownerId);
		mav.addObject(owner);
		mav.addObject("userName", owner.getUser().getUsername());
		mav.addObject("loggedUsername", UserUtils.getUser());
		mav.addObject("isAdmin", this.userService.isAdmin(user));
		return mav;
	}

	@PostMapping(path = "/owners/{ownerId}", params = { "postDeleteAccount" })
	public String deleteOwner(@PathVariable("ownerId") int ownerId) {

		Owner owner = this.ownerService.findOwnerById(ownerId);
		Optional<User> userOp = this.userService.findUser(UserUtils.getUser());
		assertTrue(userOp.isPresent());
		User user = userOp.get();
		if (!(owner.getUser().getUsername().equals(UserUtils.getUser())) && !this.userService.isAdmin(user)) {
			return "redirect:/oups";
		}
		for (Reserva reserva : this.reservaSer.findAll()) {
			if (reserva.getOwner().getId().equals(ownerId)) {
				reservaSer.delete(reserva);
			}
		}
		if (this.userService.isAdmin(user)) {
			this.ownerService.deleteOwner(owner);
			return "redirect:/owners/find";
		}
		this.ownerService.deleteOwner(owner);
		return "redirect:/login";
	}

}
