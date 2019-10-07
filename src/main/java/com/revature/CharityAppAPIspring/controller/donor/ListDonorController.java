package com.revature.CharityAppAPIspring.controller.donor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Donor;
import com.revature.charity.service.DonorService;
import com.revature.charity.util.Logger;

@RestController
@RequestMapping("donor")
public class ListDonorController {
	/**
	 *List donor 
	 **/
	
	@Autowired
	private DonorService donorService;
	
	@GetMapping("list")
	public @ResponseBody String listDonor()
	{
		
		List<Donor> donorList = null;
		String errorMessage = null;
		try {
			donorList = donorService.donorList();
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Logger.error(e.getMessage());
		}
		//Prepare for json object
		String json = null;
		Gson gson = new Gson();
		if(errorMessage != null)
		{
			json = errorMessage;
		} else {
			json = gson.toJson(donorList);
		}
		return json;
		
	}
}
