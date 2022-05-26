package com.data.extraction.service.impl;

import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.extraction.entites.AIHitdataDirectory;
import com.data.extraction.repo.AIHitdataDirectoryRepo;
import com.data.extraction.service.AIHitdataDirectoryService;
import com.data.extraction.utils.JsoupUtils;

@Service
public class AIHitdataDirectoryServiceImpl implements AIHitdataDirectoryService{
	
	@Autowired
	private AIHitdataDirectoryRepo aIHitdataDirectoryRepo;

	private static String target_url="https://www.aihitdata.com/search/companies?i=Healthcare&p=";

	public static Long count=0L;
	
	@Override
	public boolean runService() {
		boolean processed=false;
		for (int pageNo = 1; pageNo <=100; pageNo++) {
			try {
				String responsePagination = JsoupUtils.getURLResponse(target_url+pageNo);
				System.out.println(target_url+pageNo);
				if(responsePagination!=null) {
					Document paginationDocument=Jsoup.parse(responsePagination);
					if(paginationDocument!=null) {
						String targetedCompaniesUrls="a[style='text-decoration: underline;']";
						List<String>  companiesUrls=JsoupUtils.getTargetedSelectorWithAttributeList(paginationDocument,targetedCompaniesUrls,"href");
						companiesUrls.stream().filter(x->x!=null).forEach(x->processCompanyUrl("https://www.aihitdata.com"+x));
						processed=true;
					}
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return processed;
	}

	public boolean processCompanyUrl(String companyUrl) {
		boolean processed=false;
	
		String companyResponse = JsoupUtils.getURLResponse(companyUrl);
		if(companyResponse!=null) {
			Document companyDocument=Jsoup.parse(companyResponse);
			if(companyDocument!=null) {
				saveExtractedDataIntoDB(companyDocument,companyUrl);
				processed=true;
			}
		}
		return processed;
	}
	
	public void saveExtractedDataIntoDB(Document companyDocument,String checkUrl) {
		
		String companyName=null;
		String url=null;
		String description=null;
		String address=null;

	
		String companyNameSelector=".text-info";
		String descriptionSelector=".col-md-8 > div:nth-child(2) > div>div+div";
		String urlSelector="i[class='icon-sm icon-home']+a";
		String addressSelector="div.text-muted:nth-child(4)";

		companyName=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,companyNameSelector,"text");
		url=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,urlSelector,"href");
		address=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,addressSelector,"text");
		String o_description=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,descriptionSelector,"text");
		if(o_description!=null&&!o_description.isEmpty()) {
			description=o_description.replaceAll("[\\r\\n]+", "").trim();
		}
		count=count+1;
		System.out.println(count+" "+companyName);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		AIHitdataDirectory ahdd=new AIHitdataDirectory();
		ahdd.setUrl(url);
		ahdd.setCompanyName(companyName);
		ahdd.setAddress(address);
		ahdd.setDescription(description);
		ahdd.setCheckUrl(checkUrl);
		ahdd.setActivityDataTime(new Date());
		
		aIHitdataDirectoryRepo.save(ahdd);
	}

}
