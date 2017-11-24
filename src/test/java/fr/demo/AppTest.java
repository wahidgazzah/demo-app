package fr.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.demo.data.accessdata.DataInstance;
import fr.demo.data.accessdata.IDataInstance;
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

public class AppTest {

	// init the services
	private IUserService userService = new UserServiceImpl();
	private IAccountService acountService = new AccountServiceImpl();
	private ITransationService transactionService = new TransationServiceImpl();
	private IDataInstance data = DataInstance.getInstance();
	
	// common data using during the tests
	private final static String REF_USER = "U00002";
	private final static String REF_USER_TO_DELETE = "U00003";
	private final static String REF_ACCOUNT = "A000077";
	private final static String REF_ACCOUNT_22 = "A000022";
	private final static String REF_TRANSACTION = "T00023";
	private final static String NEW_FIRST_NAME = "DJO";
	private final static Double INIT_TRANSACTION_AMOUNT = 10D;
	
	@Before
	public void init() throws Exception {

		// init data for each test function
		// create 4 users client instance [U00001, U00002, U00003, U00004]
		// each user have 2 accounts and each account we create an init transaction
		data.initInstance();
	}
	
	/**
	 * Test create user
	 * @throws CustomException
	 */
	@Test
	public void create_user_profile() throws CustomException {

			final UserDTO user = new UserDTO();
			user.setAccounts(new ArrayList<AccountDTO>());
			user.setReference("U000099");

			// create 3 account for this user
			for (int j = 1; j < 3; j++) {
				final AccountDTO account = new AccountDTO();
				account.setReference("A000099"+j);
				account.setReferenceUser(user.getReference());
				account.setIban("FR00000000000099" + j);
				account.setCreationDate(new Date());

				// create 1 transaction for each account
				List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
				TransactionDTO transaction = new TransactionDTO();
				transaction.setReference("T0000" + j);
				transaction.setReferenceUser("U000099");
				transaction.setReferenceAccount("A0000" + j);
				transaction.setAmount(INIT_TRANSACTION_AMOUNT);
				transactions.add(transaction);
				account.setTransactions(transactions);

				user.getAccounts().add(account);
			}

			String refernceUsers = userService.create(user);
			Assert.assertNotNull(refernceUsers);
			Assert.assertEquals(refernceUsers, "U000099");
	}

	/**
	 * Test get all users
	 * @throws CustomException
	 */
	@Test
	public void read_users() throws CustomException {
		
		final List<UserDTO> users = userService.getAll();

		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 4);
	}

	/**
	 * Test get one user by reference
	 * @throws CustomException
	 */
	@Test
	public void read_user() throws CustomException {
		
		final UserDTO user = userService.get(REF_USER);

		Assert.assertNotNull(user);
	}

	/**
	 * Test update the firstName of a user
	 * @throws CustomException
	 */
	@Test
	public void update_user_simple_data() throws CustomException {
		
		UserDTO user = userService.get(REF_USER);
		Assert.assertNotNull(user);
		
		user.setFirstName(NEW_FIRST_NAME);
		String userReferenceResult = userService.update(user);

		//assertThat(userReferenceResult).isEqualTo(REF_USER);
		Assert.assertEquals(userReferenceResult, REF_USER);
	}

	/**
	 * Test add an account for a user and update it
	 * @throws CustomException
	 */
	@Test
	public void update_user_with_new_account() throws CustomException {
		
		UserDTO user = userService.get(REF_USER);
		// create a new account
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
		userService.update(user);

		// CALL the GET serive again
		user = userService.get(REF_USER);
		// assert that the firstName is updated
		Assert.assertEquals(user.getFirstName(), NEW_FIRST_NAME);
		
		// assert that the user have 3 account now
		Assert.assertEquals(user.getAccounts().size(), 3);
		
		// assert that the totalAmount is updated
		Double totalAmount = INIT_TRANSACTION_AMOUNT * 3;
		Assert.assertEquals(user.getTotalAmount(), totalAmount);
	}

	/**
	 * Test remove a user
	 * @throws CustomException
	 */
	@Test
	public void delete_user() throws CustomException {
		
		UserDTO user = userService.get(REF_USER_TO_DELETE);

		// make sure that this user exist
		Assert.assertNotNull(user);

		String deleteResult = userService.delete(REF_USER_TO_DELETE);

		// test the success delete action
		Assert.assertEquals(deleteResult, IConstant.SUCCESS_ACTION);
	}

	/**
	 * Test the exception when we try to get a user using a bad reference 
	 * @throws CustomException
	 */
	@Test
	public void should_throw_exception_when_get_user_using_reference_not_exist() throws CustomException {
		
		// make sure that the user is doesn't exist, so throw an exception
		try {
			userService.get(REF_USER_TO_DELETE);
		} catch (CustomException e) {
			Assert.assertEquals(e.getMessage(), IExceptionMessage.USER_NOT_EXIST);
		}

	}

	/**
	 * Test create account
	 * @throws CustomException
	 */
	@Test
	public void create_account() throws CustomException {
		
		final AccountDTO accountToCreate = new AccountDTO();
		accountToCreate.setReference(REF_ACCOUNT);
		accountToCreate.setReferenceUser(REF_USER);
		accountToCreate.setIban("FR000000000000018");
		accountToCreate.setAmount(INIT_TRANSACTION_AMOUNT);

		// Create account service test
		String accountReferenceResult = acountService.create(accountToCreate);

		Assert.assertEquals(REF_ACCOUNT, accountReferenceResult);
		
	}

	/**
	 * Test retrive an account
	 * @throws CustomException
	 */
	@Test
	public void get_account() throws CustomException {
		
		// Get account service test
		AccountDTO account = acountService.get(REF_ACCOUNT_22);
		Assert.assertNotNull(account);
	}
	

	/**
	 * Test update account
	 * @throws CustomException
	 */
	@Test
	public void update_account() throws CustomException {
		
		// Get account service test
		AccountDTO account = acountService.get(REF_ACCOUNT_22);
		account.setDescription("Desc");
		// Update account service test
		acountService.update(account);
		// assert that the description is updated
		Assert.assertEquals(account.getDescription(), "Desc");
	}

	/**
	 * Test delete account 
	 * @throws CustomException
	 */
	@Test
	public void delete_account() throws CustomException {
		
		// Delete account service test
		String deleteResult = acountService.delete(REF_ACCOUNT_22);

		// test success delete action
		Assert.assertEquals(deleteResult, IConstant.SUCCESS_ACTION);
	}

	/**
	 * Test the exception when we try to get an account using a bad reference 
	 * @throws CustomException
	 */
	@Test
	public void should_throw_exception_when_get_account_using_reference_not_exist() throws CustomException {
		
		// Throw an exception when the account NOT exist
		try {
			acountService.get(REF_ACCOUNT);
		} catch (CustomException e) {
			Assert.assertEquals(e.getMessage(), IExceptionMessage.ACCOUNT_NOT_EXIST);
		}

	}

	/**
	 * Test create transaction
	 * @throws CustomException
	 */
	@Test
	public void create_transaction() throws CustomException {

		Double transactionAmount = 150D;
		Double totalUserAmountBeforeTransaction = userService.get(REF_USER).getTotalAmount();

		final TransactionDTO transaction = new TransactionDTO();
		transaction.setReference(REF_TRANSACTION);
		transaction.setReferenceUser(REF_USER);
		transaction.setReferenceAccount(REF_ACCOUNT_22);
		transaction.setAmount(transactionAmount);

		String status = transactionService.doTransaction(transaction);
		// test the success action
		Assert.assertEquals(status, IConstant.SUCCESS_ACTION);

		Double totalUserAmountAfterTransaction = userService.get(REF_USER).getTotalAmount();

		Double total =  transactionAmount + totalUserAmountBeforeTransaction;
		Assert.assertEquals(totalUserAmountAfterTransaction, total);
	}
	
	/**
	 * Test the exception when we try to create an account using a bad account reference 
	 * @throws CustomException
	 */
	@Test
	public void should_throw_exception_when_create_transaction_for_account_not_exist() throws CustomException {
		
		Double transactionAmount = 150D;

		final TransactionDTO transaction = new TransactionDTO();
		transaction.setReference(REF_TRANSACTION);
		transaction.setReferenceUser(REF_USER);
		transaction.setReferenceAccount(REF_ACCOUNT_22);
		transaction.setAmount(transactionAmount);

		// Throw an exception when the account NOT exist
		// This Account is deleted
		transaction.setReferenceAccount(REF_ACCOUNT);
		try {
			transactionService.doTransaction(transaction);
		} catch (CustomException e) {
			Assert.assertEquals(e.getMessage(), IExceptionMessage.ACCOUNT_NOT_EXIST);
		}
	}
}
