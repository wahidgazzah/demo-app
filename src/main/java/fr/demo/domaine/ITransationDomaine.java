package fr.demo.domaine;

import fr.demo.dto.TransactionDTO;
import fr.demo.exception.CustomException;

/**
 * Domain interface for transactions.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public interface ITransationDomaine {

	public String doTransaction(TransactionDTO transaction) throws CustomException;
}
