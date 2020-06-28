package ServerClasses;

import Dragon.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * Класс в котором храниться информация о коллекции и файлы для её записи
 *
 *
 */


abstract public class Information implements ColorText{

    protected static LinkedHashMap<Integer, Dragon> dragonLinkedHashMap = new LinkedHashMap<Integer, Dragon>();


    public static LinkedHashMap<Integer, Dragon> getDragonLinkedHashMap() {
        return dragonLinkedHashMap;
    }

    protected static File getXml() {
        return xml;
    }

    public static void setXml(File xml) {
        Information.xml = xml;
    }

    protected static File getNewXml() {
        return newXml;
    }

    protected static void setNewXml(File newXml) {
        Information.newXml = newXml;
    }

    static private File xml = new File("xml.xml");
    static private File newXml;// = new File("newXml.xml");



    protected static BufferedReader bufferedReader;
    protected static BufferedWriter bufferedWriter;

    protected static BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    protected static BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }


    protected Dragon dragon = new Dragon();

    public void setDragon(Dragon dragon){
        this.dragon = dragon;
    }


    public boolean CheckAndSetDragon(Object o) {
        try {
            Dragon dragon1 = (Dragon) o;
            this.dragon = dragon1;
            return true;
        } catch (ClassFormatError e) {
            return false;
        }
    }




    public static void stop() throws IOException {
        try {
            getBufferedWriter().close();
            getBufferedReader().close();
        }catch (NullPointerException e){}
        //getNewXml().delete();
    }

    protected static void setBufferedReader() throws FileNotFoundException, IOException {
        if(!getNewXml().exists()){
            getNewXml().createNewFile();
        }
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(getNewXml())));
    }

    protected static void setBufferedWriter() throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(getNewXml()));
    }




//Сортирует коллецию по имени Дракона
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


    public void setCollection(LinkedHashMap<Integer, Dragon> collection){
        dragonLinkedHashMap = collection;
    }

    public LinkedHashMap getCollection(){
        return Information.getDragonLinkedHashMap();
    }


}