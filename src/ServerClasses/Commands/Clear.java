package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Clear extends AbstractCommand {
    public Clear() {
        command = "clear";
        TextInfo = ": Удаляет содержимое коллекции";

        NeedAnStr = false;
    }

    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        collection.clear();

        return "Коллекция " + collection + " была очищена.";
    }
}
