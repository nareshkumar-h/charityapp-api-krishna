package com.revature.CharityAppAPIspring.controller.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.revature.charity.model.Transaction;
import com.revature.charity.service.TransactionService;

@RestController
@RequestMapping("donor")
public class TransactionController {
	/**
	 * Donor contribution
	 * **/
	
	@Autowired
	private TransactionService transaction;
	
	@GetMapping("contribute")
	public @ResponseBody String donorContribute(
				@RequestParam("id")String id,
				@RequestParam("fundrequestid")String fundrequestid,
				@RequestParam("amount")String amount
			)
	{
		Transaction transactionObj = new Transaction();
		
		Integer transactionId = Integer.parseInt(id);
		Integer fundrequestId = Integer.parseInt(fundrequestid);
		Double fundAmount = Double.parseDouble(amount);
		
		
		transactionObj.setId(transactionId);
		transactionObj.setfundRequestId(fundrequestId);
		transactionObj.setAmount(fundAmount);
		
		Boolean status = transaction.transaction(transactionObj);
		String json = null;
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("status", status);
		json = jsonObj.toString();
	
	return json;
		
	}
}
