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

    public static class ConnectionIsNull extends Exception {
    }

    public static class CreateDatabaseStructoreErrors extends Exception {
    }

    public void init() throws IOException, CreateDatabaseStructoreErrors {
        Properties props = new Properties();
        try {
            props.load(
                    new FileInputStream(
                            "/home/denis/work/job4j/junior/chapter_003/src/main/java/tracker/conn.properties")
            );
            this.connection = DriverManager.getConnection(props.getProperty("url"),
                    props.getProperty("user"),
                    props.getProperty("password"));
            path = props.getProperty("sql_script_path");
            if (databaseStructureIsEmpty()) {
                createDatabaseStructure();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabaseStructure() throws IOException, CreateDatabaseStructoreErrors {
        ScriptRunner runner = new ScriptRunner(connection, path);
        List<String> errors = runner.run();
        if (errors.size() > 0) {
            errors.forEach(System.out::println);
            throw new CreateDatabaseStructoreErrors();
        }
    }

    private boolean databaseStructureIsEmpty() throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet rs = dbm.getTables(null, null, "items", null);
        boolean empty = true;
        if (rs.next()) {
            empty = false;
        }
        return empty;
    }

    public Item add(Item item) throws ConnectionIsNull, SQLException {
        if (connection == null) {
            throw new ConnectionIsNull();
        }

        PreparedStatement st =
                connection.prepareStatement("insert into tracker.items (name) values (?)",
                        Statement.RETURN_GENERATED_KEYS);
        st.setString(1, item.getName());
        st.executeUpdate();

        ResultSet keys = st.getGeneratedKeys();
        if (keys.next()) {
            item.setId(keys.getInt(1));
        }

        st.close();

        return item;
    }

    public void replace(int id, Item item) throws ConnectionIsNull, SQLException {
        if (connection == null) {
            throw new ConnectionIsNull();
        }
        PreparedStatement st = connection.prepareStatement("update tracker.items set name=? where id=?");
        st.setString(1, item.getName());
        st.setInt(2, id);
        st.executeUpdate();
        st.close();
    }

    public void delete(int id) throws ConnectionIsNull, SQLException {
        if (connection == null) {
            throw new ConnectionIsNull();
        }
        PreparedStatement st = connection.prepareStatement("delete from tracker.items where id = ?");
        st.setInt(1, id);
        st.executeUpdate();
        st.close();
    }

    public List<Item> findAll() throws ConnectionIsNull, SQLException {
        if (connection == null) {
            throw new ConnectionIsNull();
        }
        PreparedStatement st = connection.prepareStatement("select * from tracker.items");
        ResultSet rs = st.executeQuery();
        List<Item> items = new ArrayList<Item>();
        while (rs.next()) {
            items.add(new Item(rs.getInt(1), rs.getString(2)));
        }
        rs.close();
        st.close();
        return items;
    }

    public List<Item> findByName(String key) throws ConnectionIsNull, SQLException {
        if (connection == null) {
            throw new ConnectionIsNull();
        }

        PreparedStatement st = connection.prepareStatement("select * from tracker.items where name = ?");
        st.setString(1, key);
        ResultSet rs = st.executeQuery();

        List<Item> items = new ArrayList<Item>();
        while (rs.next()) {
            items.add(new Item(rs.getInt(1), rs.getString(2)));
        }

        rs.close();
        st.close();

        return items;
    }

    public Item findById(int id) throws ConnectionIsNull, SQLException {
        if (connection == null) {
            throw new ConnectionIsNull();
        }
        PreparedStatement st = connection.prepareStatement("select * from tracker.items where id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        List<Item> items = new ArrayList<Item>();
        while (rs.next()) {
            items.add(new Item(rs.getInt(1), rs.getString(2)));
        }

        rs.close();
        st.close();

        return items.get(0);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public boolean connectionIsNull() {
        return connection == null;
    }
}
