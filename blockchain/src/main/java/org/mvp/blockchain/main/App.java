package org.mvp.blockchain.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mvp.blockchain.model.Transactions;
import org.mvp.blockchain.platform.model.BitcoinTransaction;
import org.mvp.blockchain.platform.model.TxInput;
import org.mvp.blockchain.platform.model.TxOutput;
import org.mvp.blockchain.platform.service.BitcoinService;
import org.mvp.blockchain.platform.service.impl.BitcoinServiceImpl;
import org.mvp.blockchain.util.BitcoinMath;
import org.mvp.blockchain.utility.CsvToJson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		File input = new File("C:\\Users\\emmanuel.quizon\\Desktop\\feat\\transactions-1528357066241.csv");
		BitcoinService btcService = new BitcoinServiceImpl();
		Gson gson = new Gson();
		List<Map<?, ?>> data;
		try {
			data = CsvToJson.readObjectsFromCsv(input);
			JsonParser jsonParser = new JsonParser();
			JsonArray arrayFromString = jsonParser.parse(gson.toJson(data)).getAsJsonArray();
			List<Transactions> transactionList = new ArrayList<Transactions>();
			
			for(JsonElement json : arrayFromString) {
				transactionList.add(gson.fromJson(json, Transactions.class));
			}
			int index = 1;
			for(Transactions t : transactionList) {
				BitcoinTransaction btcTx = btcService.getTransactionDetails(t.getTxId());

				System.out.println(t);
				System.out.println(btcTx);
				if(btcTx != null) {
					if(t.getType().equalsIgnoreCase("Send")) {//Out
						for(TxOutput o : btcTx.getOutputs()) {
							
							if(o.getToAddress().equals(t.getAddress())){
								if(BitcoinMath.satoshiToBtc(Long.valueOf(o.getValue())).toString().equals(t.getAmount()) && 
										btcTx.getCreateDateTime() == Long.valueOf(t.getTime())){
										System.out.println("line "+index++  +" valid");
										break;
								}else {
									System.out.println("line "+index++ +" invalid");
								}
							}
						}
					}else if(t.getType().equalsIgnoreCase("Receive")) {//In
						for(TxInput i : btcTx.getInputs()) {
							
							if(i.getToAddress().equals(t.getAddress())){
								if(BitcoinMath.satoshiToBtc(Long.valueOf(i.getValue())).toString().equals(t.getAmount()) && 
										btcTx.getCreateDateTime() == Long.valueOf(t.getTime())){
										System.out.println("line "+index++  +" valid");
										break;
								}else {
									System.out.println("line "+index++ +" invalid");
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
