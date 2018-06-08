package org.com.bitcoinj;

import java.io.File;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

public class LoadWallet {
	public static void main(String[] args) throws BlockStoreException, UnreadableWalletException {

		final NetworkParameters params = NetworkParameters.testNet();
		Wallet wallet = Wallet.loadFromFile(new File("test.wallet"));
		BlockStore blockStore = new MemoryBlockStore(params);
		BlockChain chain = new BlockChain(params, wallet, blockStore);
		PeerGroup peerGroup = new PeerGroup(params, chain);
		peerGroup.addWallet(wallet);
		peerGroup.startAsync();
		peerGroup.awaitRunning();

		Address a = wallet.currentReceiveAddress();
		ECKey b = wallet.currentReceiveKey();
		Address c = wallet.freshReceiveAddress();

		System.out.println(wallet);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(wallet.getChangeAddress());
		
		/*DeterministicKeyChain c = wallet.getActiveKeyChain();
		c.get
		System.out.println("Seed words are: " + Joiner.on(" ").join(seed.getMnemonicCode()));
		System.out.println("Seed birthday is: " + seed.getCreationTimeSeconds());

		String seedCode = "yard impulse luxury drive today throw farm pepper survey wreck glass federal";
		long creationtime = 1409478661L;
		DeterministicSeed seed2 = new DeterministicSeed(seedCode, null, "", creationtime);
		Wallet restoredWallet = Wallet.fromSeed(params, seed);
		System.out.println(restoredWallet);
		wallet.reset();
		System.out.println(wallet.getBalance());*/
		
		 WalletAppKit kit = new WalletAppKit(params, new File("."), "forwarding-service-testnet");
	        kit.startAsync();
	        kit.awaitRunning();
	        
	        System.out.println(kit.wallet());
		
	}
}
