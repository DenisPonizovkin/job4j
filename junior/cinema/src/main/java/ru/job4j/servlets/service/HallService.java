package ru.job4j.servlets.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.job4j.servlets.persistence.Connector;
import ru.job4j.servlets.persistence.DbConnect;
import ru.job4j.servlets.persistence.DbStoreHall;
import ru.job4j.servlets.persistence.Seat;
import ru.job4j.servlets.persistence.Store;

public class HallService implements StoreService<Seat> {

	@Override
	public Seat add(Seat value) {
		Seat s = null;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store hs = new DbStoreHall(c.connect());
			s = (Seat) hs.add(value);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return s;
	}

	@Override
	public boolean update(Seat value) {
		boolean ok = true;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store hs = new DbStoreHall(c.connect());
			ok = hs.update(value);
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
			Store hs = new DbStoreHall(c.connect());
			ok = hs.update(id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return ok;
	}

	@Override
	public List<Seat> findAll() {
		List<Seat> al = null;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store hs = new DbStoreHall(c.connect());
			al = hs.findAll();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return al;
	}

	@Override
	public Seat findById(int id) {
		Seat s = null;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store hs = new DbStoreHall(c.connect());
			s = (Seat) hs.findById(id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return s;
	}

	public Seat findSeatByRowNumber(int row, int number) {
		Seat s = null;
		try (Connector c = DbConnect.getInstance().connector()) {
			Store hs = new DbStoreHall(c.connect());
			s = ((DbStoreHall) hs).findSeatByRowNumber(row, number);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return s;
	}
	
}
