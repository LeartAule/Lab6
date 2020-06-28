package ServerClasses.Commands;

import Dragon.*;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.*;

public class Print_field_descending_character extends AbstractCommand {
    public Print_field_descending_character() {
        command = "print_field_descending_character";
        TextInfo = ": Вывести коллекцию, сортируя по character.";
        NeedAnStr = false;
    }

    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        if(collection.isEmpty()) return "Коллекция пуста";
        String msg = "";
        msg = ("Character в порядке возрастания\n");
        Comparator<DragonCharacter> dragonCharacterComparator = new Comparator<DragonCharacter>() {
            public int compare(DragonCharacter d1, DragonCharacter d2) {
                return d1.compareTo(d2);
            }
        };
        List<DragonCharacter> dragonCharacters = new ArrayList<>();
        for (Map.Entry<Integer, Dragon> dragonEntry : dragonLinkedHashMap.entrySet()) {
            dragonCharacters.add(dragonEntry.getValue().getCharacter());
        }
        Collections.sort(dragonCharacters, dragonCharacterComparator);
        for (DragonCharacter dragonCharacter : dragonCharacters) {
            msg = msg + (dragonCharacter) + " ";
        }

        return msg;
    }
}
