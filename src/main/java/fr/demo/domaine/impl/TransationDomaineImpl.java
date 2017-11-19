package fr.demo.domaine.impl;

import fr.demo.data.accessdata.DataInstance;
import fr.demo.domaine.ITransationDomaine;
import fr.demo.dto.TransactionDTO;
import fr.demo.exception.CustomException;

/**
 * Implementation for {@link ITransationDomaine}.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class TransationDomaineImpl implements ITransationDomaine{
	
	private DataInstance accessData = DataInstance.getInstance();
	
	public String doTransaction(TransactionDTO transaction) throws CustomException{
	
		return accessData.doTransaction(transaction);
	}

}
