package fr.demo.service.impl;

import java.util.List;

import fr.demo.domaine.IAccountDomaine;
import fr.demo.domaine.impl.AccountDomaineImpl;
import fr.demo.dto.AccountDTO;
import fr.demo.exception.CustomException;
import fr.demo.service.IAccountService;

/**
 * Service implementation for {@link IAccountService}.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class AccountServiceImpl implements IAccountService {

	private IAccountDomaine accountDomaine = new AccountDomaineImpl();

	
	public String create(AccountDTO dto) throws CustomException{

		return accountDomaine.create(dto);
	}

	public String delete(String reference) throws CustomException{

		return accountDomaine.delete(reference);
	}

	public String update(AccountDTO dto) throws CustomException{

		return accountDomaine.update(dto);
	}

	public AccountDTO get(String reference) throws CustomException{

		return accountDomaine.get(reference);
	}
	
	public List<AccountDTO> getAll() throws CustomException{

		return accountDomaine.getAll();
	}
	
	public List<AccountDTO> getAllByUser(String referenceUser) throws CustomException{

		return accountDomaine.getAllByUser(referenceUser);
	}

}
