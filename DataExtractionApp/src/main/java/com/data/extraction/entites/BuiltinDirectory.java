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
@Table(name ="builtin_directory")
public class BuiltinDirectory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "logo")
	private String logo;
	
	@Column(name = "year_founded")
	private String yearFounded;
	
	@Column(name = "total_employees")
	private String totalEmployees;
	
	@Column(name = "perks_overview")
	private String perksOverview;
	
	@Column(name = "street_address_1")
	private String streetAddress1;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "zipcode")
	private String zipcode;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "facebook")
	private String facebook;
	
	@Column(name = "linkedin")
	private String linkedin;
	
	@Column(name = "twitter")
	private String twitter;
	
	@Column(name = "mini_description")
	private String miniDescription;
	
	@Column(name = "industries")
	private String industries;
	

	@Column(name = "check_url")
	private String checkUrl;
	
	@Column(name = "activity_date_time")
	private Date activityDataTime;
	

}
