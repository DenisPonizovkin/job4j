package ru.job4j.servlets.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

public class DbStoreAccount implements StoreAccount {

	private Connection connection = null;
	
	public  DbStoreAccount(Connection c) {
		connection = c;
	}

	@Override
	public Account add(Account a) {
        Account existingAccount = null;
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "select * from cinema.accounts where id=?"
                     );
        ) {
            st.setInt(1, a.getId());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    existingAccount = rs2account(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "insert into cinema.accounts (name, phone, seat_id) values (?, ?, ?)");
        ) {
            if (existingAccount == null) {
                st.setString(1, a.getName());
                st.setString(2, a.getPhone());
                st.setInt(3, a.getSeatId());
                st.executeUpdate();
                existingAccount = a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingAccount;
	}

	private Account rs2account(ResultSet rs) throws SQLException {
		Account a = new Account();
        a.setId(rs.getInt(1));
        a.setName(rs.getString(2));
        a.setPhone(rs.getString(3));
		return a;
	}

	@Override
	public boolean update(Account a) {
        boolean ok = false;
        if (findById(a.getId()) != null) {
            String query = "update cinema.accounts set name=?, phone=?, seat_id=? where id=?";
            try (PreparedStatement st = connection.prepareStatement(query)) {
                    st.setString(2, a.getName());
                    st.setString(3, a.getPhone());
                    st.setInt(4, a.getSeatId());
                    st.executeUpdate();
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
                        connection.prepareStatement("delete from cinema.accounts where id=?");
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
	public List<Account> findAll() {
        List<Account> accounts = new ArrayList<Account>();
        try (PreparedStatement st =
                     connection.prepareStatement("select * from cinema.accounts");
        ) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Account u = rs2account(rs);
                    accounts.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
	}

	@Override
	public Account findById(int id) {
        Account account = null;
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "select * from cinema.accounts where id=?"
                     );
        ) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    account = rs2account(rs);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
	}

	@Override
	public Account findByName(String name) {
        Account account = null;
        try (PreparedStatement st =
                     connection.prepareStatement(
                             "select * from cinema.accounts where name=?"
                     );
        ) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    account = rs2account(rs);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
	}
}
