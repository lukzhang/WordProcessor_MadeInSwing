import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.*;

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
import java.io.IOException;
import java.io.StringReader;

//************************************************
//In the relativley far future, might need to switch to SAX from DOM if XML gets to big. DOM faster because it loads
//entire XML into memory.
//**********************************************
public class ReadWriteXML {

    public String xmlPath = "../WordProcessor_MadeInSwing/UserData/data.xml";

    public boolean containsFileXML(String currFile) throws Exception{
        File xmlFile = new File(xmlPath);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        NodeList list = document.getElementsByTagName("DocName");

        for(int i=0; i<document.getElementsByTagName("DocName").getLength(); i++){
            Node node = list.item(i);

            if(node.getNodeType()==Node.ELEMENT_NODE){
                Element element = (Element)node;
                NamedNodeMap attributes = element.getAttributes();

                //Loop through in case we add more attributes later
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node tempNode = attributes.item(j);
                    //Do not just compare with '==', use 'equals'
                    if(currFile.equals(tempNode.getNodeValue())){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void writeXML(String currFile) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element element = document.createElement("MyFiles");
        document.appendChild(element);

        Element docName = document.createElement("DocName");
        docName.setAttribute("name", currFile);
        element.appendChild(docName);

        Element nonce = document.createElement("Nonce");
        nonce.appendChild(document.createTextNode("1"));    //Initialize to 1 upon creation of new doc
        docName.appendChild(nonce);

        Element dateCreated = document.createElement("DateCreated");
        dateCreated.appendChild(document.createTextNode("March 7, 2142"));
        docName.appendChild(dateCreated);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File(xmlPath));

        transformer.transform(source, streamResult);

    }

    public void appendXML(String docName) throws Exception{
        File xmlFile = new File(xmlPath);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xmlFile);

        NodeList rootList = doc.getElementsByTagName("MyFiles");
        Node root = rootList.item(0);

        // create new Employee
        Element employee = doc.createElement("DocName");
        employee.setAttribute("name", docName);

        // create child nodes
        Element firstName = doc.createElement("Nonce");
        firstName.appendChild(doc.createTextNode("1"));

        Element lastName = doc.createElement("DateCreated");
        lastName.appendChild(doc.createTextNode("Longtimeago"));

        // append and return
        employee.appendChild(firstName);
        employee.appendChild(lastName);

        // append using a helper method
        root.appendChild(employee);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult streamResult = new StreamResult(new File(xmlPath));

        transformer.transform(source, streamResult);
    }

    //Taken from: https://www.mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/
    //Use their xml as example to follow along
    public void incrementNonceXML(String docToFind){

        try {
            String filepath = xmlPath;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            // Get the staff element by tag name directly
            Node staff = doc.getElementsByTagName("DocName").item(0);

            // loop the staff child node
            NodeList list = staff.getChildNodes();


            for (int i = 0; i < doc.getElementsByTagName("DocName").getLength(); i++) {

                Node currDoc = doc.getElementsByTagName("DocName").item(i);

                // update staff attribute
                NamedNodeMap currAttb = currDoc.getAttributes();
                Node currName = currAttb.getNamedItem("name");

                if(docToFind.equals(currName.getTextContent())){
                    Node currNonce = doc.getElementsByTagName("Nonce").item(i);
                    int highestNonce = Integer.parseInt(currNonce.getTextContent());
                    highestNonce += 1;
                    System.out.println(highestNonce);
                    currNonce.setTextContent(""+highestNonce);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlPath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    public int highestNonce(String docToFind){

        try {
            String filepath = xmlPath;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            // Get the staff element by tag name directly
            Node staff = doc.getElementsByTagName("DocName").item(0);

            // loop the staff child node
            NodeList list = staff.getChildNodes();

            for (int i = 0; i < doc.getElementsByTagName("DocName").getLength(); i++) {

                Node node = list.item(i);

                Node currDoc = doc.getElementsByTagName("DocName").item(i);

                // update staff attribute
                NamedNodeMap currAttb = currDoc.getAttributes();
                Node currName = currAttb.getNamedItem("name");
                System.out.println(currName.getTextContent());

                if(docToFind.equals(currName.getTextContent())){
                    Node currNonce = doc.getElementsByTagName("Nonce").item(i);
                    int highestNonce = Integer.parseInt(currNonce.getTextContent());
                    return highestNonce;
                }
            }

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        return 0; //Return 0 if doc not fouund
    }

}
