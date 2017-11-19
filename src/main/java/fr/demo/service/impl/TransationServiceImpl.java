package fr.demo.service.impl;

import fr.demo.domaine.ITransationDomaine;
import fr.demo.domaine.impl.TransationDomaineImpl;
import fr.demo.dto.TransactionDTO;
import fr.demo.exception.CustomException;
import fr.demo.service.ITransationService;

/**
 * Service implementation for {@link ITransationService	}.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class TransationServiceImpl implements ITransationService{

	private ITransationDomaine transationDomaine = new TransationDomaineImpl();

	
	public String doTransaction(TransactionDTO transaction) throws CustomException{
		
		return transationDomaine.doTransaction(transaction);
	}
}
