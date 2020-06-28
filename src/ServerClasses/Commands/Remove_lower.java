package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Remove_lower extends AbstractCommand{
    public Remove_lower() {
        command = "remove_lower";
        TextInfo = "{element} : удалить из коллекции все элементы, меньшие, чем заданный";


        NeedAnStr = false;
        NeedAnObject = true;
    }


    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        if(collection.isEmpty()) return "Коллекция пуста";
        //System.out.println("Введите данные Группы. Все группы, которые меньше вашей(исходя из логики сравнения), будут удалены.");
        dragon.update(dragon.getGlobalId());
        dragonLinkedHashMap.entrySet().removeIf(entry -> entry.getValue().compareTo(dragon) > 0);
        return null;
    }

    public String execute(Dragon dragon){
        if(dragonLinkedHashMap.isEmpty()) return "Коллекция пуста";
        dragonLinkedHashMap.entrySet().removeIf(entry -> entry.getValue().compareTo(dragon) > 0);
        return ("Команда выполнена");
    }
}
