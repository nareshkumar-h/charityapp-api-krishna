package com.revature.charityapp.controller.donor;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Donor;
import com.revature.charityapp.model.FundRequest;
import com.revature.charityapp.model.Transaction;
import com.revature.charityapp.service.DonorService;
import com.revature.charityapp.service.FundService;
import com.revature.charityapp.service.TransactionService;
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
	
	@Autowired
	private TransactionService transactionServiceObj;
	
	@Autowired
	private FundService fundServiceObj;
	
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
			donorObj = donorServiceObj.donorLogin(donor);
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
	
	
	/** Start **/
	
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
		Donor donorStatusObj = null;
		try {
			Donor donorObj = new Donor();		
			donorObj.setName(name);
			donorObj.setEmail(email);
			donorObj.setPassword(password);
			donorObj.setDateOfBirth(dateOfBirth);
			donorObj.setGender(gender);
			//Donor register service
			donorStatusObj = donorServiceObj.donorRegister(donorObj);
		} catch (ServiceException e) {
			errorMSG = e.getMessage();
		}
		String status = null;
		if (donorStatusObj != null) {
			status = "success";
			Message message = new Message(status);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else {
			Message message = new Message(errorMSG);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	/** End **/	
	
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
	
	
	
	@PostMapping("contribute")
	@ApiOperation("Transaction Api")
	@ApiResponses({
		@ApiResponse( code = 200, message = "Transaction success!", response = Message.class),
		@ApiResponse( code = 400, message = "Transaction failed!", response = Message.class)
	})
	public ResponseEntity<?> donorContribute(
				@RequestParam("donorId") Integer donarId,
				@RequestParam("fundrequestId")Integer fundrequestid,
				@RequestParam("amount") Double fundAmount
			)
	{
		Transaction transactionObj = new Transaction();
		Transaction transaction = null;
		
		String errorMsg = null;
		
		Donor donor = new Donor();
		donor.setId(donarId);
		
		FundRequest fr = new FundRequest();
		fr.setId(fundrequestid);
		
		transactionObj.setDonor(donor);
		transactionObj.setFundRequest(fr);
		transactionObj.setAmount(fundAmount);
		
		try {
			transaction = transactionServiceObj.contributeFund(transactionObj);
		} catch (ServiceException e) {
			errorMsg = e.getMessage();
		}
		
		String trasactionStatus = null;
		if(transaction != null)
		{
			trasactionStatus = "success";
			Message message = new Message(trasactionStatus);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else {
			Message message = new Message(errorMsg);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	/** Get fund request **/
	
	@GetMapping("listFundRequest")
	@ApiOperation("List fund request Api")
	@ApiResponses({
		@ApiResponse(code = 200, message = "list success", response = FundRequest.class),
		@ApiResponse(code = 400, message = "list success", response = FundRequest.class)
	})
	
	public ResponseEntity<?> listFundRequest()
	{
		
		List<FundRequest> list = null;
		String errorMessage = null;
		try {
			list = fundServiceObj.listFundRequest();
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
		
		if(list != null)
		{
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		
	}
	/** End **/
	
	
	
	
}
