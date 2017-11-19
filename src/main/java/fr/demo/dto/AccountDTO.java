package fr.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.demo.dto.core.BaseDTO;

public class AccountDTO extends BaseDTO{
	
	private String referenceUser;
	private String iban;
	private Double amount;
	private Date lastUpdate;
	
	private List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();

	public String getReferenceUser() {
		return referenceUser;
	}

	public void setReferenceUser(String referenceUser) {
		this.referenceUser = referenceUser;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	

}
