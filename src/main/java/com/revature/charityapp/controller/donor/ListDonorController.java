package com.revature.charityapp.controller.donor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.charity.exception.ServiceException;
import com.revature.charity.model.Donor;
import com.revature.charity.service.DonorService;
import com.revature.charity.util.Logger;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("donor")
public class ListDonorController {
	/**
	 *List donor 
	 **/
	
	@Autowired
	private DonorService donorService;
	
	@GetMapping("list")
	@ApiOperation(value = "List donor")
	@ApiResponses(
			{
				@ApiResponse(code = 200, message = "List success!"),
				@ApiResponse(code = 400, message = "List not success!")
			}
			)
	public ResponseEntity<?> listDonor()
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
		
		
		if(errorMessage != null)
		{
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(donorList, HttpStatus.OK);
		}
		
		
	}
}
