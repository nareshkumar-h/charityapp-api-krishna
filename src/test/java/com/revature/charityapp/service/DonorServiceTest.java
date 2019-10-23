package com.revature.charityapp.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Donor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DonorServiceTest {
	@Autowired
	private DonorService donorServiceObj;
	/**
	 * Doonr Login
	 * @param email and password
	 * return donor object
	 * **/
	@Test
	public void donorLoginTest()
	{
		Donor donorDetails = null;	
		try {
			Donor donor = new Donor();
			donor.setEmail("krishna192168@gmail.com");
			donor.setPassword("mypass123");
			donorDetails = donorServiceObj.donorLogin(donor);
			System.out.println(donorDetails);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		assertNotNull(donorDetails);
	}
	/**
	 * Doonr Login
	 * return donor list object
	 * **/
	@Test
	public void listDonorTest()
	{
		List<Donor> donorList = null;	
		try {
			donorList = donorServiceObj.listDonors();
			System.out.println(donorList);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		assertNotNull(donorList);
	}
	@Test
	public void donorRegister()
	{
		Donor donorDetailsObj = new Donor();
		try {
			Donor donorObj = new Donor();
			donorObj.setName("ramkumar");
			donorObj.setEmail("ramkumar192@gmail.com");
			donorObj.setPassword("mypass123");
			
			LocalDate dob = LocalDate.parse("1997-06-05");
			donorObj.setDateOfBirth(dob);
			donorObj.setGender("MALE");
			
			donorDetailsObj = donorServiceObj.donorRegister(donorObj);
			System.out.println(donorDetailsObj);
			
		} catch(ServiceException e)
		{
			System.err.println(e.getMessage());
		}
		assertNotNull(donorDetailsObj);
	}
}
