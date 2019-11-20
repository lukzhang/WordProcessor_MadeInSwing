import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ReadWriteXML {

    public void writeXML() throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element element = document.createElement("Developer");
        document.appendChild(element);

        Attr attr = document.createAttribute("Id");
        attr.setValue("1");
        element.setAttributeNode(attr);

        Element name = document.createElement("Name");
        name.appendChild(document.createTextNode("Bruv"));
        element.appendChild(name);

        Element surname = document.createElement("Surname");
        name.appendChild(document.createTextNode("Hendrix"));
        element.appendChild(surname);

        Element age = document.createElement("Age");
        name.appendChild(document.createTextNode("21"));
        element.appendChild(age);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File("../MyJavaProjects/UserData/data.xml"));

        transformer.transform(source, streamResult);

    }

}
