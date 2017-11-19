package fr.demo.service;

import fr.demo.dto.TransactionDTO;
import fr.demo.exception.CustomException;

public interface ITransationService {

	public String doTransaction(TransactionDTO transaction) throws CustomException;
}
