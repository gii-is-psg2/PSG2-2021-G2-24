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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Slf4j
@Controller

public class VetController {

	private final VetService vetService;
	private final UserService userService;
	
	@Autowired
	public VetController(VetService clinicService, UserService userService) {
		this.vetService = clinicService;
		this.userService = userService;
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of
		// Vet
		// objects
		// so it is simpler for Object-Xml mapping
		String userName = UserUtils.getUser();
		Optional<User> userOp = this.userService.findUser(userName);
		if(!userOp.isPresent()) {
			return "redirect:/login";
		}
		User user = userOp.get();
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		model.put("isAdmin", this.userService.isAdmin(user));
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml" })
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of
		// Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}

	@PostMapping(path = "/vets", params = {"postDeleteVet"})
	public String deleteVet(@RequestParam("vetId") int vetId) {
		String userName = UserUtils.getUser();
		Optional<User> userOp = this.userService.findUser(userName);
		if(!userOp.isPresent()) {
			return "redirect:/login";
		}
		User user = userOp.get();
		if(!this.userService.isAdmin(user)) {
			return "redirect:/oups";
		}
		Optional<Vet> vetOp = this.vetService.findVetbyId(vetId);
		if(!vetOp.isPresent()) {
			return "redirect:/vets";
		}
		Vet vet = vetOp.get();
		this.vetService.vetDelete(vet);
		return "redirect:/vets"; 
	}
	@GetMapping(path = "/vets/new")
	public String createVet(ModelMap modelMap) {
		String userName = UserUtils.getUser();
		Optional<User> userOp = this.userService.findUser(userName);
		if(!userOp.isPresent()) {
			return "redirect:/login";
		}
		User user = userOp.get();
		if(!this.userService.isAdmin(user)) {
			return "redirect:/oups";
		}
		String view = "vets/addVet";
		modelMap.addAttribute("vet", new Vet());
		return view;
	}

	@PostMapping(path = "/vets/save")
	public String saveVet(@Valid Vet vet, @RequestParam Optional<String[]> specialties, BindingResult result,
			ModelMap modelMap) {
		String userName = UserUtils.getUser();
		Optional<User> userOp = this.userService.findUser(userName);
		if(!userOp.isPresent()) {
			return "redirect:/login";
		}
		User user = userOp.get();
		if(!this.userService.isAdmin(user)) {
			return "redirect:/oups";
		}
	//	log.info("El nombre es:" + vet.getFirstName());
	//	log.info("El apellido es:" + vet.getLastName());
		String view = "vets/vetList";
		if (result.hasErrors()) {
			modelMap.addAttribute("vet", vet);
			return "vet/addVet";

		} else {
			if (!specialties.isPresent()) {

			} else {
				String[] specialtiesstr = specialties.get();

				for (String s : specialtiesstr) {
					Collection<Specialty> findSpecialties = this.vetService.findSpecialties();
					for (Specialty type : findSpecialties) {
						if (type.getName().equals(s)) {
							vet.addSpecialty(type);
						}
					}
				}
			}
			vetService.save(vet);
			modelMap.addAttribute("message", "vet successfully saved!");
			view = showVetList(modelMap);

		}

		return view;
	}

	@GetMapping(value = "vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") int vetId, ModelMap model) {
		String userName = UserUtils.getUser();
		Optional<User> userOp = this.userService.findUser(userName);
		if(!userOp.isPresent()) {
			return "redirect:/login";
		}
		User user = userOp.get();
		if(!this.userService.isAdmin(user)) {
			return "redirect:/oups";
		}
	//	log.info("Loading update vet form");
		Vet vet = vetService.findVetbyId(vetId).get();
		model.put("vet", vet);
		return "vets/updateVet";
	}

	@PostMapping(value = "vets/{vetId}/edit")
	public String processUpdateVetForm(@Valid Vet vet, BindingResult result, @PathVariable("vetId") int vetId,
			ModelMap model, @RequestParam Optional<String[]> specialties) {
		String userName = UserUtils.getUser();
		Optional<User> userOp = this.userService.findUser(userName);
		if(!userOp.isPresent()) {
			return "redirect:/login";
		}
		User user = userOp.get();
		if(!this.userService.isAdmin(user)) {
			return "redirect:/oups";
		}
	//	log.info("Updating vet: " + vetId);
		vet.setId(vetId);
		if (result.hasErrors()) {
	//		log.warn("Found errors on update: " + result.getAllErrors());
			model.put("vet", vet);
			return "vets/updateVet";
		} else {
			if (!specialties.isPresent()) {

			} else {
				String[] specialtiesstr = specialties.get();

				for (String s : specialtiesstr) {
					Collection<Specialty> findSpecialties = this.vetService.findSpecialties();
					for (Specialty type : findSpecialties) {
						if (type.getName().equals(s)) {
							vet.addSpecialty(type);
						}
					}
				}
			}
			this.vetService.save(vet);
			return "redirect:/vets";
		}
	}

	@ModelAttribute("specialties")
	public Collection<Specialty> populateSpecialties() {
		return this.vetService.findSpecialties();
	}
}
