package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.*;

public class Min_by_name extends AbstractCommand{
    public Min_by_name() {
        command = "min_by_name";
        TextInfo = ": Вывести объект, минимального по имени";
        NeedAnStr = false;
    }

    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {

        String msg = "";
        if(!collection.isEmpty()) {


            Comparator<String> stringComparator = new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return t1.compareTo(s);
                }
            };
            List<String> strings = new ArrayList<>();
            for (Map.Entry<Integer, Dragon> dragonEntry : dragonLinkedHashMap.entrySet()) {
                strings.add(dragonEntry.getValue().getName());
            }
            Collections.sort(strings, stringComparator);
            msg = ("Вывод имен в порядке возрастания: \n");
            for (String string : strings) {
                msg = msg + string + "\n";
            }

            return msg;
        }else{
            return ("Коллекция пуста.");
        }

    }
}
