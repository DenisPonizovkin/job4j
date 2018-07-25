package magnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.util.List;

public class StoreXML {

    private final File file;

    public StoreXML(File file) {
        this.file = file;
    }

    public void save(List<Entry> list) throws JAXBException {
        Entries entries = new Entries(list);
        JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(entries,  file);
    }

}
