package magnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreSQL implements AutoCloseable {

    private Connection conn = null;
    private final Config cfg;

    @Override
    protected void finalize() throws Throwable {
        System.out.println("!!!!!!!!!!!! FINAL");
        super.finalize();
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("!!!!!!!!!!!! CLOSE");
        if (conn != null) {
            conn.close();
        }
    }

    public StoreSQL(Config cfg) {
        this.cfg = cfg;
    }

    public void start() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:sqlite:" + cfg.getBase());
        }
        if (!tableExists()) {
            createaTable();
        }
        generate(cfg.getN());
    }

    public void generate(int n) {
        try {
            if (count() > 0) {
                clear();
            }
            String sql = "insert into entry values (?)";
            PreparedStatement st = conn.prepareStatement(sql);
            for (int i = 1; i <= cfg.getN(); i++) {
                st.setInt(1, i);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clear() throws SQLException {
        PreparedStatement st = conn.prepareStatement("delete  from " + cfg.getTable());
        st.executeUpdate();
        st.close();
    }

    public void createaTable() throws SQLException {
        conn.createStatement().execute(
                "create table "
                        + cfg.getTable()
                        + "( id integer primary key );");
    }

    public boolean tableExists() throws SQLDataException {
        DatabaseMetaData dbm = null;
        boolean empty = true;
        try {
            dbm = conn.getMetaData();
            ResultSet rs = dbm.getTables(null, null, cfg.getTable(), null);
            if (rs.next()) {
                empty = false;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return !empty;
    }

    public int count() {
        Statement st  = null;
        int n = 0;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from " + cfg.getTable());
            while (rs.next()) {
                n = rs.getInt(1);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public List<Entry> findAll() {
        Statement st  = null;
        List<Entry> list = new ArrayList<Entry>();
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from " + cfg.getTable());
            while (rs.next()) {
                list.add(new Entry(rs.getInt(1)));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
