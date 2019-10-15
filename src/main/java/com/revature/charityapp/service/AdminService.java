package com.revature.charityapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Admin;
import com.revature.charityapp.repository.AdminRepository;
import com.revature.charityapp.util.MessageConstant;

@Service
public class AdminService {
	@Autowired
	AdminRepository adminRepositoryObj;
	
	public Admin adminLogin(Admin adminObj) throws ServiceException
	{
		Admin admin = null;
		String email = adminObj.getEmail();
		String password = adminObj.getPassword();
		admin = adminRepositoryObj.findByEmailAndPassword(email, password);
		if(admin == null)
		{
			throw new ServiceException(MessageConstant.INVALID_LOGIN_CREDENTIALS);
		}
		return admin;
	}
}
