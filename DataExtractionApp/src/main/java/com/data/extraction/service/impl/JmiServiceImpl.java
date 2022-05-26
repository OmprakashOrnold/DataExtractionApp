package com.data.extraction.service.impl;

import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.data.extraction.utils.JsoupUtils;

@Service
public class JmiServiceImpl {

	private static String target_url="https://www.jmi.com/companies/";

	public static  boolean runService() {
		boolean processed=false;
		String responsePagination = JsoupUtils.getURLResponse(target_url);
		String companyName=null;
		String description=null;
		String status=null;
		String website=null;
		String logo=null;
		String linkdein=null;
		if(responsePagination!=null) {
			Document paginationDocument=Jsoup.parse(responsePagination);
			if(paginationDocument!=null) {
				String mainFrame="div[class='popup-content-box']";
				Elements elements=paginationDocument.select(mainFrame);
				String logoSelector="div[class='popup-content-box']+div>img";
				Elements logo_elments = paginationDocument.select(logoSelector);
				if (logo_elments != null && logo_elments.size() > 0) {
					for (Element logo_elment : logo_elments) {
						
						try {
							logo = logo_elment.attr("src");
System.out.println(logo);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(elements!=null&&elements.size()>0){
					for (Element element : elements) {
						/*
						 * companyName=""; website=""; description=""; status=""; logo="";
						 * 
						 * String nameSelector="div[class='popup-content-box']>div+div>h2"; String
						 * descSelector="div[class='popup-content-box']>div+div>h2+p"; String
						 * statusSelector="div[class='popup-content-box']>div>div"; String
						 * linkedinSelector="div[class='popup-content-box']>div+div>h2+p+p>a"; String
						 * websiteSelector="div[class='popup-content-box']>div>div+p>a";
						 * 
						 * 
						 * Elements name_elments = element.select(nameSelector); if (name_elments !=
						 * null && name_elments.size() > 0) { for (Element name_elment : name_elments) {
						 * 
						 * try { companyName = name_elment.text();
						 * 
						 * } catch (Exception e) { e.printStackTrace(); } } }
						 * 
						 * Elements des_elments = element.select(descSelector); if (des_elments != null
						 * && des_elments.size() > 0) { for (Element des_elment : des_elments) { try {
						 * description = des_elment.text();
						 * 
						 * 
						 * } catch (Exception e) { e.printStackTrace(); } } }
						 * 
						 * Elements web_elments = element.select(websiteSelector); if (web_elments !=
						 * null && web_elments.size() > 0) { for (Element web_elment : web_elments) {
						 * 
						 * try { website = web_elment.attr("href");
						 * 
						 * 
						 * } catch (Exception e) { e.printStackTrace(); } } }
						 * 
						 * Elements status_elments = element.select(statusSelector); if (status_elments
						 * != null && status_elments.size() > 0) { for (Element status_elment :
						 * status_elments) {
						 * 
						 * try { status = status_elment.text();
						 * 
						 * 
						 * } catch (Exception e) { e.printStackTrace(); } } }
						 * 
						 * 
						 * 
						 * 
						 * 
						 * System.out.println(companyName); System.out.println(description);
						 * System.out.println(website); System.out.println(status.replace("Status: ",
						 * "")); System.out.println(logo);
						 * 
						 * System.out.println();
						 * 
						 * String outputFile = "D:\\Selenium\\downloads\\zoominfo excel2\\jimi.com.csv";
						 * boolean alreadyExists = new File(outputFile).exists();
						 * 
						 * try { CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true),
						 * ','); if (!alreadyExists) { csvOutput.write("companyName");
						 * csvOutput.write("description"); csvOutput.write("website");
						 * csvOutput.write("status"); csvOutput.write("logo"); csvOutput.endRecord();
						 * 
						 * }
						 * 
						 * csvOutput.write(companyName); csvOutput.write(description);
						 * csvOutput.write(website); csvOutput.write(status.replace("Status: ", ""));
						 * csvOutput.write(logo);
						 * 
						 * csvOutput.endRecord();
						 * 
						 * csvOutput.close(); } catch (Exception e) { e.printStackTrace(); }
						 */}
				}
			}
		}




		return processed;
	}

}
