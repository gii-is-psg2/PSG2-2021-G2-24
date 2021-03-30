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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.ReservaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reservas")

public class ReservaController {

	private final ReservaService reservaSer;
	private final UserService userService;

	private List<Reserva> reservations;

	@Autowired
	public ReservaController(ReservaService reservaSer, UserService userService) {
		this.userService = userService;
		this.reservaSer = reservaSer;
	}

	@GetMapping()
	public String reservasList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		log.info("El username es: " + username);
		Authorities authority = reservaSer.getAuthority(username);
		List<Reserva> reservas = StreamSupport.stream(reservaSer.findAll().spliterator(), false)
				.collect(Collectors.toList());
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : reservaSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					reservations = new ArrayList<Reserva>();
					for (Reserva reserva : reservas) {
						if (reserva.getOwner().getId() == owner.getId()) {
							reservations.add(reserva);
						}
					}
				}
			}
			modelMap.addAttribute("reservas", reservations);
		} else {
			modelMap.addAttribute("reservas", reservas);
		}

		String view = "reservas/listReservas";

		return view;
	}

	@GetMapping(path = "/new")
	public String createReserva(ModelMap modelMap) {
		String view = "reservas/addReserva";
		modelMap.addAttribute("reserva", new Reserva());
		return view;
	}

	@PostMapping(path = "/save")
	public String saveReserva(@Valid Reserva reserva, @RequestParam String owner, @RequestParam String room,
			BindingResult result, ModelMap modelMap) {
		String view = "reservas/listReservas";

		if (result.hasErrors()) {
			modelMap.addAttribute("reserva", reserva);
			return "reservas/addReserva";
		} else {
			for (Room roomid : this.reservaSer.findRooms()) {
				if (roomid.getId().toString().equals(room)) {
					reserva.setRoom(roomid);
				}
			}
			reservaSer.save(reserva);
			modelMap.addAttribute("message", "Reserva successfully saved!");
			view = reservasList(modelMap);
		}
		return view;
	}

	@ModelAttribute("pets")
	public Collection<Pet> populatePets() {
		List<String> petstostr = new ArrayList<String>();
		List<Pet> pets = new ArrayList<Pet>();
		String username = UserUtils.getUser();
		Authorities authority = reservaSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : reservaSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					pets = new ArrayList<Pet>();
					for (Pet pet : this.reservaSer.findPets()) {
						if (pet.getOwner().getId() == owner.getId()) {
							pets.add(pet);
						}
					}
				}
			}

		} else {
			pets = StreamSupport.stream(reservaSer.findPets().spliterator(), false).collect(Collectors.toList());
		}
		for (Pet pet : pets) {
			petstostr.add(pet.getName());
		}
		return pets;
	}

	@ModelAttribute("rooms")
	public Collection<Integer> populateRooms() {
		List<Integer> rooms = new ArrayList<Integer>();
		for (Room room : this.reservaSer.findRooms()) {
			rooms.add(room.getId());
		}
		return rooms;
	}

	@ModelAttribute("owners")
	public Collection<Owner> populateOwners() {

		List<Owner> owners = new ArrayList<Owner>();
		String username = UserUtils.getUser();
		Authorities authority = reservaSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner o : reservaSer.findOwners()) {
				if (o.getUser().getUsername().equals(username)) {
					owners.add(o);
				}
			}

		} else if (authority.getAuthority().equals("admin")) {
			owners = StreamSupport.stream(reservaSer.findOwners().spliterator(), false).collect(Collectors.toList());
		}
		return owners;
	}

	@PostMapping(params = { "postDeleteBooking" })
	public String deleteBooking(@RequestParam("reservaId") int reservaId) {
		Optional<Reserva> reservaOp = this.reservaSer.getReservaById(reservaId);
		if (!reservaOp.isPresent()) {
			return "redirect:/oups";
		} else {
			Reserva reserva = reservaOp.get();
			Owner owner = reserva.getOwner();
			if (!(owner.getUser().getUsername().equals(UserUtils.getUser()))
					&& !this.userService.isAdmin(this.userService.findUser(UserUtils.getUser()).get())) {
				return "redirect:/oups";
			} else {
				this.reservaSer.delete(reserva);
			}
		}
		return "redirect:/reservas";
	}

}