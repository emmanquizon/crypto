package org.com.bitcoinj;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;

import com.google.common.util.concurrent.ListenableFuture;

public class FetchGenesisBlock {

    public static void main(String[] args) {

        // work with testnet
        final NetworkParameters netParams = NetworkParameters.testNet();

        // data structure for block chain storage
        BlockStore blockStore = new MemoryBlockStore(netParams);

        // declare object to store and understand block chain
        BlockChain chain;
        
        // Start up a basic app using a class that automates some boilerplate.
        WalletAppKit kit = new WalletAppKit(netParams, new File("."), "forwarding-service-testnet");
        kit.startAsync();
        kit.awaitRunning();

        try {
        	
            // initialize BlockChain object
            chain = new BlockChain(netParams, blockStore);
            
            PeerGroup pg = kit.peerGroup();
            // instantiate Peer object to handle connections
            final Peer peer = pg.getDownloadPeer();
            


            // we found the hash of the genesis block on Bitcoin Block Explorer 
            /*Sha256Hash blockHash = new Sha256Hash("000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943");*/
            
            Sha256Hash blockHash = new Sha256Hash("000000000000005dd4dc155e3ca736ee36a62bf2fda3802b77162a996ddd24c5");
            
            // ask the node to which we're connected for the block
            // and wait for a response
            Future<Block> future = peer.getBlock(blockHash);
            System.out.println("Waiting for node to send us the requested block: " + blockHash);
            
            // get and use the Block's toString() to output the genesis block
            Block block = future.get();
            System.out.println("Here is the genesis block:\n" + block);
            
            
            for(Transaction t : block.getTransactions()) {

            	if(!t.getInputs().isEmpty()) {
            		for(TransactionInput i : t.getInputs()) {
            			System.out.println("Transaction Input:\n" + i.getValue());
            		}
            	}
            	if(!t.getOutputs().isEmpty()) {
            		for(TransactionOutput o : t.getOutputs()) {
            			if(o.getScriptPubKey() != null ){
	            			if(o.getScriptPubKey().toString().contains("062d30e5ad5941069839270cbd8e590928f0a420")) {
	            				System.out.println("Sent By :\n" + o.getSpentBy());
	            				System.out.println("Transaction Value:\n" + o.getValue());
	            			}
            			}
            		}
            	}
            }
            
            
            Sha256Hash txHash = new Sha256Hash("c49d6c5bf8c08b03dcf988cfc3828c53db46e6fdc0e037411ff2275c141815a5");
            ListenableFuture<Transaction> future2 = peer.getPeerMempoolTransaction(txHash);
            System.out.println("Waiting for node to send us the requested transaction: " + txHash);
            Transaction tx = future2.get();
            System.out.println(tx);

            System.out.println("Waiting for node to send us the dependencies ...");
            List<Transaction> deps = peer.downloadDependencies(tx).get();
            for (Transaction dep : deps) {
                System.out.println("Got dependency " + dep.getHashAsString());
            }

            System.out.println("Done.");
            


        // handle the various exceptions; this needs more work
        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
