package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Remove_lower_key extends AbstractCommand{
    public Remove_lower_key() {
        command = "remove_lower_key";
        TextInfo = " {key}: заменить значение по ключу, если новое значение больше старого";
        NeedAnStr = true;
    }

    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        String msg = " ";
        if(!collection.isEmpty()) {

            int rlk = Integer.parseInt(arg);
            msg = ("Были удалены: \n");
            for (Map.Entry<Integer, Dragon> dragonEntry : collection.entrySet()) {
                if (dragonEntry.getKey() < rlk) {
                    msg = msg + (dragonEntry.getValue().getId() + " -> " + dragonEntry.getValue().getName() +  " \n");
                    collection.remove(dragonEntry.getValue().getId());
                }
            }
        }else{
            msg = ("Коллекция пуста.");
        }
        return msg;
    }

    public String execute(String[] arg){
        String msg = "";
        if(!dragonLinkedHashMap.isEmpty()) {
            int rlk = Integer.parseInt(arg[1]);
            msg = ("Были удалены: ");
            for (Map.Entry<Integer, Dragon> dragonEntry : dragonLinkedHashMap.entrySet()) {
                if (dragonEntry.getKey() > rlk) {
                    dragonLinkedHashMap.remove(rlk);
                    msg = msg + (rlk + " ");
                }
            }
        }else{
            return ("Коллекция пуста.");
        }
        return msg;
    }

}

