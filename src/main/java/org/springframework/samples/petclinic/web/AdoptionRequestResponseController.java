package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.AdoptionRequestResponse;
import org.springframework.samples.petclinic.service.AdoptionRequestResponseService;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoptionrequestresponses")
public class AdoptionRequestResponseController {

	private final AdoptionRequestResponseService adoptioReqResser;
	
	@Autowired
	public AdoptionRequestResponseController(AdoptionRequestResponseService adoptioReqResser) {
		this.adoptioReqResser = adoptioReqResser;
	}
	
	@GetMapping(path = "/new")
	public String createAdoptionrequestresponse(ModelMap modelMap) {
		String view = "adoptionrequestresponses/addAdoptionrequestresponse";
		modelMap.addAttribute("adoptionrequestresponse", new AdoptionRequestResponse());
		return view;
	}
	@PostMapping()
	public String saveAdoptionrequest(@Valid AdoptionRequestResponse arr,BindingResult result, ModelMap modelMap) {
		String view="adoptionrequestresponses/listAdoptionRequests";
		if(result.hasErrors()) {
			modelMap.addAttribute("adoptionrequest", arr);
			return "adoptionrequests/addAdoptionrequests";
		}else {
			adoptioReqResser.save(arr);
			modelMap.addAttribute("message", "Response successfully saved!");
			//view = reservasList(modelMap);
		}
		return view;
	}
	/*@ModelAttribute
	public Collection<String> populateAdoptionRequest(){
		
	}*/
}
