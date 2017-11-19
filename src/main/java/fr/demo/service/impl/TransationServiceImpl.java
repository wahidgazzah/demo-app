package fr.demo.service.impl;

import fr.demo.domaine.ITransationDomaine;
import fr.demo.domaine.impl.TransationDomaineImpl;
import fr.demo.dto.TransactionDTO;
import fr.demo.exception.CustomException;
import fr.demo.service.ITransationService;

public class TransationServiceImpl implements ITransationService{

	private ITransationDomaine transationDomaine = new TransationDomaineImpl();

	
	public String doTransaction(TransactionDTO transaction) throws CustomException{
		
		return transationDomaine.doTransaction(transaction);
	}
}
