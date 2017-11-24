package fr.demo.domaine.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import fr.demo.IConstant;
import fr.demo.data.accessdata.DataInstance;
import fr.demo.domaine.IUserDomaine;
import fr.demo.dto.AccountDTO;
import fr.demo.dto.TransactionDTO;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;
import fr.demo.exception.IExceptionMessage;

/**
 * Implementation for {@link IUserDomaineF}.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class UserDomaineImpl implements IUserDomaine{
	
	private DataInstance accessData = DataInstance.getInstance();
	
	public String create(UserDTO user) throws CustomException{

		if (user == null) {

			throw new CustomException(IExceptionMessage.NULL_DATA);
		} else if (user.getReference() == null) {

			throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
		}
		
		if(!accessData.hasProperty(user.getReference())){
			
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
			
			accessData.getMap().put(user.getReference(), user);
			
			return user.getReference();
		}else{
			
			throw new CustomException(IExceptionMessage.DUPLICATED_REFERENCE);
		}	
	}

	public String delete(String reference) throws CustomException{
		
		if(reference != null){
			if (accessData.hasProperty(reference)) {
				accessData.getMap().remove(reference);

				return IConstant.SUCCESS_ACTION;
			} else {
				throw new CustomException(IExceptionMessage.USER_NOT_EXIST);
			}
			
		}else {
			throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
		}	
	}

	public String update(UserDTO user) throws CustomException{
		
		if (accessData.hasProperty(user.getReference())) {

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
			accessData.getMap().put(user.getReference(), user);
			return user.getReference();
		} else {
			throw new CustomException(IExceptionMessage.USER_NOT_EXIST);
		}
	}

	public UserDTO get(String reference) throws CustomException{
		
		if (accessData.hasProperty(reference)) {
			
			return accessData.getMap().get(reference);
		}else{
			
			throw new CustomException(IExceptionMessage.USER_NOT_EXIST);
		}
	}

	public List<UserDTO> getAll() throws CustomException{
		
		List<UserDTO> result = new ArrayList<UserDTO>();
		
		for (Entry<String, UserDTO> pair : accessData.getMap().entrySet()){
            
			result.add(pair.getValue());
        }
		
		return result;
	}
}
