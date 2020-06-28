package ServerClasses.Commands;

import Dragon.*;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.*;

public class Print_field_descending_type extends AbstractCommand {
    public Print_field_descending_type() {
        command = "print_field_descending_type";
        TextInfo = ": Вывести коллекцию, сортируя по типу";
        NeedAnStr = false;
    }


    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        String msg = "";
        if (collection.isEmpty())return "Коллекция пуста";
        msg = ("Типы в порядке возрастания + \n");
        Comparator<DragonType> dragonTypeComparator = new Comparator<DragonType>() {
            public int compare(DragonType d1, DragonType d2) {
                return d1.compareTo(d2);
            }
        };
        List<DragonType> dragonTypes = new ArrayList<>();
        for (Map.Entry<Integer, Dragon> dragonEntry : dragonLinkedHashMap.entrySet()) {
            dragonTypes.add(dragonEntry.getValue().getType());
        }
        Collections.sort(dragonTypes, dragonTypeComparator);
        for (DragonType dragonType : dragonTypes) {
            msg = msg + (dragonType) + " ";
        }

        return msg;
    }
}
