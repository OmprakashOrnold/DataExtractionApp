package com.data.extraction.utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class JsoupUtils{

	public static String getURLResponse(String company_url) {
		Document document=null;;
		try {

			SSLExceptionSolution.enableSSLSocket();
			document = Jsoup.connect(company_url).followRedirects(true).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0)       Gecko/20100101 Firefox/46.0").ignoreHttpErrors(true)
					.timeout(20000).get();

			if (document == null) {
				OkHttpClient client = new OkHttpClient();
				Request request = new Request.Builder().url(company_url).get()
						.addHeader("cache-control", "no-cache").build();
				String response = client.newCall(request).execute().body().string();
				document = Jsoup.parse(response);
			}

			if (document == null) {
				String line = null;
				String finalline = null;
				URL url = new URL(company_url);
				URLConnection urlConnection = url.openConnection();
				HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) urlConnection;
				urlConnection.setConnectTimeout(20000);
				httpsUrlConnection.setConnectTimeout(20000);
				SSLSocketFactory sslSocketFactory = createSslSocketFactory();
				httpsUrlConnection.setSSLSocketFactory(sslSocketFactory);
				try (InputStream inputStream = httpsUrlConnection.getInputStream()) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					while ((line = reader.readLine()) != null) {
						finalline = finalline + line;
					}
				}
				document = Jsoup.parse(finalline);
			}
		} catch (Exception e) {
			return null;
		}
		return document.toString();
	}

	public static SSLSocketFactory createSslSocketFactory() throws Exception {
		TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}
		} };
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, byPassTrustManagers, new SecureRandom());
		return sslContext.getSocketFactory();
	}

	public static String getTargetedSelectorWithAttribute(Document document,String tragetedSelector,String attributName) {
		String targetedValue="";
		Element element = document.select(tragetedSelector).first();	
		if(element!=null) {
			if(attributName.equals("text")&&element.hasText()) {
				targetedValue=element.text();
			}else if(attributName.equals("href")&&element.hasAttr("href")) {
				targetedValue=element.attr("href");
			}else if(attributName.equals("alt")&&element.hasAttr("alt")) {
				targetedValue=element.attr("alt");
			}else if(attributName.equals("src")&&element.hasAttr("src")) {
				targetedValue=element.attr("src");
			}
		}
		return targetedValue;
	}

	public static List<String>  getTargetedSelectorWithAttributeList(Document document,String targetedSelector,String attribute) {
		List<String> targetResultList=new ArrayList<String>();
		Elements elements = document.select(targetedSelector);
		
		if(attribute.equals("text")) {
			List<String> targetedResults = elements.stream()
					.filter(x->x != null && x.childNodeSize()>0)
					.filter(x->x.hasText())
					.map(x->x.text())
					.collect(Collectors.toList());
			targetedResults.stream().filter(y->y!=null).forEach(x->targetResultList.add(x));
		}else{
			List<String> targetedResults = elements.stream()
					.filter(x->x != null && x.childNodeSize()>0)
					.filter(x->x.hasAttr(attribute))
					.map(x->x.attr(attribute))
					.collect(Collectors.toList());
			targetedResults.stream().filter(y->y!=null).forEach(x->targetResultList.add(x));

		}
		return targetResultList;
	}
}