package com.moneymoney.account;

import java.util.Comparator;

public class SortByAccountHolderNameInDescending extends SavingsAccount implements Comparator<SavingsAccount> {

	

	@Override
	public int compare(SavingsAccount savingAccount1, SavingsAccount savingAccount2) {
		
		return (savingAccount2.getBankAccount().getAccountHolderName().compareToIgnoreCase(savingAccount1.getBankAccount().getAccountHolderName()));
	}

}
