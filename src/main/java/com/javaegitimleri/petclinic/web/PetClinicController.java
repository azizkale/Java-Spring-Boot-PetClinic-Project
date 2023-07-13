package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PetClinicController {

	//tomcat kullanilacagi zaman application properties a prefix ve suffix endpoint leri eklenir
	//spring.mvc.view.prefix=/WEB-INF/jsp/
	//spring.mvc.view.suffix=.jsp

	//alttaki annotation su ise yarar: Bootstrapt sirasinda PetClinicService tipindeki bean i Controller bean ine inject eder.

	@Autowired
	private PetClinicService petClinicService;

	@RequestMapping("/pcs")
	@ResponseBody
	public String welcome() {
		return "Welcome to PetClinic World!";
	}

	@RequestMapping("/owners")
	public ModelAndView getOwners() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("owners", petClinicService.findOwners());
		mav.setViewName("owners");
		return mav;
	}
}
