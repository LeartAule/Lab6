package Utils;


import ServerClasses.Information;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Scanner;



/**
 * (Не используется)
 * Класс, который нужен для работы с пользователем.
 * Читает команды, введенные пользователем, из консоли.
 *
 */



public class UserReader extends Information {

    private static Scanner scanner = new Scanner(System.in);
   // private Command commandReader = new Command();
    private static int count = 0;

    public static String read(){
        String str = scanner.nextLine();

        if(str.equals("exit")){
            System.out.println("Завершение программы");
            System.exit(-1);
            return null;
        }

        return str;
    }

    public LinkedHashMap getCollection(){
        return Information.getDragonLinkedHashMap();
    }

/*
public synchronized void Console(String str) {
    System.out.println("Выберите команду. (Список команд: введите help)");
        SortCollection();
        try {
          //  if (str.equals("exit")) commandReader.commands("exit");

          //  commandReader.commands(str);
            NullCounter();
        } catch (TransformerException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException ignored) {
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("(\uFEFF ͡° ͜ʖ ͡°) / Вы ввели неправильно.");
        }

    }
*/
}




