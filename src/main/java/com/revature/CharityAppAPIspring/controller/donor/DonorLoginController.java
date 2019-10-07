package com.revature.CharityAppAPIspring.controller.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Donor;
import com.revature.charity.service.DonorService;

@RestController
@RequestMapping("donor")
public class DonorLoginController {
	/**
	 *Donor login 
	 **/
	
	@Autowired
	private DonorService donorServiceObj;
	
	@GetMapping("login")
	public @ResponseBody String donorLogin(
				@RequestParam("email")String email,
				@RequestParam("password")String password
			)
	{
		Donor donorObj = null;
		String errorMsg = null;

		try {
			Donor donor = new Donor();
			donor.setEmail(email);
			donor.setPassword(password);
			donorObj = donorServiceObj.donorSignin(donor);
		} catch (ServiceException e) {
			errorMsg = e.getMessage();
		}

		String json = null;
		Gson gson = new Gson();

		if (donorObj != null) {
			json = gson.toJson(donorObj);
		} else {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("errorMessage", errorMsg);
			json = jsonObj.toString();
		}
		return json;
	}
}
