package com.moneymoney.account;

import java.util.Comparator;

public class SortByAccountHolderName extends SavingsAccount implements Comparator<SavingsAccount> {


	@Override
	public int compare(SavingsAccount arg0, SavingsAccount arg1) {
		return (arg0.getBankAccount().getAccountHolderName().compareToIgnoreCase(arg1.getBankAccount().getAccountHolderName()));
	}
	
	
}
