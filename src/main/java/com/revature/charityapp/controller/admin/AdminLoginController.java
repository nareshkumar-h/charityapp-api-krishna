package com.revature.charityapp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Admin;
import com.revature.charity.service.AdminService;
import com.revature.charityapp.util.Message;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("admin")
public class AdminLoginController {
	
	/**
	 *Admin login 
	 **/
	
	@Autowired
	private AdminService adminServiceObj;
	
	
	
	@GetMapping("login")
	@ApiOperation(value = "Admin Login")
	@ApiResponses(
			{@ApiResponse(code = 200, message = "Loggin success!"),
			@ApiResponse(code = 400, message = "Loggin failed!")
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

}
