package ru.job4j.servlets.persistence;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Connector implements Closeable {

	private Connection con = null;
	
	public Connector() {
		
	}
	
	public Connection connect(BasicDataSource src) throws SQLException {
		con = src.getConnection();
		con.setAutoCommit(false);
		return con;
	}
	
	@Override
	public void close() throws IOException {
		try {
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
