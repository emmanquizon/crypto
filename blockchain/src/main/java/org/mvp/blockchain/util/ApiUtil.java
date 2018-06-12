package org.mvp.blockchain.util;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ApiUtil {


	
	public static JSONObject callApi(String urlx) {

		try {

			JSONObject json = new JSONObject(doApiCall(urlx));			
			return json;

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;
	}
	
	
	public static String callApiStr(String urlx) {

		try {
			
			return doApiCall(urlx);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;
	}


	private static String doApiCall(String urlx) throws IOException, ClientProtocolException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(urlx);
		String str = null;
		
		HttpResponse response = client.execute(request);
		
		HttpEntity responseEntity = response.getEntity();
		if(responseEntity!=null) {
			str = EntityUtils.toString(responseEntity);
			
		}
		return str;
	}	

}
