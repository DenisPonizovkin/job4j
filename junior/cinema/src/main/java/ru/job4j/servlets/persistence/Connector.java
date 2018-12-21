package ru.job4j.servlets.persistence;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Connector implements Closeable {

	private Connection con = null;
	private BasicDataSource src = null;

	public Connector(BasicDataSource bds) {
		src = bds;
	}
	
	public Connection connect() throws SQLException {
		con = src.getConnection();
		con.setAutoCommit(false);
		return con;
	}
	
	@Override
	public void close() throws IOException {
		try {
			con.commit();
			con.close();
		} catch (SQLException e) {
			try {
				con.rollback();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
