package magnit;


import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StoreSQLTest {

    @Test
    public void whenStartThenNRowsExists() throws Exception {
        final int n = 5;
        Config cfg = new Config("mgnt.db", "entry", n);
        try (StoreSQL ss = new StoreSQL(cfg)) {
            ss.start();
            assertThat(ss.count(), is(n));
        }
    }

    @Test
    public void whenSaveToFileNoExceptions() throws Exception {
        final int n = 15;
        Config cfg = new Config("magnit.db", "entry", n);
        try (StoreSQL ss = new StoreSQL(cfg)) {
            ss.start();
            StoreXML sxml = new StoreXML(new File("./magnit.xml"));
            sxml.save(ss.findAll());
            assertThat(true, is(true));
        }
    }

}