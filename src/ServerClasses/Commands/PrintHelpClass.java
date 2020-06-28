package ServerClasses.Commands;

import Dragon.*;
import Exceptions.InvalidCountOfArgumentException;
import ServerClasses.CommandManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;

import static Dragon.ColorText.Text;


/**
 *
 * Класс, который выводит список существующих команд
 */

public class PrintHelpClass extends AbstractCommand {


    public PrintHelpClass() throws TransformerException, ParserConfigurationException {
        command = "help";
        TextInfo = ": Вывод всех доступных команд";
        NeedAnStr = false;
    }



        //Метод printHelp не используется.
        protected String printHelp(){
            String info = Text("info", Color.YELLOW) +": вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
            String show =(Text("show", Color.YELLOW) +": вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
            String insert =(Text("insert {key}", Color.YELLOW) +": добавить новый элемент с заданным ключом");
            String update = (Text("update_id {key}", Color.YELLOW) +": обновить значение элемента коллекции, id которого равен заданному");
            String remove_key =(Text("remove_key {key}", Color.YELLOW) +": удалить элемент из коллекции по его ключу");
            String clear = (Text("clear", Color.YELLOW) +": очистить коллекцию");
            String save =(Text("save", Color.YELLOW) +": сохранить коллекцию в файл");
            String execute = (Text("execute_script {file_name}", Color.YELLOW) +": считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
            String exit = (Text("exit", Color.YELLOW) +": завершить программу (без сохранения в файл)");
            String remove_g =(Text("remove_greater {element}", Color.YELLOW) +": удалить из коллекции все элементы, превышающие заданный");
            String remove_l = (Text("remove_lower {element}", Color.YELLOW) +": удалить из коллекции все элементы, меньшие, чем заданный");
            String remove_l_k =(Text("remove_lower_key {key}", Color.YELLOW) +": заменить значение по ключу, если новое значение больше старого");

            String min_by_name = (Text("min_by_name", Color.YELLOW) +": Вывести объект, минимального по имени.");
            String pfst = (Text("print_field_descending_type", Color.YELLOW) +": Вывести коллекцию, сортируя по типу.");
            String pfdc = (Text("print_field_descending_character", Color.YELLOW) +": Вывести коллекцию, сортируя по character.");

            String HelpShow = info + "\n" +  show +"\n" + insert +"\n" + update +"\n" + remove_key +"\n" + clear +"\n" + save +"\n" +
                    execute +"\n" + exit +"\n" + remove_g +"\n" + remove_l +"\n" + remove_l_k +"\n" + min_by_name +"\n" +
                    pfst +"\n" + pfdc;

            return HelpShow;

        }




    @Override
    public String execute(LinkedHashMap<Integer, Dragon> collection, String s) throws IOException, InvalidCountOfArgumentException {
        String str = "";


        for (Map.Entry<String, AbstractCommand> abs : CommandManager.getAvailableCommands().entrySet()) {
            str = str + abs.getValue().toPrint() + "\n";
        }
        //setMessage(str);
        return str;
    }

}
