package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsController {

	@GetMapping("/terms_and_conditions")
	public String terms(Map<String, Object> model) {

		return "terms_and_conditions";
	}
}
