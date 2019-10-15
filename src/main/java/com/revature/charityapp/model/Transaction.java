package com.revature.charityapp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fund_request_id")
	private FundRequest fundRequest;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="donor_id")
	private Donor donor;
	
	@Column(name="amount")
	private Double amount;
	@Column(name="date")
	private LocalDate date;
}
