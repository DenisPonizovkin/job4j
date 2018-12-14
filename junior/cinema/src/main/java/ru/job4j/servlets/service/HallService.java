package ru.job4j.servlets.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.job4j.servlets.persistence.DbConnect;
import ru.job4j.servlets.persistence.DbStoreHall;
import ru.job4j.servlets.persistence.Seat;
import ru.job4j.servlets.persistence.Store;

public class HallService implements StoreService<Seat> {

	@Override
	public Seat add(Seat value) {
		Seat s = null;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store hs = new DbStoreHall(c);
			s = (Seat) hs.add(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public boolean update(Seat value) {
		boolean ok = true;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store hs = new DbStoreHall(c);
			ok = hs.update(value);
		} catch (SQLException e) {
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = true;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store hs = new DbStoreHall(c);
			ok = hs.delete(id);
		} catch (SQLException e) {
			ok = false;
		}
		return ok;
	}

	@Override
	public List<Seat> findAll() {
		List<Seat> al = null;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store hs = new DbStoreHall(c);
			al = hs.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}

	@Override
	public Seat findById(int id) {
		Seat s = null;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store hs = new DbStoreHall(c);
			s = (Seat) hs.findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public Seat findSeatByRowNumber(int row, int number) {
		Seat s = null;
		try (Connection c = DbConnect.getInstance().connect()) {
			Store hs = new DbStoreHall(c);
			s = ((DbStoreHall) hs).findSeatByRowNumber(row, number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
}
