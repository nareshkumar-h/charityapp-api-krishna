package com.revature.charityapp.controller.donor;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Donor;
import com.revature.charity.service.DonorService;

@RestController
@RequestMapping("donor")
public class DonorController {
	
	/**
	 *Donor register 
	 **/
	
	@Autowired
	private DonorService donorServiceObj;
	
	@GetMapping("register")
	public @ResponseBody String donorRegister(
				@RequestParam("name")String name,
				@RequestParam("email")String email,
				@RequestParam("password")String password,
				@RequestParam("dob")String dob,
				@RequestParam("gender")String gender
			)
	{
		
		//Convert string to local date
		LocalDate dateOfBirth = LocalDate.parse(dob);
		
		String errorMSG = "";
		String json = "";
		Boolean isRegistered = null;
		try {
			Donor donorObj = new Donor();		
			donorObj.setName(name);
			donorObj.setEmail(email);
			donorObj.setPassword(password);
			donorObj.setDateOfBirth(dateOfBirth);
			donorObj.setGender(gender);
			//Donor register service
			isRegistered = donorServiceObj.donorRegister(donorObj);
		} catch (ServiceException e) {
			errorMSG = e.getMessage();
		}
		
		if (Boolean.TRUE.equals(isRegistered)) {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("isStatus", isRegistered);
			json = jsonObj.toString();
		} else {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("errorMessage", errorMSG);
			json = jsonObj.toString();
		}
		return json;
	}
}
