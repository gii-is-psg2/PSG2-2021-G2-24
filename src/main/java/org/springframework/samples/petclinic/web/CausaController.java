package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reservas")

public class CausaController {

	private final CausaService causaSer;
	private final UserService userService;

	private List<Causa> listaCausas;

	//@Autowired
	//private CausaValidator causaVal;

	@Autowired
	public CausaController(CausaService causaSer, UserService userService) {
		this.userService = userService;
		this.causaSer = causaSer;
	}

	@GetMapping()
	public String reservasList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		// log.info("El username es: " + username);
		Authorities authority = causaSer.getAuthority(username);
		List<Causa> causas = StreamSupport.stream(causaSer.findAll().spliterator(), false).collect(Collectors.toList());
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : causaSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					listaCausas = new ArrayList<Causa>();
					for (Causa causa : causas) {
						if (causa.getOwner().getId() == owner.getId()) {
							listaCausas.add(causa);
						}
					}
				}
			}
			modelMap.addAttribute("causas", listaCausas);
		} else {
			modelMap.addAttribute("reservas", causas);
			modelMap.addAttribute("currentDate", LocalDate.now());
		}

		String view = "reservas/listReservas";

		return view;
	}
}