package ru.job4j.servlets.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import ru.job4j.servlets.persistence.Account;
import ru.job4j.servlets.persistence.Connector;
import ru.job4j.servlets.persistence.DbConnect;
import ru.job4j.servlets.persistence.DbStoreAccount;
import ru.job4j.servlets.persistence.DbStoreHall;
import ru.job4j.servlets.persistence.Seat;
import ru.job4j.servlets.persistence.Store;

public class AccountHallService {

	public void add(Account a, Seat s) throws SQLException {
		try (Connector c = DbConnect.getInstance().connector()) {
			Store hs = new DbStoreHall(c.connect());
			Store as = new DbStoreAccount(c.connect());

			as.add(a);
			hs.update(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
