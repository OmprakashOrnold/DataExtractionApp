package com.data.extraction.service;

import org.jsoup.nodes.Document;

public interface SmallBusinessAmericanDirectoryService {
	
	public boolean runService();

	public Integer getTotalCountPages(Document document);

	public void saveExtractedDataIntoDB(Document companyDocument,String checkUrl);

	public void processTragetedUrlWithAlphabetAndPagination(int alphabet, int pageNo);

	public void processTragetedUrlWithAlphabet(int alphabet);

}
