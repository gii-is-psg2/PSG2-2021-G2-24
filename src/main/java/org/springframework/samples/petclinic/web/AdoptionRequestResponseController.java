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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoptionRequestResponse")
public class AdoptionRequestResponseController {

	private final AdoptionRequestResponseService adoptionRequestResponseService;
	private final AdoptionRequestService adoptionRequestService;
	
	@Autowired
	public AdoptionRequestResponseController(AdoptionRequestResponseService adoptioReqResser, AdoptionRequestService adoptionRequestService) {
		this.adoptionRequestResponseService = adoptioReqResser;
		this.adoptionRequestService = adoptionRequestService;
	}
	
	@GetMapping(path = "/{adoptionRequestId}/new")
	public String createAdoptionrequestresponse(ModelMap modelMap,@PathVariable("adoptionRequestId") int adoptionRequestId) {
		String view = "adoptionrequestresponses/addAdoptionrequestresponse";
		AdoptionRequestResponse adoptionRequestResponse =  new AdoptionRequestResponse();
		adoptionRequestResponse.setAdoptionrequest(adoptionRequestService.findById(adoptionRequestId).get());
		modelMap.addAttribute("adoptionRequestResponse",adoptionRequestResponse);
		return view;
	}
	
	@PostMapping()
	public String saveAdoptionrequest(@Valid AdoptionRequestResponse arr,BindingResult result, ModelMap modelMap) {
		String view="adoptionrequestresponses/listAdoptionRequests";
		if(result.hasErrors()) {
			modelMap.addAttribute("adoptionrequest", arr);
			return "adoptionrequests/addAdoptionrequests";
		}else {
			adoptionRequestResponseService.save(arr);
			modelMap.addAttribute("message", "Response successfully saved!");
			//view = reservasList(modelMap);
		}
		return view;
	}
	/*@ModelAttribute
	public Collection<String> populateAdoptionRequest(){
		
	}*/
}
