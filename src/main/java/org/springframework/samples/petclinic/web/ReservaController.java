package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/reservas")
public class ReservaController {

	
	@Autowired
	private ReservaService reservaSer;
	
	private List<Reserva> reservations;
	
	@GetMapping()
	public String reservasList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		Authorities authority = reservaSer.getAuthority(username);
		List<Reserva> reservas= StreamSupport.stream(reservaSer.findAll().spliterator(), false).collect(Collectors.toList());
		if(authority.getAuthority().equals("owner")) {
			for(Owner owner: reservaSer.findOwners()) {
				if(owner.getUser().getUsername().equals(username)) {
					reservations = new ArrayList<Reserva>();
					for(Reserva reserva:reservas) {
						if(reserva.getOwner().getId() == owner.getId()) {
							reservations.add(reserva);
						}
					}
				}
			}
		}else {
			reservations.addAll(reservas);
		}
		
		String view= "reservas/listReservas";
		modelMap.addAttribute("reservas", reservations);
		return view;
	}
	@GetMapping(path="/new")
	public String createRestaurantReservation(ModelMap modelMap) {
		String view="reservas/addReserva";
		modelMap.addAttribute("reserva", new Reserva() );
		return view;
	}
	@ModelAttribute("pets")
	public Collection<Pet> populatePets() {
		return this.reservaSer.findPets();
	}
	@ModelAttribute("rooms")
	public Collection<Room> populateRooms() {
		return this.reservaSer.findRooms();
	}
	
	
	
}