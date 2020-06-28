package Utils;

import Dragon.Dragon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

/**
 * Класс Писатель
 * нужен чтобы записывать коллекции в файлы xml формата
 *
 * Writer
 */

public class Writter {
    public static void write(LinkedHashMap<Integer, Dragon> dragonLinkedHashMap , File file) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("Dragons");
        doc.appendChild(root);

        for(HashMap.Entry<Integer,Dragon> dragon : dragonLinkedHashMap.entrySet()){
            root.appendChild(createUser(doc,dragon.getValue()));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);

        StreamResult my_file = new StreamResult(file);
        // сохраняем в файл
        transf.transform(source, my_file);
    }



    private static Node createUserElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
}


    private static Node createUser(Document doc,Dragon dragon) {
        Element dragons = doc.createElement("Dragon");
        Element cords = doc.createElement("Coordinates");
        Element cave = doc.createElement("Cave");

        dragons.appendChild(createUserElement(doc, "id", String.valueOf(dragon.getId())));
        dragons.appendChild(createUserElement(doc, "name",dragon.getName()));
        dragons.appendChild(createUserElement(doc,"age",dragon.getAge().toString()));
        dragons.appendChild(createUserElement(doc,"color",dragon.getColor().toString()));
        dragons.appendChild(createUserElement(doc,"type",dragon.getType().toString()));
        dragons.appendChild(createUserElement(doc,"character",dragon.getCharacter().toString()));
        dragons.appendChild(createUserElement(doc, "Time", dragon.GetTime().toString()));
        cords.appendChild(createUserElement(doc,"x",String.valueOf(dragon.getCoordinates().getX())));
        cords.appendChild(createUserElement(doc,"y",String.valueOf(dragon.getCoordinates().getY())));
        cave.appendChild(createUserElement(doc,"depth",String.valueOf(dragon.getCave().getDepth())));

        dragons.appendChild(cords);
        dragons.appendChild(cave);
        return dragons;
    }


}
