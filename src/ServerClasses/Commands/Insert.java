package ServerClasses.Commands;

import Dragon.*;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Insert extends AbstractCommand {

    public Insert(){
        command = "insert";
        TextInfo = "{key} : добавить новый элемент с заданным ключом";


        NeedAnStr = true;
        NeedAnObject = true;
    }




    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {

        Integer id = getDragon().getId();


        for (Map.Entry<Integer, Dragon> dragonEntry : dragonLinkedHashMap.entrySet()) {
            if (dragonEntry.getKey().equals(id)) {
                String str = ColorText.Text("info", Color.YELLOW) +": вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
                return str;

            }
        }
            collection.put(id, dragon);

            return "Дракон " + dragon.getName() + " был добавлен.";

    }
}
