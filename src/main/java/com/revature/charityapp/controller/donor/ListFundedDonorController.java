package com.revature.charityapp.controller.donor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Donor;
import com.revature.charity.service.DonorService;

@RestController
@RequestMapping("donor")
public class ListFundedDonorController {
	
	/**
	 * List funded donor
	 * **/

	@Autowired
	private DonorService donorService;
	
	@GetMapping("fundeddonors")
	public @ResponseBody String listFundedDonors()
	{
		List<Donor> listDonor = null;
		String errorMessage = null;
		try {
		listDonor = donorService.listFundedDonor();
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
		String json = null;
		if(listDonor != null)
		{
			Gson gson = new Gson();
			json = gson.toJson(listDonor);
		} else {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("ERROR_MESSAGE", errorMessage);
		}
	
		return json;
	}
}
