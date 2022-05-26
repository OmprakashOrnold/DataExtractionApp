package com.data.extraction.service.impl;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.data.extraction.service.Saastrannual2022SponsersService;
import com.data.extraction.utils.JsoupUtils;

@Service
public class Saastrannual2022SponsersServiceImpl implements Saastrannual2022SponsersService{

	private static String target_url="https://www.saastrannual2022.com/sponsor";

	@Override
	public boolean runService() {
		boolean processed=false;
		String target_response = JsoupUtils.getURLResponse(target_url);
		if(target_response!=null) {
			Document document=Jsoup.parse(target_response);
			if(document!=null) {
				String targetedCompaniesUrls="a[class='gallery-grid-image-link']";
				String targetedCompaniesUrlsImg="a[class='gallery-grid-image-link']>img";
				List<String>  companiesUrls=JsoupUtils.getTargetedSelectorWithAttributeList(document,targetedCompaniesUrls,"href");
				companiesUrls.stream().filter(x->x!=null).forEach(x->System.out.println(x));
				
				System.out.println();
				List<String>  companiesNames=JsoupUtils.getTargetedSelectorWithAttributeList(document,targetedCompaniesUrlsImg,"alt");
				companiesNames.stream().filter(x->x!=null).forEach(x->System.out.println(x.replace("_", "").replace(".jpg", ".")
						.replace(".png", "")));
				
				System.out.println();
				
				List<String>  companiesImgages=JsoupUtils.getTargetedSelectorWithAttributeList(document,targetedCompaniesUrlsImg,"data-image");
				companiesImgages.stream().filter(x->x!=null).forEach(x->System.out.println(x));
			
			}
	
	}
		return processed;
	}
}
