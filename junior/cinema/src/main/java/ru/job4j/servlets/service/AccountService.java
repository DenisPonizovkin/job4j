package ru.job4j.servlets.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.job4j.servlets.persistence.Account;
import ru.job4j.servlets.persistence.DbConnect;
import ru.job4j.servlets.persistence.DbStoreAccount;
import ru.job4j.servlets.persistence.Store;

public class AccountService implements StoreService<Account> {

	@Override
	public Account add(Account value) {
		Account a = null;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store as = new DbStoreAccount(c);
			a = (Account) as.add(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public boolean update(Account value) {
		boolean ok = true;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store as = new DbStoreAccount(c);
			ok = as.update(value);
		} catch (SQLException e) {
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = true;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store as = new DbStoreAccount(c);
			ok = as.delete(id);
		} catch (SQLException e) {
			ok = false;
		}
		return ok;
	}

	@Override
	public List<Account> findAll() {
		List<Account> al = null;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store as = new DbStoreAccount(c);
			al = as.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}

	@Override
	public Account findById(int id) {
		Account a = null;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store as = new DbStoreAccount(c);
			a = (Account) as.findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
}
