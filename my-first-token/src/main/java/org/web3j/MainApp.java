package org.web3j;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.contracts.generated.Crowdsale;
import org.web3j.contracts.generated.TokenERC20;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

public class MainApp {
    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        new MainApp().run();
    }

    private void run() throws Exception {

        // We start by creating a new web3j instance to connect to remote nodes on the network.
        // Note: if using web3j Android, use Web3jFactory.build(...
        Web3j web3j = Web3j.build(new HttpService(
                "https://rinkeby.infura.io/bIiZ9ZlgIRraYClar19x"));  // FIXME: Enter your Infura token here;
        log.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        
        
        // We then need to load our Ethereum wallet file
        // FIXME: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html
        System.out.println("====LOADING CREDENTIALS===================================================================================");

        Credentials credentials =
                WalletUtils.loadCredentials(
                        "Eman##28",
                        "C:\\dev\\web3j-3.4.0\\keystore\\UTC--2018-06-04T07-55-09.397000000Z--fe755d7d1079194a3f0a63439796c9ed3554d219.json");
        log.info("Credentials loaded");
        
        
      //GETTING THE BALANCE
        EthGetBalance bal = web3j.ethGetBalance(credentials.getAddress() , DefaultBlockParameterName.LATEST).send();
        System.out.println("====GETTING CURRENT BALANCE===================================================================================");
        log.info("CURRENT BALANCE : "+Convert.fromWei(bal.getBalance().toString(),Unit.ETHER));
        	 
        // FIXME: Request some Ether for the Rinkeby test network at https://www.rinkeby.io/#faucet
        System.out.println("====SEND ETHER===================================================================================");
        log.info("Sending 1 Wei ("
                + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
        TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentials,
                "0xf7D1666Aa7b70Be8Af3f21314A8178E09cc590a0",  // you can put any address here
                BigDecimal.valueOf(0.5), Convert.Unit.ETHER)  // 1 wei = 10^-18 Ether
                .send();
        System.out.println("====TRANSACTION COMPLETE===================================================================================");
        log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());
        
        
        //GETTING THE BALANCE
        EthGetBalance bal1 = web3j.ethGetBalance(credentials.getAddress() , DefaultBlockParameterName.LATEST).send();
        System.out.println("====GETTING CURRENT BALANCE===================================================================================");
        log.info("CURRENT BALANCE : "+Convert.fromWei(bal1.getBalance().toString(),Unit.ETHER));
        
        
        
        // Now lets deploy a smart contract
        System.out.println("====DEPLOYING TOKEN===================================================================================");
        TokenERC20 token1 = TokenERC20.deploy(
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
                BigInteger.valueOf(21000000), "Emmaneum", "EMN").send();

        String contractAddress = token1.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress);
        log.info("View contract at https://rinkeby.etherscan.io/address/" + contractAddress);

/*        log.info("Value stored in remote smart contract: " + token1.balanceOf(contractAddress).send());
        log.info("Value stored in remote smart contract: " + token1.balanceOf(credentials.getAddress()).send());
        log.info("transferring to  0x238Db2BCE0a7D50044F200375E56271308C45B98");*/

        
        
        System.out.println("====DEPLOYING CROWD SALE===================================================================================");

        Crowdsale crowd = Crowdsale.deploy(
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, credentials.getAddress(),
                BigInteger.valueOf(50), BigInteger.valueOf(30), BigInteger.valueOf(100), contractAddress).send();
        

        log.info("crowd sale deployed to address " + crowd.getContractAddress());



        
        
        System.out.println("====GETTING TRANSACTION===================================================================================");
        
        EthGetTransactionReceipt tr = web3j.ethGetTransactionReceipt("0xf00225afafd5eedc4a6782440509fa0dcc4cea507c1a4e42f1ad2f931797b24e").send();
  	 
        log.info(tr.getTransactionReceipt().get().toString());
        
        
        
        
        
        
        System.out.println("====GETTING BLOCK===================================================================================");

        EthBlock eb = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(tr.getTransactionReceipt().get().getBlockNumber()), true).send();

        
		System.out.println("Number "+eb.getBlock().getNumber());
        System.out.println("Hash "+eb.getBlock().getHash());
        System.out.println("Parent Hash "+eb.getBlock().getParentHash());
        System.out.println("Nonce "+eb.getBlock().getNonce());
        System.out.println("Sha3uncles "+eb.getBlock().getSha3Uncles());
        System.out.println("Logs Bloom "+eb.getBlock().getLogsBloom());
        System.out.println("Transaction Root "+eb.getBlock().getTransactionsRoot());
        System.out.println("State Root "+eb.getBlock().getStateRoot());
        System.out.println("Receipts Root "+eb.getBlock().getReceiptsRoot());
        System.out.println("Author "+eb.getBlock().getAuthor());
        System.out.println("Miner "+eb.getBlock().getMiner());
        System.out.println("Mix Hash "+eb.getBlock().getMixHash());
        System.out.println("Difficulty "+eb.getBlock().getDifficulty());
        System.out.println("Total Difficulty "+eb.getBlock().getTotalDifficulty());
        System.out.println("Extra Data "+eb.getBlock().getExtraData());
        System.out.println("Size "+eb.getBlock().getSize());
        System.out.println("Gas Limit "+eb.getBlock().getGasLimit());
        System.out.println("Gas Used "+eb.getBlock().getGasUsed());
        System.out.println("Time Stamp "+eb.getBlock().getTimestamp());
        System.out.println("Transactions"+" "+eb.getBlock().getTransactions());
        System.out.println("Uncles"+" "+eb.getBlock().getUncles());
        System.out.println("Seal Fields"+" "+eb.getBlock().getSealFields());
        //TransactionReceipt tr = contract.transfer("0x238Db2BCE0a7D50044F200375E56271308C45B98", BigInteger.valueOf(1302035)).send();

        //log.info("Gas used: " +  tr.getGasUsed());
        
        // Lets modify the value in our smart contract
        /*       TransactionReceipt transactionReceipt = contract.newGreeting("Well hello again").send();

        log.info("New value stored in remote smart contract: " + contract.greet().send());

        // Events enable us to log specific events happening during the execution of our smart
        // contract to the blockchain. Index events cannot be logged in their entirety.
        // For Strings and arrays, the hash of values is provided, not the original value.
        // For further information, refer to https://docs.web3j.io/filters.html#filters-and-events
        for (Greeter.ModifiedEventResponse event : contract.getModifiedEvents(transactionReceipt)) {
            log.info("Modify event fired, previous value: " + event.oldGreeting
                    + ", new value: " + event.newGreeting);
            log.info("Indexed event previous value: " + Numeric.toHexString(event.oldGreetingIdx)
                    + ", new value: " + Numeric.toHexString(event.newGreetingIdx));
        }
        
        
       log.info("==================================================================================");

        
        log.info("Deploying  new smart contract");
        MyToken contract2 = MyToken.deploy(
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();

        String contractAddress2 = contract2.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress2);
        log.info("View contract at https://rinkeby.etherscan.io/address/" + contractAddress2);

        

        log.info("trigger constructor 1: " + contract2.MyFirstToken().send());
        
        log.info("transferring 1 wei: " + contract2.transferFrom(contractAddress2, credentials.getAddress(), BigInteger.ONE).send());
        

        log.info("Current balance in remote smart contract: " + contract2.balanceOf(contractAddress2).send());
        log.info("Current balance in remote smart contract: " + contract2.balanceOf(credentials.getAddress()).send());*/

    }
    
/*    public void refreshStatus(String transactionId) {
    	  LOG.info("Polling TX status...");

    	  Transaction tx = new TragetTransaction(transactionId);
    	  EthGetTransactionReceipt txReceipt = null;

    	  try {
    	    txReceipt = getWeb3j().ethGetTransactionReceipt(tx.getHash()).sendAsync().get();
    	  }
    	  catch (Exception e) {
    	    throw new ProcessingException("failed to poll status for transaction " + tx.getSignedContent(), e);
    	  }

    	  tx.setTransactionReceipt(txReceipt.getResult());
    	  LOG.info("Successfully polled status. Status: " + tx.getStatus());

    	  save(tx);

    	}*/
}
