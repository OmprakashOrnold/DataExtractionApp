package com.data.extraction.entites;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

	
@Data
@Entity
@Table(name ="small_business_american_directory")
public class SmallBusinessAmericanDirectory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "logo")
	private String logo;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "company_type")
	private String companyType;
	
	@Column(name = "full_description")
	private String fullDescription;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "check_url")
	private String checkUrl;
	
	@Column(name = "activity_date_time")
	private Date activityDataTime;
	

}
