package tracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Tracker implements AutoCloseable {

    private Connection connection;
    private String path;

    public void init() throws IOException, SQLException {
        Properties props = new Properties();
        try (FileInputStream propertiesFile =
                     new FileInputStream(
                              System.getProperty("user.dir")
                                      + "/src/main/java/tracker/" + "conn.properties")) {
            props.load(propertiesFile);
            path = props.getProperty("sql_script_path");
            connection = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("user"),
                    props.getProperty("password")
            );
            if (databaseStructureIsEmpty()) {
                createDatabaseStructure();
            }
        }
    }

    private void createDatabaseStructure() throws IOException, SQLException {
        ScriptRunner runner = new ScriptRunner(connection, path);
        List<String> errors = runner.run();
        if (errors.size() > 0) {
            errors.forEach(System.out::println);
            throw new SQLException("Can't create database");
        }
    }

    private boolean databaseStructureIsEmpty() throws SQLException {
        boolean empty = true;
        DatabaseMetaData dbm = connection.getMetaData();
        try (ResultSet rs = dbm.getTables(null, null, "items", null)) {
            if (rs.next()) {
                empty = false;
            }
        }
        return empty;
    }

    public Item add(Item item) throws SQLException {
        try (PreparedStatement st =
                connection.prepareStatement("insert into tracker.items (name) values (?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    item.setId(keys.getInt(1));
                }
            }
        }

        return item;
    }

    public void replace(int id, Item item) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement("update tracker.items set name=? where id=?")) {
            st.setString(1, item.getName());
            st.setInt(2, id);
            st.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement("delete from tracker.items where id = ?")) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    public List<Item> findAll() throws SQLException {
        List<Item> items = new ArrayList<Item>();
        try (PreparedStatement st = connection.prepareStatement("select * from tracker.items")) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    items.add(new Item(rs.getInt(1), rs.getString(2)));
                }
            }
        }
        return items;
    }

    public List<Item> findByName(String key) throws SQLException {
        List<Item> items = new ArrayList<Item>();
        try (PreparedStatement st = connection.prepareStatement("select * from tracker.items where name = ?")) {
            st.setString(1, key);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    items.add(new Item(rs.getInt(1), rs.getString(2)));
                }
            }
        }
        return items;
    }

    public Item findById(int id) throws SQLException {
        List<Item> items = new ArrayList<Item>();
        try (PreparedStatement st = connection.prepareStatement("select * from tracker.items where id = ?")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    items.add(new Item(rs.getInt(1), rs.getString(2)));
                }
            }
        }
        return items.get(0);
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public boolean connectionIsNull() {
        return connection == null;
    }
}
