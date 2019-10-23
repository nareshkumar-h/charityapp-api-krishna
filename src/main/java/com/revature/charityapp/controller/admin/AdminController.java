package com.revature.charityapp.controller.admin;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.charityapp.dto.LoginDTO;
import com.revature.charityapp.dto.RaiseFundDTO;
import com.revature.charityapp.dto.UpdateFundDTO;
import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Admin;
import com.revature.charityapp.model.Donor;
import com.revature.charityapp.model.FundRequest;
import com.revature.charityapp.model.Transaction;
import com.revature.charityapp.service.AdminService;
import com.revature.charityapp.service.DonorService;
import com.revature.charityapp.service.FundService;
import com.revature.charityapp.service.TransactionService;
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
	
	@Autowired
	private FundService fundService;
	
	@Autowired
	private TransactionService transactionServiceObj;
	
	/** Login Start **/
	@PostMapping("login")
	@ApiOperation(value = "Admin Login")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Loggin success", response = Admin.class),
		@ApiResponse(code = 400, message = "Loggin failed", response = Admin.class)
			})
	
	public ResponseEntity<?> adminLoginJPA(@RequestBody LoginDTO login)
	{
		String errorMsg = null;
		Admin adminObj = null;
	
		try {
			Admin admin = new Admin();
			admin.setEmail(login.getEmail());
			admin.setPassword(login.getPassword());
			adminObj = adminServiceObj.adminLogin(admin);
		}catch (ServiceException e) {
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
	/** End **/
	
	
	/** Start List donors **/
	
	/**
	 *List donors 
	 *Status:success=>{code=200, message=List success}
	 *Status:failure=>{code=400, message=List failed}
	 *return:loggin success=>{list donor details}
	 *return:loogin failed=>{error message}
	 **/
	@GetMapping("listDonor")
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
			donorList = donorServiceObj.listDonors();
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
		//Prepare for json object
		if(errorMessage != null)
		{
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(donorList, HttpStatus.OK);
		}
		
	}
	
	/** End **/
	
	/** Start **/
	
	/**
	 *Update fund request 
	 *Status:success=>{code=200, message=List success}
	 *Status:failure=>{code=400, message=List failed}
	 *return:loggin success=>{success}
	 *return:loogin failed=>{fialed}
	 **/
	
	
	@PutMapping(value="updateFund")
	@ApiOperation( value = "Update Fund Request Api")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Update Success!", response = FundRequest.class),
			@ApiResponse(code = 400, message = "Update Failed!", response = FundRequest.class) 
	})
	public ResponseEntity<?> updateFundRequest(@RequestBody UpdateFundDTO updateFundDTO)
	{
		int rows = 0;
		String errorMessage = null;
		try {
		
		FundRequest fundRequest = new FundRequest();
		LocalDate expiryDate = LocalDate.parse(updateFundDTO.getExpireDate());
		
		fundRequest.setId(updateFundDTO.getId());
		fundRequest.setAmount(updateFundDTO.getAmount());
		fundRequest.setExpireDate(expiryDate);
		fundRequest.setDescription(updateFundDTO.getDescription());
		fundRequest.setRequestType(updateFundDTO.getRequestType());
		
		rows = fundService.updateFundRequest(fundRequest);
		} catch(ServiceException e){
			errorMessage = e.getMessage();
		}
		 String status = null;
		 if(rows == 1)
		 {
			 status = "success";
			 Message message = new Message(status);
			 return new ResponseEntity<>(message,HttpStatus.OK);
		 } else {
			 Message message = new Message(errorMessage);
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		 }
		
		
	}
	
	/** End **/
	
	/** Raise fund request **/
	
	@PostMapping("raiseFund")
	@ApiOperation(value="Raise fund")
	@ApiResponses( value= {
			@ApiResponse(code=200, message="Fund raise success", response = FundRequest.class),
			@ApiResponse(code=400, message="Fund raise failure", response = FundRequest.class)
	})
	public ResponseEntity<?> raiseFund(@RequestBody RaiseFundDTO raiseFund)
	{
		FundRequest fundRequest= new FundRequest();
		FundRequest fundRequestObj= new FundRequest();
		
		LocalDate fundExpiryDate = LocalDate.parse(raiseFund.getExpiryDate());
		
		fundRequest.setAdminId(raiseFund.getAdminId());
		fundRequest.setRequestType(raiseFund.getRequestType());
		fundRequest.setDescription(raiseFund.getDescription());
		fundRequest.setAmount(raiseFund.getAmount());
		fundRequest.setExpireDate(fundExpiryDate);
		
		String errorMessage = null;
		try {
			fundRequestObj = fundService.raiseFund(fundRequest);
		} catch(ServiceException e) {
			errorMessage = e.getMessage();
		}
		String status = null;
		if(fundRequestObj != null)
		{
			status = "success";
			Message message = new Message(status);
			return new ResponseEntity<>(message,HttpStatus.OK);
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
	}
	/** End **/
	
	/** List contributed donors **/
	
	@GetMapping("listFundedDonor")
	@ApiOperation("List Funded Donor Api")
	@ApiResponses({
		@ApiResponse(code=200, message = "list success", response = Transaction.class),
		@ApiResponse(code=400, message = "list faliure", response = Transaction.class)
	})
	public ResponseEntity<?> listFundedDonor()
	{
		String errorMessage = null;
		List<Transaction> listFudedDonor = null;
		try {
			listFudedDonor = transactionServiceObj.listFundedDonor();
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
		
		if(listFudedDonor != null)
		{
			return new ResponseEntity<>(listFudedDonor, HttpStatus.OK);
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	/** End **/
	
	
	/*List fund request*/
	@GetMapping("listFundDetails")
	@ApiOperation("List fund details")
	@ApiResponses({
		@ApiResponse(code=200, message = "list success", response = FundRequest.class),
		@ApiResponse(code=400, message = "list failure", response = FundRequest.class)
	})
	public ResponseEntity<?> listFundDetails()
	{
		String errorMessage = null;
		List<FundRequest> listFundDetails = null;
		try {
			listFundDetails = fundService.listFundDetails();
		} catch(ServiceException e)
		{
			errorMessage = e.getMessage();
		}
		
		if(listFundDetails != null)
		{
			return new ResponseEntity<>(listFundDetails, HttpStatus.OK);
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
	}
	

}
