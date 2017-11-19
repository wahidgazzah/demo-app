package fr.demo.data.accessdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.demo.IConstant;
import fr.demo.dto.AccountDTO;
import fr.demo.dto.TransactionDTO;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;
import fr.demo.exception.IExceptionMessage;

public class DataInstance implements IDataInstance{
	
	private static Map<String, UserDTO> map = new HashMap<String, UserDTO>();

	private static DataInstance INSTANCE = null;

	public static DataInstance getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataInstance();
		}
		return INSTANCE;
	}
	
	public final boolean hasProperty(final String key) {

		return map.containsKey(key);
	}
	
	public String createUser(UserDTO user) throws CustomException {

		if (user == null) {

			throw new CustomException(IExceptionMessage.NULL_DATA);
		} else if (user.getReference() == null) {

			throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
		}
		
		if(!hasProperty(user.getReference())){
			
			Double totalAmount = 0D;
			for (AccountDTO account : user.getAccounts()) {
				
				Double totalTransactionsAmount = 0D;
				for(TransactionDTO transaction : account.getTransactions()){
					
					if(transaction.getAmount() != null){
						totalTransactionsAmount = totalTransactionsAmount + transaction.getAmount();
					}
				}
				
				account.setAmount(totalTransactionsAmount);
				totalAmount = totalAmount + totalTransactionsAmount;
			}
			
			user.setTotalAmount(totalAmount);
			user.setCreationDate(new Date());
			user.setLastUpdate(new Date());
			
			map.put(user.getReference(), user);
			
			return user.getReference();
		}else{
			
			throw new CustomException(IExceptionMessage.DUPLICATED_REFERENCE);
		}		
	}
	
	public String deleteUser(String reference) throws CustomException {

		if(reference != null){
			if (hasProperty(reference)) {
				map.remove(reference);

				return IConstant.SUCCESS_ACTION;
			} else {
				throw new CustomException(IExceptionMessage.USER_NOT_EXIST);
			}
			
		}else {
			throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
		}	
	}
	
	public String updateUser(UserDTO user) throws CustomException {

		if (hasProperty(user.getReference())) {

			Double totalAmount = 0D;
			for (AccountDTO account : user.getAccounts()) {
				
				Double totalTransactionsAmount = 0D;
				for(TransactionDTO transaction : account.getTransactions()){
					
					if(transaction.getAmount() != null){
						totalTransactionsAmount = totalTransactionsAmount + transaction.getAmount();
					}
				}
				
				account.setAmount(totalTransactionsAmount);
				totalAmount = totalAmount + totalTransactionsAmount;
			}

			user.setLastUpdate(new Date());
			user.setTotalAmount(totalAmount);
			map.put(user.getReference(), user);
			return user.getReference();
		} else {
			throw new CustomException(IExceptionMessage.USER_NOT_EXIST);
		}
	}
	
	public UserDTO getUser(String reference) throws CustomException {

		if (hasProperty(reference)) {
			
			return map.get(reference);
		}else{
			
			throw new CustomException(IExceptionMessage.USER_NOT_EXIST);
		}
	}
	
	public List<UserDTO> getUsers() {
		
		List<UserDTO> result = new ArrayList<UserDTO>();
		
		for (Entry<String, UserDTO> pair : map.entrySet()){
            
			result.add(pair.getValue());
        }
		
		return result;
	}
	
	public String createAccount(AccountDTO account) throws CustomException {

		if (account == null) {

			throw new CustomException(IExceptionMessage.NULL_DATA);
		} else if (account.getReference() == null || account.getReferenceUser() == null) {

			throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
		}

		UserDTO user = getUser(account.getReferenceUser());

		account.setCreationDate(new Date());
		account.setLastUpdate(new Date());
		user.getAccounts().add(account);
		updateUser(user);
		return account.getReference();
	}
	
	public String deleteAccount(String reference) throws CustomException {

		String result = null;
		if (reference != null) {
			AccountDTO account = getAccount(reference);
			if (account.getReferenceUser() != null) {

				UserDTO user = getUser(account.getReferenceUser());
				user.getAccounts().remove(account);
				updateUser(user);
				result = IConstant.SUCCESS_ACTION;
			}

		} else {
			throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
		}

		return result;
	}
	
	public String updateAccount(AccountDTO account) throws CustomException {

		String result = null;
		if (account != null) {
			if (account.getReference() == null || account.getReferenceUser() == null) {

				throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
			}

			UserDTO user = getUser(account.getReferenceUser());
			user.getAccounts().remove(account);
			account.setLastUpdate(new Date());
			user.getAccounts().add(account);
			updateUser(user);

			result = account.getReference();
		} else {
			throw new CustomException(IExceptionMessage.NULL_DATA);
		}
		return result;
	}

	public AccountDTO getAccount(String reference) throws CustomException {

		if (reference != null) {

			List<AccountDTO> accounts = getAccounts();

			for (AccountDTO account : accounts) {

				if (reference.equals(account.getReference())) {

					return account;
				}
			}
		} else {
			throw new CustomException(IExceptionMessage.NULL_DATA);
		}

		throw new CustomException(IExceptionMessage.ACCOUNT_NOT_EXIST);
	}

	public List<AccountDTO> getAccountsByUser(String referenceUser) throws CustomException {

		List<AccountDTO> result = new ArrayList<AccountDTO>();
		if (referenceUser != null) {

			result = getUser(referenceUser).getAccounts();
		} else {
			throw new CustomException(IExceptionMessage.NULL_DATA);
		}

		return result;
	}

	public List<AccountDTO> getAccounts() throws CustomException {

		List<AccountDTO> result = new ArrayList<AccountDTO>();
		for (UserDTO user : getUsers()) {

			result.addAll(user.getAccounts());
		}

		return result;
	}

	public String doTransaction(TransactionDTO transaction) throws CustomException {
		
		if (transaction != null) {
			
			if (hasProperty(transaction.getReferenceUser())) {
				
				UserDTO user = map.get(transaction.getReferenceUser());
				for(AccountDTO account : user.getAccounts()){
					if(account.getReference().equals(transaction.getReferenceAccount())){
						account.getTransactions().add(transaction);
						updateUser(user);
						return IConstant.SUCCESS_ACTION;
					}
				}
				
				throw new CustomException(IExceptionMessage.ACCOUNT_NOT_EXIST); 
				
			} else {
				throw new CustomException(IExceptionMessage.USER_NOT_EXIST);
			}
			
		} else {
			throw new CustomException(IExceptionMessage.NULL_DATA);
		}
	}	
}
