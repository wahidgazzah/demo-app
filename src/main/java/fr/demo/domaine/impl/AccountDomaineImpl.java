package fr.demo.domaine.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.demo.IConstant;
import fr.demo.domaine.IAccountDomaine;
import fr.demo.domaine.IUserDomaine;
import fr.demo.dto.AccountDTO;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;
import fr.demo.exception.IExceptionMessage;

/**
 * Implementation for {@link IAccountDomaine}.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class AccountDomaineImpl implements IAccountDomaine{
	
	private IUserDomaine userDomain = new UserDomaineImpl();
	
	public String create(AccountDTO account) throws CustomException{
				
		if (account == null) {

			throw new CustomException(IExceptionMessage.NULL_DATA);
		} else if (account.getReference() == null || account.getReferenceUser() == null) {

			throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
		}

		UserDTO user = userDomain.get(account.getReferenceUser());

		account.setCreationDate(new Date());
		account.setLastUpdate(new Date());
		user.getAccounts().add(account);
		userDomain.update(user);
		return account.getReference();
	}

	public String delete(String reference) throws CustomException{
		
		String result = null;
		AccountDTO account = get(reference);
		if(account != null){
			if (reference != null) {
				AccountDTO acc = get(reference);
				if (acc.getReferenceUser() != null) {

					UserDTO user = userDomain.get(acc.getReferenceUser());
					user.getAccounts().remove(acc);
					userDomain.update(user);
					result = IConstant.SUCCESS_ACTION;
				}

			} else {
				throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
			}
			
			UserDTO user = userDomain.get(account.getReferenceUser());
			userDomain.update(user);
		}
			
		return result;
	}

	public String update(AccountDTO account) throws CustomException{
		
		String result = null;
		if (account != null) {
			if (account.getReference() == null || account.getReferenceUser() == null) {

				throw new CustomException(IExceptionMessage.REQUIRED_REFERENCE);
			}

			UserDTO user = userDomain.get(account.getReferenceUser());
			user.getAccounts().remove(account);
			account.setLastUpdate(new Date());
			user.getAccounts().add(account);
			userDomain.update(user);

			result = account.getReference();
		} else {
			throw new CustomException(IExceptionMessage.NULL_DATA);
		}
		return result;
	}

	public AccountDTO get(String reference) throws CustomException{
		
		if (reference != null) {

			List<AccountDTO> accounts = getAll();
			
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

	public List<AccountDTO> getAll() throws CustomException{
		
		List<AccountDTO> result = new ArrayList<AccountDTO>();
		for (UserDTO user : userDomain.getAll()) {
			
			result.addAll(user.getAccounts());
		}

		return result;
	}
	
	public List<AccountDTO> getAllByUser(String referenceUser) throws CustomException{
		
		List<AccountDTO> result = new ArrayList<AccountDTO>();
		if (referenceUser != null) {

			result = userDomain.get(referenceUser).getAccounts();
		} else {
			throw new CustomException(IExceptionMessage.NULL_DATA);
		}

		return result;
	}

}
