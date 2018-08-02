package magnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreSQL implements AutoCloseable {

    private final Config cfg;

    @Override
    public void close() throws Exception {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public StoreSQL(Config cfg) {
        this.cfg = cfg;
    }

    public void start() throws SQLException {
        if (!tableExists()) {
            createaTable();
        }
        generate(cfg.getN());
    }

    private Connection createConnection() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + cfg.getBase())) {
            return c;
        }
    }

    public void generate(int n) throws SQLException {
        if (count() > 0) {
            clear();
        }
        String sql = "insert into entry values (?)";
        try (Connection conn = createConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (int i = 1; i <= cfg.getN(); i++) {
                    st.setInt(1, i);
                    st.executeUpdate();
                }
            }
        }
    }

    private void clear() throws SQLException {
        try (Connection conn = createConnection()) {
            try (PreparedStatement st = conn.prepareStatement("delete  from " + cfg.getTable())) {
                st.executeUpdate();
            }
        }
    }

    public void createaTable() throws SQLException {
        try (Connection conn = createConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute("create table " + cfg.getTable() + " (id integer primary key);");
            }
        }
    }

    public boolean tableExists() throws SQLException {
        boolean empty = true;
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + cfg.getBase())) {
            DatabaseMetaData dmd = c.getMetaData();
            try (ResultSet rs = dmd.getTables(null, null, cfg.getTable(), null)) {
                if (rs.next()) {
                    empty = false;
                }
            }
        }
        return !empty;
    }

    public int count() throws SQLException {
        int n = 0;
        try (Connection conn = createConnection()) {
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("select count(*) from " + cfg.getTable())) {
                while (rs.next()) {
                    n = rs.getInt(1);
                }
            }
        }
        return n;
    }

    public List<Entry> findAll() throws SQLException {
        List<Entry> list = new ArrayList<Entry>();
        try (Connection conn = createConnection()) {
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("select * from " + cfg.getTable())) {
                while (rs.next()) {
                    list.add(new Entry(rs.getInt(1)));
                }
            }
        }
        return list;
    }

}
