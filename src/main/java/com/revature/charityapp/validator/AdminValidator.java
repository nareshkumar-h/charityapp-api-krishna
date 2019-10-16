package com.revature.charityapp.validator;

import com.revature.charityapp.exception.ValidatorException;
import com.revature.charityapp.model.Admin;
import com.revature.charityapp.util.MessageConstant;

public class AdminValidator {
	
	private AdminValidator()
	{}
	static AdminValidator adminValidator = null;
	
	/**
	 * Get instance of donor validator class 
	**/
	public static AdminValidator getInstance()
	{
		if(adminValidator == null)
		{
			adminValidator = new AdminValidator();
		}
		return adminValidator;
	}
	/** Login validator **/
	public void loginValidator(Admin admin) throws ValidatorException
	{
		String email = admin.getEmail();
		String password = admin.getPassword();
		
		if(email == null || "".equals(email))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL);
		}
		if(password == null || "".equals(password))
		{
			throw new ValidatorException(MessageConstant.INVALID_PASSWORD);
		}
	}
}
