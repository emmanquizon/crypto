package org.mvp.blockchain.model;

import com.google.gson.annotations.SerializedName;

public class BitcoinTransaction {
	@SerializedName("spent")
	private boolean spent;
	@SerializedName("tx_index")
	private int txIndex;
	@SerializedName("type")
	private int type;
	@SerializedName("addr")
	private String addr;
	@SerializedName("value")
	private int value;
	@SerializedName("n")
	private int n;
	@SerializedName("script")
	private String script;

	public boolean isSpent() {
		return spent;
	}

	public void setSpent(boolean spent) {
		this.spent = spent;
	}

	public int getTxIndex() {
		return txIndex;
	}

	public void setTxIndex(int txIndex) {
		this.txIndex = txIndex;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
