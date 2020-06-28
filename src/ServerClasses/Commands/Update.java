package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Update extends AbstractCommand{

    public Update() throws TransformerException, ParserConfigurationException {

        command = "update_id";
        TextInfo = "{id} : обновить значение элемента коллекции, id которого равен заданному";
        NeedAnStr = true;

        NeedAnObject = true;
    }


    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {

        if(dragon.getName().equals(null)) return "Не задан объект";



        boolean bool = true;

        Integer j = getDragon().getId();
        for (Map.Entry<Integer, Dragon> dragonEntry : collection.entrySet()) {
            if (dragonEntry.getKey().equals(j)) {
                bool = false;
            }
        }
        if (!bool) {
            String msg = collection.get(j).getName();
            collection.put(j, dragon);
            return msg + " был заменён на " + dragon.getName();
        }else{
            return ("Элемента с таким номером не существует.");
        }


    }
}
