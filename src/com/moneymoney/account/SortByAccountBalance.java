package com.moneymoney.account;

import java.util.Comparator;

public class SortByAccountBalance extends SavingsAccount implements
		Comparator<SavingsAccount> {

	@Override
	public int compare(SavingsAccount savingsAccount0,
			SavingsAccount savingsAccount1) {
		if (savingsAccount0.getBankAccount().getAccountBalance() > savingsAccount1
				.getBankAccount().getAccountBalance())
			return 1;
		else if (savingsAccount0.getBankAccount().getAccountBalance() < savingsAccount1
				.getBankAccount().getAccountBalance())
			return -1;
		return 0;
	}

}
