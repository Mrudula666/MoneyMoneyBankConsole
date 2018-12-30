package com.moneymoney.account;

import java.util.Comparator;

public class SortByAccountBalanceInDescending extends SavingsAccount implements Comparator<SavingsAccount> {

	@Override
	public int compare(SavingsAccount savingAccount1, SavingsAccount savingAccount2) {
		if (savingAccount1.getBankAccount().getAccountBalance() < savingAccount2
				.getBankAccount().getAccountBalance())
			return 1;
		else if (savingAccount1.getBankAccount().getAccountBalance() < savingAccount2
				.getBankAccount().getAccountBalance())
			return -1;
		return 0;
	}

	

}
