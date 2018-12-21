package ru.job4j.servlets.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.job4j.servlets.persistence.Account;
import ru.job4j.servlets.persistence.Connector;
import ru.job4j.servlets.persistence.DbConnect;
import ru.job4j.servlets.persistence.DbStoreAccount;
import ru.job4j.servlets.persistence.Store;

public class AccountService implements StoreService<Account> {

	@Override
	public Account add(Account value) {
		Account a = null;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store ha = new DbStoreAccount(c.connect());
			a = (Account) ha.add(value);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return a;
	}

	@Override
	public boolean update(Account value) {
		boolean ok = true;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store ha = new DbStoreAccount(c.connect());
			ok = ha.update(value);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = true;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store ha = new DbStoreAccount(c.connect());
			ok = ha.update(id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return ok;
	}

	@Override
	public List<Account> findAll() {
		List<Account> al = null;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store ha = new DbStoreAccount(c.connect());
			al = ha.findAll();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return al;
	}

	@Override
	public Account findById(int id) {
		Account a = null;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store ha = new DbStoreAccount(c.connect());
			a = (Account) ha.findById(id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return a;
	}
}
