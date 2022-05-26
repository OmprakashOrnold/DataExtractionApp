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
@Table(name ="ai_hitdata_directory")
public class AIHitdataDirectory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "check_url")
	private String checkUrl;
	
	@Column(name = "activity_date_time")
	private Date activityDataTime;
	

}
