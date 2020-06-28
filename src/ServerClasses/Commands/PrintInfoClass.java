package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;
import ServerClasses.Information;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Класс для вывода информации о коллекции
 */

public class PrintInfoClass extends AbstractCommand {

    public PrintInfoClass(){
        command = "info";
        TextInfo = ": вывести в стандартный поток вывода информацию о коллекции";
        NeedAnStr = false;
    }

    protected String printInfo(LinkedHashMap collection){
        String type = "Тип коллекции: " + collection.getClass().getSimpleName();
        String date = ("Дата инициализации: " + collection.values().toString());
        String size = ("Количество элементов: " + collection.size());

        return type + "\n" + date + "\n" + size;
    }

    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        return printInfo(collection);
    }
}
