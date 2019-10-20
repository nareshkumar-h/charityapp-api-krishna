package com.revature.charityapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Donor;
import com.revature.charityapp.repository.DonorRepository;
import com.revature.charityapp.util.MessageConstant;

@Service
public class DonorService {
	@Autowired
	DonorRepository donorRepositoryObj;
	
	public Donor donorLogin(final Donor donorObj) throws ServiceException
	{
		Donor donor = null;
		String email = donorObj.getEmail();
		String password = donorObj.getPassword();
		donor = donorRepositoryObj.findByEmailAndPassword(email, password);
		if(donor == null)
		{
			throw new ServiceException(MessageConstant.INVALID_LOGIN_CREDENTIALS);
		}
		return donor;
	}
	
	public List<Donor> listDonors() throws ServiceException
	{
		List<Donor> donorList = null;
		
		donorList = donorRepositoryObj.findAll();
		
		if(donorList == null)
		{
			throw new ServiceException(MessageConstant.UNABLE_TO_LIST_DONOR);
		}
		
		return donorList;
		
	}
	
	public Donor donorRegister(final Donor donor) throws ServiceException
	{
		Donor donorObj = null;
		donorObj = donorRepositoryObj.save(donor);
		if(donorObj == null)
		{
			throw new ServiceException(MessageConstant.UNABLE_TO_REGISTER);
		}
		return donorObj;
	}
}
