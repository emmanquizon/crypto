package org.com.bitcoinj;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.AbstractWalletEventListener;

public class RefreshWallet {
	public static void main(String[] args) throws UnreadableWalletException, BlockStoreException, IOException {
		File file = new File("test.wallet");
		Wallet wallet = Wallet.loadFromFile(file);
		System.out.println(wallet.toString());

		// Set up the components and link them together.
		final NetworkParameters params = TestNet3Params.get();
		BlockStore blockStore = new MemoryBlockStore(params);
		BlockChain chain = new BlockChain(params, wallet, blockStore);

		final PeerGroup peerGroup = new PeerGroup(params, chain);
		peerGroup.addAddress(new PeerAddress(InetAddress.getLocalHost()));
		peerGroup.startAsync();
		peerGroup.awaitRunning();
		wallet.addEventListener(new AbstractWalletEventListener() {
			@Override
			public synchronized void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
				System.out.println("\nReceived tx " + tx.getHashAsString());
				System.out.println(tx.toString());
			}
		});

		// Now download and process the block chain.
		peerGroup.downloadBlockChain();
		peerGroup.stopAsync();
		wallet.saveToFile(file);
		System.out.println("\nDone!\n");
		System.out.println(wallet.toString());
	}
}
