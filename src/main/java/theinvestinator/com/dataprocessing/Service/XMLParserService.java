package theinvestinator.com.dataprocessing.Service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

@Service
public class XMLParserService {

    //access to API in XML format
    Document xmlParser(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document xmlDocument = null;

        do {
            try {
                if (path.contains("http"))
                    xmlDocument = factory.newDocumentBuilder().parse(new URL(path).openStream());
                else
                    xmlDocument = factory.newDocumentBuilder().parse(new InputSource(path));
            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
            }
        } while (xmlDocument == null);

        xmlDocument.getDocumentElement().normalize();
        return xmlDocument;
    }
}
