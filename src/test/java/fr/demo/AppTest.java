package fr.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.demo.dto.AccountDTO;
import fr.demo.dto.TransactionDTO;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;
import fr.demo.exception.IExceptionMessage;
import fr.demo.service.IAccountService;
import fr.demo.service.ITransationService;
import fr.demo.service.IUserService;
import fr.demo.service.impl.AccountServiceImpl;
import fr.demo.service.impl.TransationServiceImpl;
import fr.demo.service.impl.UserServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {
	
	private IUserService userService = new UserServiceImpl();
	private IAccountService acountService = new AccountServiceImpl();
	private ITransationService transactionService = new TransationServiceImpl();
	
	private final static String REF_USER = "U00002";
	private final static String REF_USER_TO_DELETE = "U00003";
	private final static String REF_ACCOUNT = "A000077";
	private final static String REF_ACCOUNT_22 = "A000022";
	private final static String REF_TRANSACTION = "T00023";
	private final static String NEW_FIRST_NAME = "DJO";
	private final static Double INIT_TRANSACTION_AMOUNT = 10D;

	@Test
	public void test_1_create_users_profiles() throws CustomException {
		
		// create 4 users client instance,
		// each user have 2 accounts, each account one transaction
		
		for (int i = 1; i < 5; i++) {
			final UserDTO user = new UserDTO();
			user.setAccounts(new ArrayList<AccountDTO>());
			user.setReference("U0000" + i);

			for (int j = 1; j < 3; j++) {
				final AccountDTO account = new AccountDTO();
				account.setReference("A0000" + i + j);
				account.setReferenceUser(user.getReference());
				account.setIban("FR00000000000000"+j);
				account.setCreationDate(new Date());
				
				List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
				TransactionDTO transaction = new TransactionDTO();
				transaction.setReference("T0000"+i+j);
				transaction.setReferenceUser("U0000" + i);
				transaction.setReferenceAccount("A0000" + j);
				transaction.setAmount(INIT_TRANSACTION_AMOUNT);
				transactions.add(transaction);
				account.setTransactions(transactions);
				
				user.getAccounts().add(account);
			}

			String refernceUsers = userService.create(user);
			Assert.assertNotNull(refernceUsers);
			assertThat(refernceUsers).isEqualTo("U0000"+i);
		}
	}

	@Test
	public void test_2_read_users() throws CustomException {

		final List<UserDTO> users = userService.getAll();
		
		Assert.assertNotNull(users);
		assertThat(users.size()).isEqualTo(4);
	}
	
	@Test
	public void test_3_read_user() throws CustomException {

		final UserDTO user = userService.get(REF_USER);

		Assert.assertNotNull(user);
	}

	@Test
	public void test_4_update_user_and_create_account() throws CustomException {

		UserDTO user = userService.get(REF_USER);
		Assert.assertNotNull(user);
		//assert that the user have 2 accounts now
		assertThat(user.getAccounts().size()).isEqualTo(2);
		//assert that the totalAmount is correct
		assertThat(user.getTotalAmount()).isEqualTo(INIT_TRANSACTION_AMOUNT * 2);
		
		//create a new account
		AccountDTO account = new AccountDTO();
		account.setReference("A000017");
		account.setReferenceUser(user.getReference());
		account.setIban("FR000000000000017");
		account.setCreationDate(new Date());
		
		List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
		TransactionDTO transaction = new TransactionDTO();
		transaction.setReference(REF_TRANSACTION);
		transaction.setReferenceUser(REF_USER);
		transaction.setReferenceAccount(REF_ACCOUNT);
		transaction.setAmount(INIT_TRANSACTION_AMOUNT);
		transactions.add(transaction);
		account.setTransactions(transactions);
		
		user.getAccounts().add(account);
		
		user.setFirstName(NEW_FIRST_NAME);
		String userReference = userService.update(user);
		
		assertThat(userReference).isEqualTo(REF_USER);
		
		// CALL the GET serive again
		user = userService.get(REF_USER);
		//assert that the firstName is updated		
		assertThat(user.getFirstName()).isEqualTo(NEW_FIRST_NAME);
		//assert that the user have 3 account now
		assertThat(user.getAccounts().size()).isEqualTo(3);
		//assert that the totalAmount is updated
		assertThat(user.getTotalAmount()).isEqualTo(INIT_TRANSACTION_AMOUNT * 3);
		
	}
	
	@Test
	public void test_5_delete_user_and_throw_exception() throws CustomException {

		UserDTO user = userService.get(REF_USER_TO_DELETE);
		
		//make sure that this user exist
		Assert.assertNotNull(user);
		
		String deleteResult = userService.delete(REF_USER_TO_DELETE);
		
		//test the success delete action
		assertThat(deleteResult).isEqualTo(IConstant.SUCCESS_ACTION);
		
		//make sure that the user is NOT exist, so throw an exception
		try {
			userService.get(REF_USER_TO_DELETE);
		} catch (CustomException e) {
			assertThat(e.getMessage()).isEqualTo(IExceptionMessage.USER_NOT_EXIST);
		}
		
	}
	
	@Test
	public void test_6_create_read_update_delete_account_and_throw_exception() throws CustomException{
		
		final AccountDTO accountToCreate = new AccountDTO();
		accountToCreate.setReference(REF_ACCOUNT);
		accountToCreate.setReferenceUser(REF_USER);
		accountToCreate.setIban("FR000000000000018");
		accountToCreate.setAmount(INIT_TRANSACTION_AMOUNT);

		//Create account service test
		acountService.create(accountToCreate);
		//Get account service test
		AccountDTO account = acountService.get(REF_ACCOUNT);
		assertThat(account).isEqualTo(accountToCreate);
		String initDescription = account.getDescription();
		account.setDescription("Desc");
		//Update account service test
		acountService.update(account);
		//assert that the description is updated
		assertThat(account.getDescription()).isNotEqualTo(initDescription);
		
		//Delete account service test
		String deleteResult = acountService.delete(REF_ACCOUNT);
		
		//test success delete action
		assertThat(deleteResult).isEqualTo(IConstant.SUCCESS_ACTION);
		
		//Throw an exception when the account NOT exist
		try {
			acountService.get(REF_ACCOUNT);
		} catch (CustomException e) {
			assertThat(e.getMessage()).isEqualTo(IExceptionMessage.ACCOUNT_NOT_EXIST);
		}
		
	}
	
	@Test
	public void test_7_create_transaction_and_throw_exception() throws CustomException{
		
		Double transactionAmount = 150D;
		Double totalUserAmountBeforeTransaction = userService.get(REF_USER).getTotalAmount();
		
		TransactionDTO transaction = new TransactionDTO();
		transaction.setReference(REF_TRANSACTION);
		transaction.setReferenceUser(REF_USER);
		transaction.setReferenceAccount(REF_ACCOUNT_22);
		transaction.setAmount(transactionAmount);
		
		String status = transactionService.doTransaction(transaction);
		//test the success action
		assertThat(status).isEqualTo(IConstant.SUCCESS_ACTION);
		
		Double totalUserAmountAfterTransaction = userService.get(REF_USER).getTotalAmount();
		
		assertThat(totalUserAmountAfterTransaction).isEqualTo(
				transactionAmount + totalUserAmountBeforeTransaction);
		
		//Throw an exception when the account NOT exist
		//This Account is deleted
		transaction.setReferenceAccount(REF_ACCOUNT);
		try {
			transactionService.doTransaction(transaction);
		} catch (CustomException e) {
			assertThat(e.getMessage()).isEqualTo(IExceptionMessage.ACCOUNT_NOT_EXIST);
		}
		
		
	}
}
