package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;
import Utils.Writter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.LinkedHashMap;

/**
 * Класс для сохранения коллекции в файл
 */

public class SaveClass extends AbstractCommand {

    public SaveClass() {
        command = "save";
        TextInfo = ": Сохранение коллекции";
        NeedAnStr = false;
    }


   @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {
        try {

            Writter.write(getDragonLinkedHashMap(), getXml());
            //BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(getNewXml()));
            //bufferedWriter1.write("");
           // bufferedWriter1.flush();
           // bufferedWriter1.close();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        stop();
        System.out.println( "Данные сохранены.");
        return "Данные сохранены.";
    }
}
