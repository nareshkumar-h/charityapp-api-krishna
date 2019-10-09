package com.revature.charityapp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Admin;
import com.revature.charity.service.AdminService;

@RestController
@RequestMapping("admin")
public class AdminLoginController {
	
	/**
	 *Admin login 
	 **/
	
	@Autowired
	private AdminService adminServiceObj;
	
	@GetMapping("login")
	public @ResponseBody String adminLogin(
				@RequestParam("email")String email,
				@RequestParam("password")String password
			)
	{
		String errorMsg = null;
		Admin adminObj = null;
		try {
			Admin admin = new Admin();
			admin.setEmail(email);
			admin.setPassword(password);
			adminObj = adminServiceObj.adminLoginService(admin);
		} catch (ServiceException e) {
			errorMsg = e.getMessage();
		}
		// prepare JSON obj
		String json = null;
		Gson gson = new Gson();
		if (adminObj != null) {
			json = gson.toJson(adminObj);
		} else {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("errorMessage", errorMsg);
			json = jsonObj.toString();
		}
		return json;
	}

}
