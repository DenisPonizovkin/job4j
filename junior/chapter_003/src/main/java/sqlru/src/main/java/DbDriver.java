import model.Ad;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbDriver implements AutoCloseable {
    private Connection conn;
	private final static Logger LOGGER = Logger.getLogger(DbDriver.class);

	public void init() throws IOException, SQLException {
        Properties props = new Properties();
        try (FileInputStream propertiesFile =
                     new FileInputStream(
                             String.valueOf(getClass().getResourceAsStream("app.properties")))) {

            props.load(propertiesFile);
            String url = "jdbc:"
                    + props.getProperty("jdbc.driver")
                    + "://"
                    + props.getProperty("jdbc.url");
            conn = DriverManager.getConnection(
                    url,
                    props.getProperty("jdbc.username"),
                    props.getProperty("jdbc.password")
            );
        }
    }

    public int vacanciesNumber() throws SQLException {
        int n = 0;
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("select count(*) from sqlru.vacancies")) {
            while (rs.next()) {
                n = rs.getInt(1);
            }
        }
        return n;
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    public void insert(Ad ad) throws SQLException {
	    if (!isDuplicate(ad)) {
	        LOGGER.debug("Insert ad: " + ad);
            try (PreparedStatement st =
                         conn.prepareStatement(
                                 "insert into sqlru.vacancies ("
                                         + "theme"
                                         + ", author"
                                         + ", answers_number"
                                         + ", views_number"
                                         + ", timestamp) values (?,?,?,?,?)",
                                 Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, ad.getTheme());
                st.setString(2, ad.getAuthor());
                st.setInt(3, ad.getAnswersNumber());
                st.setInt(4, ad.getViewsNumber());
                st.setLong(5, ad.datetime2timestamp());
                st.executeUpdate();
            }
        } else {
	        LOGGER.debug("Ad " + ad + " already exists in db");
        }
    }

    private boolean isDuplicate(Ad ad) throws SQLException {

        int n = 0;
        try (PreparedStatement st =
                     conn.prepareStatement("select count(*) from sqlru.vacancies where theme = ?")) {
            st.setString(1, ad.getTheme());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    n = rs.getInt(1);
                    break;
                }
            }
        }
        return n > 0;
    }
}
