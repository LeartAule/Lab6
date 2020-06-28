package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Remove extends AbstractCommand {

    public Remove(){
        command = "remove_key";
        TextInfo = "{key} : удалить элемент из коллекции по его ключу";

        NeedAnStr = true;
    }


    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {

        if(collection.isEmpty()) return "Коллекция пуста";

        boolean bool = false;
        Integer key = Integer.parseInt(string);



        for (Map.Entry<Integer, Dragon> dragonEntry : collection.entrySet()) {
            if (!dragonEntry.getKey().equals(key)) {
                bool = true;
            }
        }
        if(bool){
            String msg = collection.get(key).toDragonString();
            collection.remove(key);
            return ("Дракон " + msg + " под номер " + key + " был удален.");

        }else{
            return ("Такого дракона не существует.");
        }

    }
}
