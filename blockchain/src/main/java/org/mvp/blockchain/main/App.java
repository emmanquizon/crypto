package org.mvp.blockchain.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mvp.blockchain.model.Transactions;
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

		File input = new File("C:\\Users\\jeriel.mercado\\Downloads\\transactions-1528357066241.csv");
	//	File output = new File("/x/data.json");
		Gson gson = new Gson();
		List<Map<?, ?>> data;
		try {
			data = CsvToJson.readObjectsFromCsv(input);
			JsonParser jsonParser = new JsonParser();
			JsonArray arrayFromString = jsonParser.parse(gson.toJson(data)).getAsJsonArray();
			List<Transactions> transactionList = new ArrayList<>();
			for(JsonElement json : arrayFromString) {
				transactionList.add(gson.fromJson(json, Transactions.class));
			}
			System.out.println(transactionList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
