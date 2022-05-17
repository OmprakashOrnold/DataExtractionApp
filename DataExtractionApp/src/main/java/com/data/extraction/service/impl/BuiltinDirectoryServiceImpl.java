package com.data.extraction.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.extraction.entites.BuiltinDirectory;
import com.data.extraction.repo.BuiltinDirectoryRepo;
import com.data.extraction.service.BuiltinDirectoryService;
import com.data.extraction.utils.JSONUtils;
import com.data.extraction.utils.JsoupUtils;

@Service
public class BuiltinDirectoryServiceImpl implements BuiltinDirectoryService{

	@Autowired
	private BuiltinDirectoryRepo builtinDirectoryRepo;

	@Override
	public boolean runService() {
		boolean processed=false;
		File folder = new File("D:\\Selenium\\downloads\\others");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				File fullFileName = new File("D:\\Selenium\\downloads\\others\\"+file.getName());
				Document document=null;
				try {
					document = Jsoup.parse(fullFileName, "UTF-8");
					if(document!=null) {
						List<String> companyUrlList=getCompanyUrls(document);
						companyUrlList.stream().forEach(x->processJSONUrl(x));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return processed;
	}

	private void processJSONUrl(String jsonUrl) {
		String response = JSONUtils.readJSONFromUrlWithOkhttp(jsonUrl);
		String title=null;
		String year_founded=null;
		String total_employees=null;
		String perks_overview=null;
		String street_address_1=null;
		String city=null;
		String state=null;
		String zipcode=null;
		String email=null;
		String facebook=null;
		String linkedin=null;
		String twitter=null;
		String url=null;
		String logo=null;
		String industries=null;
		String mini_description=null;
		if(response!=null) {
			JSONObject jsonObject=new JSONObject(response);
			if(jsonObject!=null) {
				title = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "title");
				String or_year_founded = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "year_founded");
				if(or_year_founded.contains("-")){
					String year_founded_arr[]=or_year_founded.split("-");
					year_founded=year_founded_arr[0].trim();
				}else {
					year_founded=or_year_founded;
				}
				
				total_employees = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "total_employees");
				perks_overview = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "perks_overview");
				street_address_1 = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "street_address_1");
				city = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "city");
				state = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "state");
				zipcode = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "zipcode");
				email = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "email");
				facebook = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "facebook");
				linkedin = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "linkedin");
				twitter = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "twitter");
				url = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "url");
				logo = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "logo");
				mini_description = JSONUtils.getSpecificFeildFromJSONObject(jsonObject, "mini_description");
				industries = JSONUtils.getInnerJsonObjectCommaSepStrings(jsonObject, "industries", "name");
			
				
				BuiltinDirectory bd=new BuiltinDirectory();
				bd.setCompanyName(title);
				bd.setUrl(url);
				bd.setLogo("https://builtin.com/cdn-cgi/image/fit=scale-down,sharpen=0.3,f=auto,q=100,w=120,h=120/sites/www.builtin.com/files/"+logo.replace(" ","%20"));
				bd.setYearFounded(year_founded);
				bd.setTotalEmployees(total_employees);
				bd.setPerksOverview(perks_overview);
				bd.setStreetAddress1(street_address_1);
				bd.setCity(city);
				bd.setState(state);
				bd.setZipcode(zipcode);
				bd.setEmail(email);
				bd.setFacebook(facebook);
				bd.setLinkedin(linkedin);
				bd.setTwitter(twitter);
				bd.setIndustries(industries);
				bd.setMiniDescription(mini_description);
				bd.setCheckUrl(jsonUrl);
				bd.setActivityDataTime(new Date());
				builtinDirectoryRepo.save(bd);
				System.out.println(url);
			}
		}
	}

	private List<String>  getCompanyUrls(Document document) {
		List<String> companyUrlList=new ArrayList<String>();
		String targetedCompaniesUrls="div[class='d-flex align-center']>a";
		List<String> companiesUrls=JsoupUtils.getTargetedSelectorWithAttributeList(document,targetedCompaniesUrls,"href");
		companiesUrls.stream().filter(x->x!=null&&!x.isEmpty())
		.map(x->x.replace("/company/", "").trim()).forEach(x->companyUrlList.add("https://api.builtin.com/companies/alias/"+x+"?region_id=9"));
		return companyUrlList;
	}


}
