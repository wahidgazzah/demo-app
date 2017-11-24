package fr.demo.data.accessdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.demo.dto.AccountDTO;
import fr.demo.dto.TransactionDTO;
import fr.demo.dto.UserDTO;

/**
 * Data Instance to store the data for the tests
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class DataInstance implements IDataInstance{
	
	/**
	 * Map to store the data
	 */
	private Map<String, UserDTO> map = new HashMap<String, UserDTO>();

	private static DataInstance INSTANCE = null;
	
	/**
	 * getInstance function
	 * @return DataInstance
	 */
	public static DataInstance getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataInstance();
		}
		return INSTANCE;
	}
	
	/**
	 * init data for the test
	 */
	public void initInstance() {
		
		INSTANCE = new DataInstance();
		
		// create 4 users client instance,
		// each user have 2 accounts, each account one transaction
		for (int i = 1; i < 5; i++) {
			final UserDTO user = new UserDTO();
			user.setAccounts(new ArrayList<AccountDTO>());
			user.setReference("U0000" + i);
			Double totalAmount = 0D;
			for (int j = 1; j < 3; j++) {
				final AccountDTO account = new AccountDTO();
				account.setReference("A0000" + i + j);
				account.setReferenceUser(user.getReference());
				account.setIban("FR00000000000000" + j);
				account.setCreationDate(new Date());

				List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
				TransactionDTO transaction = new TransactionDTO();
				transaction.setReference("T0000" + i + j);
				transaction.setReferenceUser("U0000" + i);
				transaction.setReferenceAccount("A0000" + j);
				transaction.setAmount(10D);
				transactions.add(transaction);
				account.setTransactions(transactions);
				
				totalAmount = totalAmount + transaction.getAmount();
				account.setAmount(totalAmount);
				
				user.getAccounts().add(account);
			}
			
			user.setTotalAmount(totalAmount);
			map.put(user.getReference(), user);
		}
	}
	
	public final boolean hasProperty(final String key) {
		return map.containsKey(key);
	}
	
	public Map<String, UserDTO> getMap(){
		return map;
	}
	
}
