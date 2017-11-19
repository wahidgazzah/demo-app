package fr.demo.data.accessdata;

import java.util.List;

import fr.demo.dto.AccountDTO;
import fr.demo.dto.TransactionDTO;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;

/**
 * Data Instance interface.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public interface IDataInstance {

	public String createUser(UserDTO dto) throws CustomException;

	public String deleteUser(String reference) throws CustomException;

	public String updateUser(UserDTO dto) throws CustomException;

	public UserDTO getUser(String reference) throws CustomException;

	public List<UserDTO> getUsers() throws CustomException;

	public String createAccount(AccountDTO dto) throws CustomException;

	public String deleteAccount(String reference) throws CustomException;

	public String updateAccount(AccountDTO dto) throws CustomException;

	public AccountDTO getAccount(String reference) throws CustomException;

	public List<AccountDTO> getAccounts() throws CustomException;
	
	public List<AccountDTO> getAccountsByUser(String referenceUser) throws CustomException;
	
	public String doTransaction(TransactionDTO transaction) throws CustomException;
}
