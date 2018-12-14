package ru.job4j.servlets.service;

import java.sql.Connection;
import java.sql.SQLException;

import ru.job4j.servlets.persistence.Account;
import ru.job4j.servlets.persistence.DbConnect;
import ru.job4j.servlets.persistence.DbStoreAccount;
import ru.job4j.servlets.persistence.DbStoreHall;
import ru.job4j.servlets.persistence.Seat;
import ru.job4j.servlets.persistence.Store;

public class AccountHallService {

	public void add(Account a, Seat s) throws SQLException {
		try (Connection c = DbConnect.getInstance().connect()) {
			Store as = new DbStoreHall(c);
			Store hs = new DbStoreAccount(c);
			
			as.add(a);
			hs.add(s);
		}
	}
	
}
