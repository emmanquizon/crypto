package org.com.bitcoinj;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.Wallet.SendResult;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;

public class SendToken {

    public static void main(String[] args) {


        
        // we get the following from the command line ...
        // (this is not secure - needs validation)
        String network          = "test";  // "test" or "prod"
        String walletFileName   = "test.wallet";  // wallet file name
        String amountToSend     = "2000";  // milli-BTC
        String recipient        =  "mwo5FLc3BrK2BfzxLqsJCY6aL4ai9QdWfA";  // Bitcoin address

        // the Bitcoin network to use
        final NetworkParameters netParams;
        
        // check for production Bitcoin network ...
        if (network.equalsIgnoreCase("prod")) {
            netParams = NetworkParameters.prodNet();
        // ... otherwise use the testnet
        } else {
            netParams = NetworkParameters.testNet();
        }

        // data structure for block chain storage
        BlockStore blockStore = new MemoryBlockStore(netParams);

        // declare object to store and understand block chain
        BlockChain chain;
        
        // declare wallet
        Wallet wallet;
        
        try {
            
            // wallet file that contains Bitcoins we can send
            final File walletFile = new File(walletFileName);

            // load wallet from file
            wallet = Wallet.loadFromFile(walletFile);
            
            System.out.println(""+wallet);
            System.out.println(""+wallet.currentChangeAddress());
            // how man milli-Bitcoins to send
            BigInteger btcToSend = new BigInteger(amountToSend);
                    
            // initialize BlockChain object
            chain = new BlockChain(netParams, wallet, blockStore);

            
            WalletAppKit kit = new WalletAppKit(netParams, new File("."), "forwarding-service-testnet");
            kit.startAsync();
            kit.awaitRunning();
            // instantiate Peer object to handle connections

           
            
            // recipient address provided by official Bitcoin client
            Address recipientAddress = new Address(netParams, recipient);
            
           
            // tell peer to send amountToSend to recipientAddress
            Transaction sendTxn;

			SendResult sendResult = wallet.sendCoins(kit.peerGroup(),recipientAddress,Coin.parseCoin("0.0000056"));

            
            // null means we didn't have enough Bitcoins in our wallet for the transaction
            sendTxn = sendResult.tx;
            if (sendTxn == null) {
                System.out.println("Cannot send requested amount of " + btcToSend
                                + " BTC; wallet only contains " + wallet.getBalance() + " BTC.");
            } else {
                
                // once communicated to the network (via our local peer),
                // the transaction will appear on Bitcoin explorer sooner or later
                System.out.println(btcToSend + " BTC sent. You can monitor the transaction here:\n"
                                + "http://blockexplorer.com/tx/" + sendTxn.getHashAsString());
            }

            // save wallet with new transaction(s)
            wallet.saveToFile(walletFile);

        // handle the various exceptions; this needs more work
        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (AddressFormatException e) {
            e.printStackTrace();
        }catch (UnreadableWalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        }catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientMoneyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}