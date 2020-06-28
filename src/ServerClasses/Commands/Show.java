package ServerClasses.Commands;

import Dragon.*;
import Exceptions.InvalidCountOfArgumentException;
import ServerClasses.Information;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Show extends AbstractCommand {

    public Show(){
        command = "show";
        TextInfo = ": Показывает содержимое коллекции";
        NeedAnStr = false;
    }


    protected static String showCollection() throws IOException, ArrayIndexOutOfBoundsException{
        String collection = "";
        if(!dragonLinkedHashMap.isEmpty()){
            for(Map.Entry<Integer, Dragon> entry : dragonLinkedHashMap.entrySet()) {
                collection = collection + (entry.getValue().toDragonString()) + "\n";
                //System.out.println(entry.getKey());
            }
        }else{
            return ("Коллекция пуста");
        }
        return collection;
    }


    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {

        SortCollection(collection);

        String ShowCollection = "";
        if(!dragonLinkedHashMap.isEmpty()){
            for(Map.Entry<Integer, Dragon> entry : dragonLinkedHashMap.entrySet()) {
                ShowCollection = ShowCollection + (entry.getValue().toDragonString()) + "\n";
                //System.out.println(entry.getKey());
            }
        }else{
            return ("Collection is empty");
        }
        return (ShowCollection);



    }
}
