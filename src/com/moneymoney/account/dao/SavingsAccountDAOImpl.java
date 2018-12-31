package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class SavingsAccountDAOImpl implements SavingsAccountDAO {

	public SavingsAccount createNewAccount(SavingsAccount account)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO account_details VALUES(?,?,?,?,?,?)");
		preparedStatement
				.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount()
				.getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount()
				.getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	public List<SavingsAccount> getAllSavingsAccount()
			throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM account_details");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE account_details SET account_bal=? where account_number=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account_details where account_number=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if (resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");
	}

	public SavingsAccount updateAccount(SavingsAccount account)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE account_details SET account_number = ?, account_hn = ?, account_bal = ?,salary = ?,odlimit = ?,type = ? WHERE account_number = ?");
		preparedStatement
				.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount()
				.getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount()
				.getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		preparedStatement
				.setInt(7, account.getBankAccount().getAccountNumber());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("DELETE FROM account_details WHERE account_number = ?");
		SavingsAccount deletedAccountDetails = getAccountById(accountNumber);
		preparedStatement.setInt(1, accountNumber);
		preparedStatement.execute();
		DBUtil.commit();
		return deletedAccountDetails;
	}

	@Override
	public void commit() throws SQLException {
		DBUtil.commit();
	}

	@Override
	public SavingsAccount getAccountByName(String accountHolderName)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account_details where account_hn=?");
		preparedStatement.setString(1, accountHolderName);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;

		if (resultSet.next()) {
			int accountNumber = resultSet.getInt(1);
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountHolderName + " does not exist.");

	}

	@Override
	public List<SavingsAccount> getAccountsBetweenMinimumAndMaximumValues(
			double minimum, double maximum) throws ClassNotFoundException,
			SQLException {
		List<SavingsAccount> savingsAccountsList = new ArrayList<SavingsAccount>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account_details WHERE account_bal BETWEEN ? AND ?");
		preparedStatement.setDouble(1, minimum);
		preparedStatement.setDouble(2, maximum);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccounts = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccountsList.add(savingsAccounts);
		}
		return savingsAccountsList;
	}

	@Override
	public SavingsAccount getCurrentBalance(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {

		SavingsAccount savingsAccount = getAccountById(accountNumber);

		return savingsAccount;

	}

}
