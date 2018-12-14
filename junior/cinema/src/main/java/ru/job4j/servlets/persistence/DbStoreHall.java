package ru.job4j.servlets.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

public class DbStoreHall implements StoreHall {

	private final DbConnect dc = DbConnect.getInstance();
	private Connection connection = null;
	
	public DbStoreHall(Connection c) {
		connection = c;
	}
	
	@Override
	public Seat add(Seat s) {
        Seat existingSeat = null;
        try (Statement st =
                     connection.prepareStatement(
                             "select * from servlets.halls where id = ?"
                     );
        ) {
        	connection.setAutoCommit(false);
            ((PreparedStatement) st).setInt(1, s.getId());
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    existingSeat = rs2seat(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (Statement st =
                     connection.prepareStatement(
                             "insert into servlets.accounts (row, place, busy) values (?, ?, ?, ?)");
        ) {
            if (existingSeat == null) {
            	connection.setAutoCommit(false);
                ((PreparedStatement) st).setInt(2, s.getRow());
                ((PreparedStatement) st).setInt(3, s.getNumber());
                ((PreparedStatement) st).setBoolean(4, s.isBusy());
                ((PreparedStatement) st).executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingSeat;
	}

	private Seat rs2seat(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Seat s) {
        boolean ok = false;
        if (findById(s.getId()) != null) {
            String query = "update servlets.accounts set row=?, number=?, busy=? where id=?";
            try (Statement st = connection.prepareStatement(query)) {

            		connection.setAutoCommit(false);
                    ((PreparedStatement) st).setInt(2, s.getRow());
                    ((PreparedStatement) st).setInt(3, s.getNumber());
                    ((PreparedStatement) st).setBoolean(4, s.isBusy());
                    ((PreparedStatement) st).executeUpdate();
                    ok = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ok;
	}

	@Override
	public boolean delete(int id) {
       boolean ok = false;
       if (findById(id) != null) {
           ok = true;
           try (Statement st =
                        connection.prepareStatement("delete from servlets.halls where id = ?");
           ) {
            	connection.setAutoCommit(false);
            	((PreparedStatement) st).setInt(1, id);
            	((PreparedStatement) st).executeUpdate();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       return ok;
	}

	@Override
	public List<Seat> findAll() {
        List<Seat> accounts = new ArrayList<Seat>();
        try (Statement st =
                     connection.prepareStatement("select * from servlets.halls");
        ) {
            connection.setAutoCommit(false);
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    Seat u = rs2seat(rs);
                    accounts.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
	}

	@Override
	public Seat findById(int id) {
        Seat s = null;
        try (Statement st =
                     connection.prepareStatement(
                             "select * from servlets.halls where id = ?"
                     );
        ) {
            connection.setAutoCommit(false);
            ((PreparedStatement) st).setInt(1, id);
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    s = rs2seat(rs);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
	}

	@Override
	public Seat findSeatByRowNumber(int row, int place) {
        Seat s = null;
        try (Statement st =
                     connection.prepareStatement(
                             "select * from servlets.halls where row=? and number=?"
                     );
        ) {
            connection.setAutoCommit(false);
            ((PreparedStatement) st).setInt(1, row);
            ((PreparedStatement) st).setInt(2, place);
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    s = rs2seat(rs);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
	}
}