package com.revature.charityapp.controller.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Donor;
import com.revature.charity.service.DonorService;
import com.revature.charityapp.util.Message;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("donor")
public class DonorLoginController {
	/**
	 *Donor login 
	 *@param Email and Password
	 *@Status{ code => Success => "200", Message => "Login Success!"}
	 **/
	
	@Autowired
	private DonorService donorServiceObj;
	
	@GetMapping("login")
	@ApiOperation(value = "LoginApi")
	 @ApiResponses(value = { @ApiResponse(code = 200, message = "Loggin Success!"),
	 @ApiResponse(code = 400, message = "Login Failed!") })
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
}
