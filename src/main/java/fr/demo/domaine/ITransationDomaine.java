package fr.demo.domaine;

import fr.demo.dto.TransactionDTO;
import fr.demo.exception.CustomException;

public interface ITransationDomaine {

	public String doTransaction(TransactionDTO transaction) throws CustomException;
}
