package org.com.bitcoinj;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;

public class LoadTransactions {
	public static void main(String[] args) {
		final NetworkParameters params = NetworkParameters.testNet();
		Address target2 = new Address(params, "2N2iBNXninkMrDrXtBQTFUNmpydiKNBEx6t");
		Transaction tx = new Transaction(params);
		System.out.println(target2);
		
	}
}
