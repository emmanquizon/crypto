package org.mvp.blockchain.bitcoin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class BitcoinService {

	public String rawtx(String txHash) {
		StringBuilder sb = new StringBuilder("https://testnet.blockchain.info/rawtx/");
		sb.append(txHash);
		return httpGetCall(sb.toString());
	}

	public String httpGetCall(String url) {
		System.out.println(url);
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		StringBuilder sb = new StringBuilder();
		try {
			HttpResponse response = client.execute(request);
			
			HttpEntity responseEntity = response.getEntity();
			if(responseEntity!=null) {
				sb.append(EntityUtils.toString(responseEntity));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.toString();
	}
}
