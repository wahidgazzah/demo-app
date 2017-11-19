package fr.demo.dto;

import java.util.Date;

import fr.demo.dto.core.BaseDTO;

public class TransactionDTO extends BaseDTO{
	
	private String referenceUser;
	private String referenceAccount;
	private Double amount;
	private Date transactionDate;
	
	public String getReferenceAccount() {
		return referenceAccount;
	}
	public void setReferenceAccount(String referenceAccount) {
		this.referenceAccount = referenceAccount;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getReferenceUser() {
		return referenceUser;
	}
	public void setReferenceUser(String referenceUser) {
		this.referenceUser = referenceUser;
	}
	
	

}
