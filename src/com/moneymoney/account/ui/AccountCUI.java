package com.moneymoney.account.ui;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.SortByAccountBalance;
import com.moneymoney.account.SortByAccountBalanceInDescending;
import com.moneymoney.account.SortByAccountHolderName;
import com.moneymoney.account.SortByAccountHolderNameInDescending;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	private static SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();

	public static void start() {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private static void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			updateAccount();
			break;

		case 3:
			closeAccount();
			break;
		case 4:
			searchAccount();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			checkCurrentBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			sortMenu();
			break;

		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

	private static void checkCurrentBalance() {
		System.out.println("Enter the account number to check balance: ");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingsAccount;
		try {
			savingsAccount = savingsAccountService
					.getCurrentBalance(accountNumber);
			String accountHolderName = savingsAccount.getBankAccount()
					.getAccountHolderName();
			double currentBalance = savingsAccount.getBankAccount()
					.getAccountBalance();
			System.out.println("Current Balance of " + accountHolderName
					+ " is " + currentBalance);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void searchAccount() {
		do {
			System.out.println("1. Search by id.");
			System.out.println("2. Search by name. ");
			System.out.println("3. search by Account balance.");
			System.out.println("4. Exit.");
			System.out.println();
			System.out.println("Make Your choioce: ");

			int choice = scanner.nextInt();
			searchSpecifiedAccount(choice);
		} while (true);

	}

	private static void searchSpecifiedAccount(int choice) {
		switch (choice) {
		case 1:
			System.out.println("Enter the account Number of the customer: ");
			int accountNumber = scanner.nextInt();
			try {
				SavingsAccount getElementById = savingsAccountService
						.getAccountById(accountNumber);
				System.out.println("Your Account is: "
						+ getElementById.toString());
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Enter the account holder name to search: ");
			String accountHolderName = scanner.next();
			try {
				SavingsAccount getAccountByName = savingsAccountService
						.getAccountByName(accountHolderName);
				System.out.println("Your account is : "
						+ getAccountByName.toString());
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out
					.println("Enter the minimum and maximum balance to search");
			double minimum = scanner.nextDouble();
			double maximum = scanner.nextDouble();
			List<SavingsAccount> savingsAccounts;
			try {
				savingsAccounts = savingsAccountService
						.getAccountsBetweenMinimumAndMaximumValues(minimum,
								maximum);
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		case 4:
			start();
			break;

		default:
			break;
		}

	}

	private static void updateAccount() {
		System.out.println("Enter the account number to update: ");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingAccount = null;
		try {
			savingAccount = savingsAccountService.getAccountById(accountNumber);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			e.printStackTrace();
		}

		do {
			System.out.println("Enter which field to update: ");
			System.out.println("1. Account holder name.");
			System.out.println("2. Salary Field.");
			System.out.println("3. Exit.");
			System.out.println();
			System.out.println("Make Your choioce: ");

			int choice = scanner.nextInt();
			updateField(savingAccount, choice);

		} while (true);

	}

	private static void updateField(SavingsAccount savingAccount, int choice) {

		switch (choice) {
		case 1:

			System.out.println("Enter the name to update: ");
			String updatedName = scanner.next();
			savingAccount.getBankAccount().setAccountHolderName(updatedName);
			try {
				savingAccount = savingsAccountService
						.updateAccount(savingAccount);
				System.out.println(savingAccount.toString());
				break;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		case 2:

			System.out.println("Change the salary field(true/false)");
			boolean updatedSalaryField = scanner.nextBoolean();
			savingAccount.setSalary(updatedSalaryField);
			try {
				savingAccount = savingsAccountService
						.updateAccount(savingAccount);
				System.out.println(savingAccount.toString());
				break;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		case 3:
			start();
			break;
		default:
			System.out.println("Invalid Choice.");
		}

	}

	private static void closeAccount() {
		System.out.println("Enter the account number to delete: ");
		int accountNumber = scanner.nextInt();
		SavingsAccount deletedSavingAccount = null;
		try {
			deletedSavingAccount = savingsAccountService
					.deleteAccount(accountNumber);
			System.out.println(deletedSavingAccount.getBankAccount()
					.getAccountHolderName() + " deleted.");
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
		}

	}

	private static void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService
					.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService
					.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount,
					receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void sortMenu() {
		do {
			System.out.println("Enter which way to sort: ");
			System.out.println("1. Ascending order.");
			System.out.println("2. Decending order.");
			System.out.println("3. Exit.");
			int choiceForType = scanner.nextInt();
			waysOfSorting(choiceForType);
		} while (true);

	}

	private static void waysOfSorting(int choiceForType) {
		switch (choiceForType) {
		case 1:
			do {
				System.out.println("+++++Ways of Sorting+++++++");
				System.out.println("1. Account Holder Name");
				System.out.println("2. Account Balance");
				System.out.println("3. Exit from Sorting");

				int choice = scanner.nextInt();

				sortTheFields(choice);

			} while (true);
		case 2:
			do {
				System.out.println("+++++Ways of Sorting+++++++");
				System.out.println("1. Account Holder Name");
				System.out.println("2. Account Balance");
				System.out.println("3. Exit from Sorting");

				int choice = scanner.nextInt();

				sortTheFieldsInDecendingOrder(choice);

			} while (true);
		case 3:
			start();
			break;
		default:
			System.out.println("Invalid Choice.");
			break;
		}

	}

	private static void sortTheFieldsInDecendingOrder(int choice) {
		switch (choice) {
		case 1:
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(savingsAccounts,
						new SortByAccountHolderNameInDescending());
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();
			}
			break;
		case 2:
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(savingsAccounts,
						new SortByAccountBalanceInDescending());
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();
			}
			break;
		case 3:
			start();
			break;
		default:
			System.out.println("Invalid Choice.");

		}

	}

	private static void sortTheFields(int choice) {
		switch (choice) {
		case 1:
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService
						.getAllSavingsAccount();
				Collections
						.sort(savingsAccounts, new SortByAccountHolderName());
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();
			}
			break;
		case 2:
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(savingsAccounts, new SortByAccountBalance());
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			start();
			break;

		default:
			System.out.println("Invalid Choice.");
			break;
		}

	}

	private static void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private static void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out
					.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false
					: true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}
	}

	private static void createSavingsAccount(String accountHolderName,
			double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName,
					accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
