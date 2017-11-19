package fr.demo.domaine.impl;

import java.util.List;

import fr.demo.data.accessdata.DataInstance;
import fr.demo.domaine.IAccountDomaine;
import fr.demo.dto.AccountDTO;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;

/**
 * Implementation for {@link IAccountDomaine}.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class AccountDomaineImpl implements IAccountDomaine{
	
	private DataInstance accessData = DataInstance.getInstance();
	
	public String create(AccountDTO dto) throws CustomException{
				
		return accessData.createAccount(dto);
	}

	public String delete(String reference) throws CustomException{
		
		String result = null;
		AccountDTO account = accessData.getAccount(reference);
		if(account != null){
			
			result = accessData.deleteAccount(reference);
			UserDTO user = accessData.getUser(account.getReferenceUser());
			accessData.updateUser(user);
		}
			
		return result;
	}

	public String update(AccountDTO dto) throws CustomException{
		
		return accessData.updateAccount(dto);
	}

	public AccountDTO get(String reference) throws CustomException{
		
		return accessData.getAccount(reference);
	}

	public List<AccountDTO> getAll() throws CustomException{
		
		return accessData.getAccounts();
	}
	
	public List<AccountDTO> getAllByUser(String referenceUser) throws CustomException{
		
		return accessData.getAccountsByUser(referenceUser);
	}

}
