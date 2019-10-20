package com.revature.charityapp.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Admin;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {
	
	@Autowired
	private AdminService adminServiceObj;
	
	@Test
	public void adminLoginTest()
	{
		Admin admin = new Admin();
		try {
			Admin adminObj = new Admin();
			adminObj.setEmail("admin@gmail.com");
			adminObj.setPassword("mypass");
			admin = adminServiceObj.adminLogin(adminObj);
			System.out.println(admin);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		assertNotNull(admin);
	}

}
