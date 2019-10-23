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
import com.revature.charityapp.model.FundRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FundServiceTest {
	
	@Autowired
	private FundService fundServiceObj;
	
	@Test
	public void raseFundTest()
	{
		FundRequest fundRequest = new FundRequest();
		
		try {
			FundRequest fundRequestObj = new FundRequest();
			fundRequestObj.setAdminId(1);
			fundRequestObj.setAmount(1000D);
			LocalDate expireDate = LocalDate.now();
			fundRequestObj.setExpireDate(expireDate);
			fundRequestObj.setRequestType("OTHERS");
			fundRequestObj.setDescription("fund for tree planting");
			fundRequest = fundServiceObj.raiseFund(fundRequestObj);
			System.out.println(fundRequest);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void updateFundTest()
	{
		int rows = 0;
		
		try {
			FundRequest fundRequestObj = new FundRequest();
			fundRequestObj.setId(159);;
			fundRequestObj.setAmount(1000D);
			LocalDate expireDate = LocalDate.now();
			fundRequestObj.setExpireDate(expireDate);
			fundRequestObj.setRequestType("OTHERS");
			fundRequestObj.setDescription("fund for tree planting");
			rows = fundServiceObj.updateFundRequest(fundRequestObj);
			System.out.println(rows);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		
	}
	@Test
	public void listFundRequest()
	{
		List<FundRequest> listFund = null;
		
		try {
			listFund = fundServiceObj.listFundRequest();
			System.out.println(listFund);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		assertNotNull(listFund);
	}
	@Test
	public void listFundDetails()
	{
		List<FundRequest> listFund = null;
		
		try {
			listFund = fundServiceObj.listFundDetails();
			System.out.println(listFund);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		assertNotNull(listFund);
	}
}
