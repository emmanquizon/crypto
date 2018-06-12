package org.mvp.blockchain.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.mvp.blockchain.platform.model.BitcoinTransaction;
import org.mvp.blockchain.platform.model.TxInput;
import org.mvp.blockchain.platform.model.TxOutput;
import org.mvp.blockchain.platform.service.BitcoinService;
import org.mvp.blockchain.util.ApiUtil;

import com.google.gson.Gson;

public class BitcoinServiceImpl implements BitcoinService{

	
	public BitcoinTransaction getTransactionDetails(String txId) {
		Gson gson = new Gson();
		JSONObject json = ApiUtil.callApi("https://testnet.blockchain.info/rawtx/"+txId);
		BitcoinTransaction btc = gson.fromJson(json.toString(), BitcoinTransaction.class);

		List<TxOutput> out = new ArrayList<TxOutput>();
		for(Object jArr : json.getJSONArray("out")) {
			out.add(gson.fromJson(jArr.toString(), TxOutput.class));
		}
		
		List<TxInput> in = new ArrayList<TxInput>();
		for(Object jArr : json.getJSONArray("inputs")) {
			Object prev_out = new JSONObject(jArr.toString()).get("prev_out");
			in.add(gson.fromJson(prev_out.toString(), TxInput.class));
		}
		btc.setInputs(in);
		btc.setOutputs(out);
		
		long txFee = Long.valueOf(ApiUtil.callApiStr("https://testnet.blockchain.info/q/txfee/"+txId));
		
		btc.setTransactionFee(txFee);
		
		return btc;
	}

	
}
