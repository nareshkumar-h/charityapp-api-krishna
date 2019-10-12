package com.revature.charityapp.controller.donor;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Donor;
import com.revature.charity.model.Transaction;
import com.revature.charity.service.DonorService;
import com.revature.charity.service.TransactionService;
import com.revature.charityapp.util.Message;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("donor")
public class DonorController {
	/**
	 *Donor login 
	 *@param Email and Password
	 *@Status{
	 *Success => code=>"200", Message => "Login Success!"
	 *Failure => code => 400, Message => "Login Failed!"
	 *}
 	 *return => success{donor details}
	 *return => failed{error message}
	 **/
	
	@Autowired
	private DonorService donorServiceObj;
	
	@PostMapping("login")
	@ApiOperation(value = "Login Api")
	@ApiResponses(value={ 
			@ApiResponse(code = 200, message = "Loggin Success!", response = Donor.class),
			@ApiResponse(code = 400, message = "Login Failed!", response = Donor.class) 
			})
	public @ResponseBody ResponseEntity<?> donorLogin(
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

		if (donorObj != null) {
			return new ResponseEntity<>(donorObj, HttpStatus.OK);
		} else {
			Message message = new Message(errorMsg);
			return new ResponseEntity<>(message,  HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *Donor register 
	 *@param {name,email,password,date of birth, and gender}
	 *@Status{
	 *Success => code=>"200", Message => "Register Success!"
	 *Failure => code => 400, Message => "Register Failed!"
	 *}
	 *return  => success{succes}
	 *return => failed{exception}
	 **/
	
	@PostMapping("register")
	@ApiOperation(value = "Donor Register")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Register success!", response = Donor.class),
		@ApiResponse(code = 400, message = "Register failed!", response = Donor.class)
	})
	public ResponseEntity<?> donorRegister(
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
		String status = null;
		if (Boolean.TRUE.equals(isRegistered)) {
			status = "success";
			Message message = new Message(status);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else {
			Message message = new Message(errorMSG);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	/**
	 *Donor contribution 
	 *@param {transaction id, fund request id, fund amount}
	 *@Status{
	 *Success => code=>"200", Message => "Register Success!"
	 *Failure => code => 400, Message => "Register Failed!"
	 *}
	 *return  => success{succes}
	 *return => failed{failure}
	 **/
	
	@Autowired
	private TransactionService transaction;
	
	@PostMapping("contribute")
	@ApiOperation("Transaction Api")
	@ApiResponses({
		@ApiResponse( code = 200, message = "Transaction success!", response = Message.class),
		@ApiResponse( code = 400, message = "Transaction failed!", response = Message.class)
	})
	public ResponseEntity<?> donorContribute(
				@RequestParam("transactionid")String id,
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
		
		String trasactionStatus = null;
		if(Boolean.TRUE.equals(status))
		{
			trasactionStatus = "success";
			Message message = new Message(trasactionStatus);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else {
			trasactionStatus = "failure";
			Message message = new Message(trasactionStatus);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
	
	
}
