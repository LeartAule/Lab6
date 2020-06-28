package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.LinkedHashMap;



public class Remove_greater extends AbstractCommand{
    public Remove_greater() {
        command  = "remove_greater";
        TextInfo = " {element} : удалить из коллекции все элементы, превышающие заданный";

        NeedAnStr = false;
        NeedAnObject =true;
    }

    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        if(collection.isEmpty()) return "Коллекция пуста";
        //System.out.println("Введите данные Группы. Все группы, которые больше вашей(исходя из логики сравнения), будут удалены.");
        dragon.update(dragon.getGlobalId());
        dragonLinkedHashMap.entrySet().removeIf(entry -> entry.getValue().compareTo(dragon) < 0);

        return "Команда выполнена";
    }

    public void execute(Dragon dragon){
        dragonLinkedHashMap.entrySet().removeIf(entry -> entry.getValue().compareTo(dragon) < 0);
    }

}
