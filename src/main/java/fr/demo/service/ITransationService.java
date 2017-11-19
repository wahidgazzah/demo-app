package fr.demo.service;

import fr.demo.dto.TransactionDTO;
import fr.demo.exception.CustomException;

/**
 * Service interface for transactions.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public interface ITransationService {

	public String doTransaction(TransactionDTO transaction) throws CustomException;
}
