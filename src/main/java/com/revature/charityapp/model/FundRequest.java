package com.revature.charityapp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="fund_request")
public class FundRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="admin_id")
	private Integer adminId;
	@Column(name="request_type")
	private String requestType;
	@Column(name="description")
	private String description;
	@Column(name="expire_date")
	private LocalDate expireDate;
	@Column(name="date")
	private String date;
	@Column(name="amount")
	private Double amount;
	@Column(name="active")
	private Boolean active;
}
