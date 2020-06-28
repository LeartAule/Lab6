package ServerClasses;


import Dragon.Dragon;
import Exceptions.NoCommandException;
import ServerClasses.Commands.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Управляющий класс.
 * Нужен для обработки команд.
 * Все команды должны (?) проходить через него.
 *
 */

public class CommandManager extends Information {
    public static CommandManager instance;
    private static final Map<String, AbstractCommand> commands = new HashMap<>();

    private DatagramChannel serverDatagramChannel;
    private SocketAddress socketAddress;

    //штуки для скрипта
    private String scriptFileName;
    private boolean isScript = false;
    private BufferedReader scriptBufferedReader;



    private static HashMap<String, AbstractCommand> availableCommands;


    public CommandManager() throws TransformerException, ParserConfigurationException {
        availableCommands = new HashMap<>();
        availableCommands.put(new PrintHelpClass().getCommand(), new PrintHelpClass());
        availableCommands.put(new PrintInfoClass().getCommand(), new PrintInfoClass());
        availableCommands.put(new Show().getCommand(), new Show());
        availableCommands.put(new Clear().getCommand(),new Clear());
        availableCommands.put(new Insert().getCommand(), new Insert());
        //availableCommands.put(new SaveClass().getCommand(), new SaveClass());
        availableCommands.put(new Update().getCommand(), new Update());
        availableCommands.put(new Remove().getCommand(), new Remove());
        availableCommands.put(new Remove_greater().getCommand(), new Remove_greater());
        availableCommands.put(new Remove_lower().getCommand(), new Remove_lower());
        availableCommands.put(new Remove_lower_key().getCommand(), new Remove_lower_key());
        availableCommands.put(new Print_field_descending_type().getCommand(), new Print_field_descending_type());
        availableCommands.put(new Print_field_descending_character().getCommand(),new Print_field_descending_character());
        availableCommands.put(new Execute_script().getCommand(), new Execute_script());
        availableCommands.put(new ExitClass().getCommand(), new ExitClass());


    }

//Позволяет добавить команду в список доступных команд
    public void AddCommand(String str,AbstractCommand abstractCommand){
        availableCommands.put(str, abstractCommand);
    }

//Позволяет проверить наличие команды в списке доступных команд
    public boolean Check(String str) {

        if(str.equals(null)){return false;}

        boolean b = false;
        String[] strings = str.split(" ");
        if(strings.length > 2) return false;
        if(strings.length == 2){
            if(!CheckINT(strings[1])) return false;
        }

        for (Map.Entry<String, AbstractCommand> abs : availableCommands.entrySet()) {
            if (abs.getKey().equals(str)) b = true;
        }
        return b;
    }






    public static HashMap<String, AbstractCommand> getAvailableCommands() {
        return availableCommands;
    }

    public static CommandManager getInstance() throws TransformerException, ParserConfigurationException {
        if (instance == null){
            instance = new CommandManager();
        }
        return instance;
    }


    public AbstractCommand getCommand(String str){
        try {


            return availableCommands.get(str);
        }catch (NullPointerException e){
            System.out.println("Такой команды не существует");
            return new AbstractCommand() {
                @Override
                public String execute() {
                    return super.execute();
                }
            };
        }
    }

//Получить экзепляр класса из коллекции по ключу
    public Dragon getDragon(LinkedHashMap<Integer, Dragon> collection, Integer key) throws IOException {
        Dragon dragon = collection.get(key);
        return dragon;
    }
//Получить экземпляр класс из коллекции по умолчанию
    public Dragon getDragon(Integer key) throws IOException {
        Dragon dragon = dragonLinkedHashMap.get(key);
        return dragon;
    }


    public void setServerDatagramChannel(DatagramChannel datagramChannel) {
        serverDatagramChannel = datagramChannel;
    }

    public DatagramChannel getServerDatagramChannel() {
        return serverDatagramChannel;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

//отправка клиенту текстовое сообщение
    public void printToClient(String line) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap((line.getBytes()));
            getServerDatagramChannel().send(buffer, getSocketAddress());

        } catch (IOException e) {

        }
    }

    private boolean CheckINT(String str){
        try{
            Integer abs = Integer.valueOf(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
