package com.revature.charityapp.controller.admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Admin;
import com.revature.charity.model.Donor;
import com.revature.charity.model.FundRequest;
import com.revature.charity.service.AdminService;
import com.revature.charity.service.DonorService;
import com.revature.charity.service.FundRequestService;
import com.revature.charity.util.Logger;
import com.revature.charityapp.util.Message;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("admin")
public class AdminController {
	
	/**
	 *Admin login 
	 *@Param input=>{email,password}
	 *Status:success=>{code=200, message=Loggin success}
	 *Status:failure=>{code=400, message=Loggin failed}
	 *return:loggin success=>{id,email,username}
	 *return:login failed=>{login failed}
	 **/
	
	@Autowired
	private AdminService adminServiceObj;
	
	@Autowired
	private DonorService donorServiceObj;
	
	
	@GetMapping("login")
	@ApiOperation(value = "Admin Login")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Loggin success", response = Admin.class),
		@ApiResponse(code = 400, message = "Loggin failed", response = Admin.class)
			})
	
	public ResponseEntity<?> adminLogin(
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

		if (adminObj != null) {
			return new ResponseEntity<>(adminObj,HttpStatus.OK);
		} else {
			Message message = new Message(errorMsg);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *List contributed donors 
	 *Status:success=>{code=200, message=List success}
	 *Status:failure=>{code=400, message=List failed}
	 *return:loggin success=>{list funded donors details}
	 *return:loogin failed=>{error message}
	 **/
	
	@GetMapping("fundedDonors")
	@ApiOperation(value = "List Contributed Donors")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="List Success"),
			@ApiResponse(code=400, message="List Failure")
	})
	public ResponseEntity<?> listFundedDonors()
	{
		List<Donor> listDonor = null;
		String errorMessage = null;
		try {
		listDonor = donorServiceObj.listFundedDonor();
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
		
		if(listDonor != null)
		{
			return new ResponseEntity<>(listDonor, HttpStatus.OK);
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *List donors 
	 *Status:success=>{code=200, message=List success}
	 *Status:failure=>{code=400, message=List failed}
	 *return:loggin success=>{list donor details}
	 *return:loogin failed=>{error message}
	 **/
	@GetMapping("list")
	@ApiOperation(value = "List Donor")
	@ApiResponses(
			value = {
				@ApiResponse(code = 200, message = "List success!", response = Donor.class),
				@ApiResponse(code = 400, message = "List not success!", response = Donor.class)
			}
			)
	public ResponseEntity<?> listDonor()
	{
		
		List<Donor> donorList = null;
		String errorMessage = null;
		try {
			donorList = donorServiceObj.donorList();
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Logger.error(e.getMessage());
		}
		//Prepare for json object
		
		
		if(errorMessage != null)
		{
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(donorList, HttpStatus.OK);
		}
		
	}
	
	/**
	 *Update fund request 
	 *Status:success=>{code=200, message=List success}
	 *Status:failure=>{code=400, message=List failed}
	 *return:loggin success=>{success}
	 *return:loogin failed=>{fialed}
	 **/
	
	@Autowired
	private FundRequestService fundRequestService;
	
	@PutMapping(value="updateFund")
	@ApiOperation( value = "Update Fund Request Api")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Update Success!", response = FundRequest.class),
			@ApiResponse(code = 400, message = "Update Failed!", response = FundRequest.class) 
	})
	public ResponseEntity<?> updateFundRequest(
				@RequestParam("fundId")String id,
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
		 
		 String status = null;
		 if(Boolean.TRUE.equals(isFundUpdated))
		 {
			 status = "success";
			 Message message = new Message(status);
			 return new ResponseEntity<>(message,HttpStatus.OK);
		 } else {
			 status = "failed";
			 Message message = new Message(status);
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		 }
		
		
	}

}
