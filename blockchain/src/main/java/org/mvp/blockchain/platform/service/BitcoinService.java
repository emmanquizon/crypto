package org.mvp.blockchain.platform.service;

import org.mvp.blockchain.platform.model.BitcoinTransaction;

public interface BitcoinService {

	public BitcoinTransaction getTransactionDetails(String txId);

}
