package sample;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class Controller {

    @FXML private TableView<Person> table ;

    private static final String XML_FILE_NAME = "C:\\Users\\Serg\\IdeaProjects\\XMLAddFromFile\\src\\sample\\PList.xml" ;

    public void initialize() throws Exception {
        table.getItems().addAll(readXMLData());
    }

    private List<Person> readXMLData() throws Exception {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xmlDoc = docBuilder.parse(new InputSource(new FileReader(XML_FILE_NAME)));
        Element root = xmlDoc.getDocumentElement() ;
        if (! root.getTagName().equals("PersonList")) {
            throw new XMLStreamException("Root element must be PersonList");
        }
        List<Person> people = new ArrayList<Person>();

        NodeList personNodeList = root.getElementsByTagName("Person") ;
        int numPeople = personNodeList.getLength();
        for (int i=0; i<numPeople; i++) {
            Node personNode = personNodeList.item(i);
            String firstName = "" ;
            String lastName = "" ;
            NodeList personChildNodes = personNode.getChildNodes();
            for (int j=0; j<personChildNodes.getLength(); j++) {
                Node childNode = personChildNodes.item(j);
                if (childNode.getNodeName().equals("FirstName")) {
                    firstName = childNode.getTextContent();
                } else if (childNode.getNodeName().equals("LastName")) {
                    lastName = childNode.getTextContent();
                }
            }
            people.add(new Person(firstName, lastName));
        }


        return people ;
    }

    public class Person {
        private final StringProperty firstName ;
        private final StringProperty lastName ;
        Person(String firstName, String lastName) {
            this.firstName = new SimpleStringProperty(this, "firstName", firstName);
            this.lastName = new SimpleStringProperty(this, "lastName", lastName);
        }
        public String getFirstName() { return firstName.get(); }
        public void setFirstName(String firstName) { this.firstName.set(firstName);}
        public StringProperty firstNameProperty() { return firstName ; }
        public String getLastName() { return lastName.get(); }
        public void setLastName(String lastName) { this.lastName.set(lastName); }
        public StringProperty lastNameProperty() { return lastName ; }
    }

}
