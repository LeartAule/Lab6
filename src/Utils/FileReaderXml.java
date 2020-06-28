package Utils;

import ServerClasses.Information;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.time.LocalDateTime;


import Dragon.*;

/**
 * Класс для чтения xml файла.
 * Объект считывания - Dragon
 */

public class FileReaderXml extends Information implements Runnable {

    private static boolean bool = false;
    private UserReader u = new UserReader();


    public void ReadDragonFile() throws IOException {


    File reserve = getNewXml();
    File main = getXml();

    while (!getXml().exists()) {
        System.out.println("Файл не найден");
        setXml(new File(u.read()));
    }

    while (!getXml().canWrite() || !getXml().canRead()) {
        System.out.println("Недостаточно прав на файл" + getXml());
        setXml(new File(u.read()));

    }




    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(getXml());
        doc.getDocumentElement().normalize();
        NodeList nodeList1 = doc.getElementsByTagName("Dragon");
        NodeList nodeList2 = doc.getElementsByTagName("Coordinates");
        NodeList nodeList3 = doc.getElementsByTagName("Cave");


        // создадим из него список объектов Dragon
        int number = nodeList1.getLength();
        for (int i = 0; i < nodeList1.getLength(); i++) {
            Dragon dragon = new Dragon();
            getDragon(dragon, nodeList1.item(i));
            dragon.setCoordinates(getCoordinates(nodeList2.item(i)));
            dragon.setCave(getCave(nodeList3.item(i)));
            getDragonLinkedHashMap().put(dragon.getId(), dragon);
            bool = true;
            //System.out.println(dragon.getName());
            if (number < dragon.getId()) {
                dragon.setGlobalId(dragon.getId());
            }
        }
        System.out.println("Файл " + getXml() + " прочитан");

        if (!bool) {
            System.out.println("В файле не было найдено искомой информации.");
        }
    } catch (Exception exc) {
        if (!bool) {
            System.out.println("Файл нельзя обработать");
        }
    }

}

    private static Dragon getDragon(Dragon dragon, Node node){
        if(node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            dragon.setName(getTagValue("name", element));
            dragon.setColor(Color.valueOf(getTagValue("color", element)));
            dragon.setAge(Integer.parseInt(getTagValue("age", element)));
            dragon.setCharacter(DragonCharacter.valueOf(getTagValue("character", element)));
            dragon.setType(DragonType.valueOf(getTagValue("type", element)));
            dragon.setId(Integer.parseInt(getTagValue("id", element)));
            dragon.SetTime(LocalDateTime.parse(getTagValue("Time", element)));
        }
        return dragon;
    }

    private static Coordinates getCoordinates(Node node){
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return new Coordinates(Integer.parseInt(getTagValue("x", element)), Integer.parseInt(getTagValue("y", element)));
        }
        return null;
    }

    private static DragonCave getCave(Node node){
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            DragonCave cave = new DragonCave();
            cave.setDepth(Float.parseFloat(getTagValue("depth", element)));
            return  cave;
        }
        return null;
    }



    private static String getTagValue(String name, Element element) {
        NodeList nodeList = element.getElementsByTagName(name).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public void run() {
        try {
            ReadDragonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
