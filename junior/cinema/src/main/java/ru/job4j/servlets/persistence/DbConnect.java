package ru.job4j.servlets.persistence;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DbConnect {

	private static final BasicDataSource SOURCE = new BasicDataSource();
    private static DbConnect instance = null;
	
    private DbConnect() {
    	
    }
    
    public static DbConnect getInstance() {
    	if (instance == null) {
       		SOURCE.setDriverClassName("org.postgresql.Driver");
    		SOURCE.setUrl("jdbc:postgresql://localhost/job4j");
    		SOURCE.setUsername("postgres");
    		SOURCE.setPassword("postgres");
        	SOURCE.setMinIdle(5);
        	SOURCE.setMaxIdle(10);
        	SOURCE.setMaxOpenPreparedStatements(100);
        	instance = new DbConnect();
    	}
    	return instance;
    }
    
    public Connector connector() throws SQLException {
    	return new Connector(SOURCE);
    }
    
}
