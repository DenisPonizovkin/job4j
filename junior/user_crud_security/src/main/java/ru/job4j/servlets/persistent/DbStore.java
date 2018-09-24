package ru.job4j.servlets.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.servlets.model.Role;
import ru.job4j.servlets.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DbStore implements Store<User> {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static DbStore instance = new DbStore();

    public DbStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://localhost/job4j");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("postgres");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        //Properties props = new Properties();
        //try (FileInputStream propertiesFile =
        //             new FileInputStream(
        //                     String.valueOf(getClass().getResourceAsStream("/webapp/WEB-INF/properties/app.properties")))) {

        //    props.load(propertiesFile);
        //    String url = "jdbc:"
        //            + props.getProperty("jdbc.driver")
        //            + "://"
        //            + props.getProperty("jdbc.url");
        //    SOURCE.setUrl(url);
        //    SOURCE.setUsername(props.getProperty("name"));
        //    SOURCE.setPassword(props.getProperty("password"));
        //    SOURCE.setMinIdle(5);
        //    SOURCE.setMaxIdle(10);
        //    SOURCE.setMaxOpenPreparedStatements(100);
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    public static DbStore getInstance() {
        return instance;
    }

    @Override
    public User add(User model) {
        User existingUser = null;
        try (Connection connection = SOURCE.getConnection();
             Statement st =
                     connection.prepareStatement(
                             "select * from servlets.users where id = ?"
                     );
        ) {
            ((PreparedStatement) st).setInt(1, model.getId());
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    existingUser = rs2user(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (Connection connection = SOURCE.getConnection();
             Statement st =
                     connection.prepareStatement(
                             "insert into servlets.users "
                                + "(id, name, login, email, create_date, role_id) "
                                + "values (?, ?, ?, ?, ?, ?)");
        ) {
            if (existingUser == null) {
                ((PreparedStatement) st).setInt(1, model.getId());
                ((PreparedStatement) st).setString(2, model.getName());
                ((PreparedStatement) st).setString(3, model.getLogin());
                ((PreparedStatement) st).setString(4, model.getEmail());
                ((PreparedStatement) st).setInt(5, (int) model.getCreateDate());
                ((PreparedStatement) st).setInt(6, (int) model.getRole().getId());
                ((PreparedStatement) st).executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingUser;
    }

    private User rs2user(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(1));
        user.setName(rs.getString(2));
        user.setLogin(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setPassword(rs.getString(5));
        user.setCreateDate(rs.getInt(6));

        try (Connection connection = SOURCE.getConnection();
            Statement st = connection.prepareStatement("select * from servlets.roles where id = ?")) {
            ((PreparedStatement) st).setInt(1, rs.getInt(7));
            try (ResultSet rs2 = ((PreparedStatement) st).executeQuery()) {
                Role r = new Role();
                while (rs2.next()) {
                    r.setId(rs2.getInt(1));
                    r.setName(rs2.getString(2));
                    user.setRole(r);
                    break;
                }
            }
        }
        return user;
    }

    @Override
    public boolean update(User model) {
        boolean ok = false;
        if (findById(model.getId()) != null) {
            String query = "update servlets.users set name=?, login=?, email=?, password=?, create_date=?, role_id=? where id=?";
            try (Connection connection = SOURCE.getConnection();
                    Statement st = connection.prepareStatement(query)) {
                    ((PreparedStatement) st).setString(1, model.getName());
                    ((PreparedStatement) st).setString(2, model.getLogin());
                    ((PreparedStatement) st).setString(3, model.getEmail());
                    ((PreparedStatement) st).setString(4, model.getPassword());
                    ((PreparedStatement) st).setInt(5, (int) model.getCreateDate());
                    ((PreparedStatement) st).setInt(6, model.getRole().getId());
                    ((PreparedStatement) st).setInt(7, model.getId());
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
           try (Connection connection = SOURCE.getConnection();
                Statement st =
                        connection.prepareStatement("delete from servlets.users where id = ?");
           ) {
               ((PreparedStatement) st).setInt(1, id);
               ((PreparedStatement) st).executeUpdate();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       return ok;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        try (Connection connection = SOURCE.getConnection();
             Statement st =
                     connection.prepareStatement("select * from servlets.users");
        ) {
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    User u = rs2user(rs);
                    users.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection connection = SOURCE.getConnection();
             Statement st =
                     connection.prepareStatement(
                             "select * from servlets.users where id = ?"
                     );
        ) {
            ((PreparedStatement) st).setInt(1, id);
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    user = rs2user(rs);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean isCredential(String login, String password) {
        User u = findByLogin(login);
        boolean ok = false;
        if (u != null) {
            ok = u.getPassword().equals(password);
        }
        return ok;
    }

    @Override
    public User findByLogin(String login) {
        User user = null;
        try (Connection connection = SOURCE.getConnection();
             Statement st =
                     connection.prepareStatement(
                             "select * from servlets.users where login = ?"
                     );
        ) {
            ((PreparedStatement) st).setString(1, login);
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                while (rs.next()) {
                    user = rs2user(rs);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Role getRoleByName(String name) {
        Role r = null;
        try (Connection connection = SOURCE.getConnection();
             Statement st =
                     connection.prepareStatement(
                             "select * from servlets.roles where name = ?"
                     );
        ) {
            ((PreparedStatement) st).setString(1, name);
            try (ResultSet rs = ((PreparedStatement) st).executeQuery()) {
                r = new Role();
                while (rs.next()) {
                    r.setId(rs.getInt(1));
                    r.setName(rs.getString(2));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public List<String> getRoles() {
        List<User> users = findAll();
        return users.stream()
                .map(new Function<User, String>() {
                    @Override
                    public String apply(User user) {
                        return user.getRole().getName();
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
