package fr.demo.domaine.impl;

import fr.demo.IConstant;
import fr.demo.data.accessdata.DataInstance;
import fr.demo.domaine.ITransationDomaine;
import fr.demo.domaine.IUserDomaine;
import fr.demo.dto.AccountDTO;
import fr.demo.dto.TransactionDTO;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;
import fr.demo.exception.IExceptionMessage;

/**
 * Implementation for {@link ITransationDomaine}.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class TransationDomaineImpl implements ITransationDomaine{
	
	private DataInstance accessData = DataInstance.getInstance();
	private IUserDomaine userDomain = new UserDomaineImpl();
	
	public String doTransaction(TransactionDTO transaction) throws CustomException{
	
		if (transaction != null) {
			
			if (accessData.hasProperty(transaction.getReferenceUser())) {
				
				UserDTO user = accessData.getMap().get(transaction.getReferenceUser());
				for(AccountDTO account : user.getAccounts()){
					if(account.getReference().equals(transaction.getReferenceAccount())){
						account.getTransactions().add(transaction);
						userDomain.update(user);
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
