
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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ReservaService;
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

@Controller
@RequestMapping("/reservas")

public class ReservaController {

	private final ReservaService reservaSer;
	private final UserService userService;

	private List<Reserva> reservations;

	@Autowired
	private ReservaValidator reservaVal;

	@InitBinder("reserva")
	public void initRestaurantReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(reservaVal);
	}

	@Autowired
	public ReservaController(ReservaService reservaSer, UserService userService) {
		this.userService = userService;
		this.reservaSer = reservaSer;
	}

	@GetMapping()
	public String reservasList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		Authorities authority = reservaSer.getAuthority(username);
		List<Reserva> reservas = StreamSupport.stream(reservaSer.findAll().spliterator(), false)
				.collect(Collectors.toList());
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : reservaSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					reservations = new ArrayList<>();
					for (Reserva reserva : reservas) {
						if (reserva.getOwner().getId().equals(owner.getId())) {
							reservations.add(reserva);
						}
					}
				}
			}
			modelMap.addAttribute("reservas", reservations);
		} else {
			modelMap.addAttribute("reservas", reservas);
			modelMap.addAttribute("currentDate", LocalDate.now());
		}
		return "reservas/listReservas";
	}

	@GetMapping(path = "/new")
	public String createReserva(ModelMap modelMap) {
		String view = "reservas/addReserva";
		modelMap.addAttribute("reserva", new Reserva());
		return view;
	}

	@PostMapping()
	public String saveReserva(@Valid Reserva reserva, @RequestParam("owner.user.username") String username,
			@RequestParam("pet.name") String pet, @RequestParam String room, BindingResult result, ModelMap modelMap) {
		String view;
		if (result.hasErrors()) {
			modelMap.addAttribute("reserva", reserva);
			return "reservas/addReserva";
		} else {
			for (Pet petname : this.reservaSer.findPets()) {
				if (petname.getName().equals(pet)) {
					reserva.setPet(petname);
				}
			}
			for (Owner owner : this.reservaSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					reserva.setOwner(owner);
				}
			}
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
	public Collection<String> populatePets() {
		List<String> petstostr = new ArrayList<>();
		List<Pet> pets = new ArrayList<>();
		String username = UserUtils.getUser();
		Authorities authority = reservaSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner owner : reservaSer.findOwners()) {
				if (owner.getUser().getUsername().equals(username)) {
					pets = new ArrayList<>();
					for (Pet pet : this.reservaSer.findPets()) {
						if (pet.getOwner().getId().equals(owner.getId())) {
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
		return petstostr;

	}

	@ModelAttribute("rooms")
	public Collection<Integer> populateRooms() {
		List<Integer> rooms = new ArrayList<>();
		for (Room room : this.reservaSer.findRooms()) {
			rooms.add(room.getId());
		}
		return rooms;
	}

	@ModelAttribute("usernames")
	public Collection<String> populateUsernames() {

		List<String> usernames = new ArrayList<>();
		String username = UserUtils.getUser();
		Authorities authority = reservaSer.getAuthority(username);
		if (authority.getAuthority().equals("owner")) {
			for (Owner o : reservaSer.findOwners()) {
				if (o.getUser().getUsername().equals(username)) {
					usernames.add(o.getUser().getUsername());
				}
			}

		} else if (authority.getAuthority().equals("admin")) {
			for (Owner o : reservaSer.findOwners()) {
				usernames.add(o.getUser().getUsername());
			}

		}
		return usernames;
	}

	@PostMapping(params = { "postDeleteBooking" })
	public String deleteBooking(@RequestParam("reservaId") int reservaId) {
		Optional<Reserva> reservaOp = this.reservaSer.getReservaById(reservaId);
		if (!reservaOp.isPresent()) {
			return "redirect:/login";
		} else {
			Reserva reserva = reservaOp.get();
			Owner owner = reserva.getOwner();
			Optional<User> userOp = this.userService.findUser(UserUtils.getUser());
			if (!userOp.isPresent()) {
				return "redirect:/oups";
			}
			User user = userOp.get();
			if (!(owner.getUser().getUsername().equals(UserUtils.getUser())) && !this.userService.isAdmin(user)) {
				return "redirect:/oups";
			} else {
				this.reservaSer.delete(reserva);
			}
		}
		return "redirect:/reservas";
	}

}