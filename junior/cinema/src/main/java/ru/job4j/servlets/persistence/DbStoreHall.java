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
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "select * from cinema.halls where id = ?"
                     );
        ) {
            st.setInt(1, s.getId());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    existingSeat = rs2seat(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "insert into cinema.halls (row, place, busy) values (?, ?, ?)");
        ) {
            if (existingSeat == null) {
                st.setInt(1, s.getRow());
                st.setInt(2, s.getNumber());
                st.setBoolean(3, s.isBusy());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingSeat;
	}

	private Seat rs2seat(ResultSet rs) throws SQLException {
		Seat s = new Seat();
		s.setId(rs.getInt(1));
		s.setRow(rs.getInt(2));
		s.setNumber(rs.getInt(3));
		s.setBusy(rs.getBoolean(4));
		return s;
	}

	@Override
	public boolean update(Seat s) {
        boolean ok = false;
        if (findById(s.getId()) != null) {
            String query = "update cinema.halls set busy=? where id=?";
            try (PreparedStatement st = connection.prepareStatement(query)) {

                st.setQueryTimeout(2);

                st.setBoolean(1, s.isBusy());
                st.setInt(2, s.getId());
                //SELECT count(*) FROM cinema.halls where busy=true
                int updatedRows = st.executeUpdate();
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
           try (PreparedStatement st =
                        connection.prepareStatement("delete from cinema.halls where id = ?");
           ) {
            	st.setInt(1, id);
            	st.executeUpdate();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       return ok;
	}

	@Override
	public List<Seat> findAll() {
        List<Seat> accounts = new ArrayList<Seat>();
        try (PreparedStatement st =
                     connection.prepareStatement("select * from cinema.halls");
        ) {
            try (ResultSet rs = st.executeQuery()) {
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
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "select * from cinema.halls where id = ?"
                     );
        ) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
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
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "select * from cinema.halls where row=? and place=?"
                     );
        ) {
            st.setInt(1, row);
            st.setInt(2, place);
            try (ResultSet rs = st.executeQuery()) {
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