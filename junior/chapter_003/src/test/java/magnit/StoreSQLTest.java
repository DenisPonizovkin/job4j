package magnit;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StoreSQLTest {

    //@Test
    public void whenStartThenNRowsExists() throws SQLException {
        System.out.println("Test 1 start");
        final int n = 5;
        Config cfg = new Config("magnit.db", "entry", n);
        StoreSQL ss = new StoreSQL(cfg);
        ss.start();

        System.out.println("Test 1 end");
        assertThat(ss.count(), is(n));
    }

    @Test
    public void whenSaveToFileNoExceptions() throws SQLException, JAXBException {
        final int n = 15;
        Config cfg = new Config("magnit.db", "entry", n);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + cfg.getBase())) {
            StoreSQL ss = new StoreSQL(cfg);
            ss.start();
            StoreXML sxml = new StoreXML(new File("./magnit.xml"));
            sxml.save(ss.findAll());

            System.out.println("Test 2 end");
            assertThat(true, is(true));
        }
   }

}