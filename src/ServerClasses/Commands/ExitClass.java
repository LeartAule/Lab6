package ServerClasses.Commands;

import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;


//Класс Exit.
//Не нужен по заданию

public class ExitClass extends AbstractCommand {

    public ExitClass(){
        command = "exit";
        TextInfo = ": Завершение работы программы";

        NeedAnStr = false;
    }


    PrintHelpClass printHelpClass;

    {
        try {
            printHelpClass = new PrintHelpClass();
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    public void save() throws IOException, InvalidCountOfArgumentException {
        printHelpClass.execute(getDragonLinkedHashMap(), getString());
    }

/*
   void Exit() throws ParserConfigurationException, TransformerException, IOException {
       BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(new FileInputStream(getNewXml())));
       String str1 = bufferedReader1.readLine();
       if(getNewXml().exists() && !(str1 == null)){
           System.out.println("Есть несохранный данные. Вы хотите их сохранить? {yes/no} {да/нет}");
           String str = UserReader.read();
           if(str.equals("дa") || str.equals("yes")){
               commands("save");
           }else{
               BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(getNewXml()));
               bufferedWriter1.write("");
               bufferedWriter1.close();
           }
       }
       bufferedReader1.close();
    }
*/

    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String arg) throws IOException, InvalidCountOfArgumentException {

        printHelpClass.execute(collection, arg);

        System.exit(-1);
        return null;
    }


    public String execute(){
        System.exit(-1);
        return "Завершение";
    }
}
