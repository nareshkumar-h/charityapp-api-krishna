package com.revature.charityapp.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Donor;
import com.revature.charityapp.model.FundRequest;
import com.revature.charityapp.model.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {
	@Autowired
	private TransactionService transactionServiceObj;
	
	@Test
	public void listFundedDonors()
	{
		List<Transaction> list = null;
		
		try {
			list = transactionServiceObj.listFundedDonor();
			System.out.println(list);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		assertNotNull(list);
	}
	
	@Test
	public void contributeFund()
	{
		Transaction transactionObj = null;
		
		
		try {
			Transaction transaction = new Transaction();
			Donor donor = new Donor();
			donor.setId(81);
			FundRequest fundRequest = new FundRequest();
			fundRequest.setId(159);
			
			transaction.setDonor(donor);
			transaction.setFundRequest(fundRequest);
			transaction.setAmount(500D);
			
			transactionObj = transactionServiceObj.contributeFund(transaction);
			System.out.println(transactionObj);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		assertNotNull(transactionObj);
	}
}
