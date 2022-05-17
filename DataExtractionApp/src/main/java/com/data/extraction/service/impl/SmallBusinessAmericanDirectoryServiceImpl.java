package com.data.extraction.service.impl;

import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.extraction.entites.SmallBusinessAmericanDirectory;
import com.data.extraction.repo.SmallBusinessAmericanDirectoryRepo;
import com.data.extraction.service.SmallBusinessAmericanDirectoryService;
import com.data.extraction.utils.JsoupUtils;

@Service
public class SmallBusinessAmericanDirectoryServiceImpl implements SmallBusinessAmericanDirectoryService {

	@Autowired
	private SmallBusinessAmericanDirectoryRepo smallBusinessAmericanDirectoryRepo;

	private static String target_url="http://www.smallbusinessamerica.com/";

	@Override
	public boolean runService() {
		boolean processed=false;
		for (int alphabet = 1; alphabet <=9; alphabet++) {
			try {
				processTragetedUrlWithAlphabet(alphabet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return processed;
	}


	@Override
	public void processTragetedUrlWithAlphabet(int alphabet) {
		String response = JsoupUtils.getURLResponse(target_url+"companies?filter="+alphabet);
		if(response!=null) {
			Document document=Jsoup.parse(response);
			if(document!=null) {
				Integer countPages=null;
				countPages=getTotalCountPages(document);
				if(countPages>=10) {
					for (int pageNo = 10; pageNo <=countPages; pageNo=pageNo+10) {
						try {
							processTragetedUrlWithAlphabetAndPagination(alphabet, pageNo);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else {
					int pageNo=alphabet;
					processTragetedUrlWithAlphabetAndPagination(alphabet, pageNo);
				}
			}
		}
	}


	@Override
	public void processTragetedUrlWithAlphabetAndPagination(int alphabet, int pageNo) {
		String responsePagination = JsoupUtils.getURLResponse(target_url+"companies?filter="+alphabet+"&per_page="+pageNo);
		if(responsePagination!=null) {
			Document paginationDocument=Jsoup.parse(responsePagination);
			if(paginationDocument!=null) {
				String targetedCompaniesUrls="td[style='padding-right:10px;']>a";
				List<String>  companiesUrls=JsoupUtils.getTargetedSelectorWithAttributeList(paginationDocument,targetedCompaniesUrls,"href");

				if(companiesUrls!=null&&companiesUrls.size()>0) {
					for (String companiesUrl : companiesUrls) {
						try {
							String companyUrl="http://www.smallbusinessamerica.com"+companiesUrl;
							String companyResponse = JsoupUtils.getURLResponse(companyUrl);
							if(companyResponse!=null) {
								Document companyDocument=Jsoup.parse(companyResponse);
								if(companyDocument!=null) {
									saveExtractedDataIntoDB(companyDocument,companyUrl);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}


	@Override
	public void saveExtractedDataIntoDB(Document companyDocument,String checkUrl) {
		String companyName=null;
		String logo=null;
		String address=null;
		String companyType=null;
		String fullDescription=null;
		String website=null;

		String companyTypeSelector=".company-details > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td";
		String logoSelector=".panes > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > img";
		String companyNameSelector=".panes > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > img";
		String fullDescriptionSelector=".company-details > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td";
		String websiteSelector=".company-details > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > a";
		String addressSelector=".address";

		companyType=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,companyTypeSelector,"text");
		logo=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,logoSelector,"src");
		companyName=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,companyNameSelector,"alt");
		fullDescription=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,fullDescriptionSelector,"text");
		website=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,websiteSelector,"href");
		address=JsoupUtils.getTargetedSelectorWithAttribute(companyDocument,addressSelector,"text");

		SmallBusinessAmericanDirectory sbad=new SmallBusinessAmericanDirectory();
		sbad.setCompanyName(companyName);
		sbad.setLogo(logo);
		sbad.setCompanyType(companyType);
		sbad.setFullDescription(fullDescription);
		sbad.setAddress(address);
		sbad.setWebsite(website);
		sbad.setCheckUrl(checkUrl);
		sbad.setActivityDataTime(new Date());
		smallBusinessAmericanDirectoryRepo.save(sbad);

		System.out.println(companyName);
	}

	@Override
	public Integer getTotalCountPages(Document document) {
		Integer countPages=0;
		Element countElement = document.select("td.grid-main:nth-child(1) > h4").first();
		if(countElement!=null) {
			String countPagesText[]=countElement.text().split("\\s");
			countPages=Integer.parseInt(countPagesText[0].trim());
		}
		return countPages;
	}


}
