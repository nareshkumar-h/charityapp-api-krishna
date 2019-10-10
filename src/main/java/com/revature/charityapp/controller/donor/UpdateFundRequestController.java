package com.revature.charityapp.controller.donor;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.revature.charity.model.FundRequest;
import com.revature.charity.service.FundRequestService;

@RestController
@RequestMapping("fund")
public class UpdateFundRequestController {
	
	/**
	 * Update fund amount
	 * **/
	
	@Autowired
	private FundRequestService fundRequestService;
	
	@RequestMapping("update")
	public @ResponseBody String updateFundRequest(
				@RequestParam("id")String id,
				@RequestParam("requestType")String requestType,
				@RequestParam("description")String description,
				@RequestParam("amount")String amount,
				@RequestParam("expireDate")String expireDate
			)
	{
		Boolean isFundUpdated = null;
		
		FundRequest fundRequest = new FundRequest();
		
		Integer fundId = Integer.parseInt(id);
		Double fundAmount = Double.parseDouble(amount);
		LocalDate expiryDate = LocalDate.parse(expireDate);
		
		fundRequest.setId(fundId);
		fundRequest.setAmount(fundAmount);
		fundRequest.setExpireDate(expiryDate);
		fundRequest.setDescription(description);
		fundRequest.setRequestType(requestType);
		
		isFundUpdated = fundRequestService.updateFundRequest(fundRequest);
		 
		 String json = null;
		 JsonObject jsonObj = new JsonObject();
		 if(Boolean.TRUE.equals(isFundUpdated))
		 {
			 jsonObj.addProperty("isFundUpdated", isFundUpdated);
			 json = jsonObj.toString();
		 } else {
			 jsonObj.addProperty("isFundUpdated", isFundUpdated);
			 json = jsonObj.toString();
		 }
		return json;
		
	}
	
}
