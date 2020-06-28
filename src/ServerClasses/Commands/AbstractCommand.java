package ServerClasses.Commands;

import Dragon.*;
import Exceptions.InvalidCountOfArgumentException;
import ServerClasses.Information;
import Utils.Writter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public abstract class AbstractCommand extends Information implements Serializable, ColorText {

    protected boolean NeedAnObject = false; //Необходимость объекта Dragon для команды.
    protected String command;// Название команды
    protected String TextInfo;// Описание команды

    protected boolean NeedAnStr = false;//Необходимость аргумента для команды.

    protected Dragon dragon = new Dragon();

    protected String string = "";





    //Getters and Setters
    public void setDragon(Dragon dragon){
        this.dragon = dragon;
    }

    public void setString(String string){
        this.string = string;
    }

    public Dragon getDragon(){
        return dragon;
    }

    public boolean isNeedAnStr() {
        return NeedAnStr;
    }

    public boolean CheckAndSetDragon(Object o){
        try{
        this.dragon = (Dragon) o;
        return true;}
        catch (ClassFormatError e){
            return false;
        }
    }

    public String getCommand() {
        return command;
    }

    public String getTextInfo() {
        return TextInfo;
    }

    public String execute(){
        return "AB Такая команда не существует";
    }

    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException{
        return execute();
    }


    //Запрос на принятие объекта
    public boolean getObjectExecute(){
        return NeedAnObject;
    }

    public String getString(){
        return string;
    }


    //сохранение коллекции в указанный файл
    protected void ReserveSave(LinkedHashMap<Integer, Dragon> collection, File file) throws TransformerException, ParserConfigurationException {
        Writter.write(collection, file);
    }


    //Сохранение коллекцию в файл по умолчанию. (xml.xml)
    protected void ReserveSave() throws TransformerException, ParserConfigurationException {
        Writter.write(dragonLinkedHashMap, getNewXml());
    }

    public String toPrint() {
        return command + " " + TextInfo;
    }


    @Override
    public String toString() {
        return command;
    }

    protected static LinkedHashMap<Integer, Dragon> SortCollection(LinkedHashMap<Integer, Dragon> collection ) {
        Comparator<Dragon> dragonComparator = new Comparator<Dragon>() {
            @Override
            public int compare(Dragon s, Dragon s1) {
                return s.getName().compareTo(s1.getName());
            }
        };
        List<Dragon> dragons = new ArrayList<>();
        for (Map.Entry<Integer, Dragon> dragonEntry : collection.entrySet()) {
            dragons.add(dragonEntry.getValue());
        }
        LinkedHashMap<Integer, Dragon> linkedHashMap = new LinkedHashMap<Integer, Dragon>();
        Collections.sort(dragons, dragonComparator);
        for(Dragon dragon : dragons){
            linkedHashMap.put(dragon.getId(), dragon);
        }
        collection = linkedHashMap;

        return collection;
    }
}
