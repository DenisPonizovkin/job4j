package ru.job4j.servlets.persistence;

public interface StoreAccount extends Store<Account> {

	public Account findByName(String storeAccount);
	
}
