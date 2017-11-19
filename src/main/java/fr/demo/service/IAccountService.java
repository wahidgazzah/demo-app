package fr.demo.service;

import java.util.List;

import fr.demo.dto.AccountDTO;
import fr.demo.exception.CustomException;

/**
 * Service interface for accounts.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public interface IAccountService {

	public String create(AccountDTO dto) throws CustomException;

	public String delete(String reference) throws CustomException;

	public String update(AccountDTO dto) throws CustomException;

	public AccountDTO get(String reference) throws CustomException;

	public List<AccountDTO> getAll() throws CustomException;

	public List<AccountDTO> getAllByUser(String referenceUser) throws CustomException;
}
