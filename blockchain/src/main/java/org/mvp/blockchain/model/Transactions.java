package org.mvp.blockchain.model;

import com.google.gson.annotations.SerializedName;

public class Transactions {
	@SerializedName("txid")
	private String txId;
	@SerializedName("time")
	private String time;
	@SerializedName("address")
	private String address;
	@SerializedName("type")
	private String type;
	@SerializedName("amount")
	private String amount;
	@SerializedName("fee")
	private String fee;

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "Transactions [txId=" + txId + ", time=" + time + ", address=" + address + ", type=" + type + ", amount="
				+ amount + ", fee=" + fee + "]";
	}
	
	
}
