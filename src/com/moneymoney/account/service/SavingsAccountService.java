package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) throws ClassNotFoundException, SQLException;

	SavingsAccount updateAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;

	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) throws ClassNotFoundException, SQLException;
	void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;
	void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;

	SavingsAccount getAccountByName(String accountHolderName) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	List<SavingsAccount> getAccountsBetweenMinimumAndMaximumValues(
			double minimum, double maximum) throws ClassNotFoundException, SQLException;

	SavingsAccount getCurrentBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
}











