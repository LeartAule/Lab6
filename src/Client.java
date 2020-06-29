import Dragon.Dragon;

/**
 * Приложение Клиента
 *
 */






import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import Exceptions.ScriptException;
import ServerClasses.CommandManager;
import ServerClasses.Commands.AbstractCommand;
import ServerClasses.Commands.Execute_script;
import Utils.ClientInterface;
import Utils.Serialization;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Client implements Runnable{



    private final DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private final Selector selector;
    private CommandManager commandManager = new CommandManager();
    private Iterator<String> script_iterator = null;

    public Client() throws IOException, TransformerException, ParserConfigurationException {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
    }

    public static void main(String args[]) throws IOException, ArrayIndexOutOfBoundsException {

        try {
            Client client = new Client();
            client.connect("localhost", 50000);
            client.run();
      } catch (IOException | UnresolvedAddressException | TransformerException | ParserConfigurationException e) {}
}

    @Override
    public void run() {


        try {
            Scanner scanner = new Scanner(System.in);

            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            while (selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    //Тут просиходит получение ответа от Сервера.
                    if (selectionKey.isReadable()){

                        String answer = receiveAnswer();
                        if(!answer.contains("Something goes wrong")){
                            datagramChannel.register(selector, SelectionKey.OP_WRITE);
                            System.out.println(answer);
                        }

                    }
                    if (selectionKey.isWritable()){

                        datagramChannel.register(selector, SelectionKey.OP_READ);

                        try {
                            String string;

                            if(script_iterator != null && script_iterator.hasNext()){
                                string = script_iterator.next();
                            } else {
                                string = scanner.nextLine();
                            }

                            String[] strings = string.trim().split("\\s+");
                            AbstractCommand command = null;


                            //Выполнение скрипта
                            if(strings[0].equals("execute_script") && strings.length == 2){
                                Execute_script execute_script = new Execute_script();
                                try {
                                    execute_script.GetExecute(strings[1]);
                                    script_iterator = execute_script.ScriptCommands().iterator();
                                } catch (ParserConfigurationException | TransformerException e) {
                                    e.printStackTrace();
                                }
                                throw new ScriptException();
                            }


                            //Отсеивание неправильных команд
                            if(strings[0].equals("execute_script") && strings.length != 2){
                                throw new NumberFormatException();
                            }

                            //Отсеивание неправильных команд
                            if(strings.length > 2 || !commandManager.Check(strings[0])){
                                if(!string.contains("execute_script")) sendCommand(string);
                                //throw new NoCommandException("Такой команды не существует");
                            }else
                                {

                                command = commandManager.getCommand(strings[0]);
                                    //Отсеивание неправильных команд
                                if(strings.length == 0){
                                    throw new NumberFormatException();
                                }

                                    //Отсеивание неправильных команд
                                    if(strings.length == 2){
                                        if(!CheckINT(strings[1]) && !strings[0].equals("execute_script")){
                                            throw new NumberFormatException();
                                        }
                                    }
                                    //Отсеивание неправильных команд
                                    if(strings.length == 2 && !command.isNeedAnStr()){
                                        throw new NumberFormatException() ; }

                                    //Отсеивание неправильных команд
                                    if(strings.length == 1 && command.isNeedAnStr()){
                                        throw new NumberFormatException() ;
                                    }

                                    //Команды, где не нужен объект и аргумент к нему.(Пример help, show)
                                if (!command.getObjectExecute() && !command.isNeedAnStr()) {
                                    sendCommand(command);
                                    continue;
                                }
                                    //Команды, где нужен только аргумент. (Пример: remove_key_lower {key})
                                    if(!command.getObjectExecute() && command.isNeedAnStr()){
                                        sendCommand(command, strings[1]);
                                        continue;
                                    }

                                    //Команды, где нужен объект и аргумент. (Пример: remove_lower {element}})
                                    if (command.getObjectExecute() && !command.isNeedAnStr()) {
                                        sendDragonCommand(command, strings[1]);
                                        continue;
                                    }


                                    //Команды, где нужен объект и аргумент. (Пример: insert {key} + {element}})
                                    if (command.getObjectExecute() && command.isNeedAnStr()) {
                                        sendDragonCommand(command, strings[1]);
                                    }

                            }
                        }//catch (NoCommandException e){ System.out.println("Данной команды не существует!");run(); }
                        catch (NumberFormatException e){

                            System.out.println("Неправильный ввод команды");
                            run();
                        }
                        catch (ScriptException e){
                            run();
                        }

                    }
                }
            }
        } catch (PortUnreachableException e) {
            System.err.println("Не удалось получить данные по указанному порту/сервер не доступен");
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Подключение к серверу
    private void connect(String hostname, int port) throws IOException{
        socketAddress = new InetSocketAddress(hostname, port);
        datagramChannel.connect(socketAddress);
        System.out.println("Устанавливаем соединение с " + hostname + " по порту " + port);
    }


    //Получение ответа от сервера
    private String receiveAnswer() throws IOException {
        byte[] bytes = new byte[65536];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        socketAddress = datagramChannel.receive(buffer);
        String msg = new String(buffer.array());
        return msg;
    }


    //Отправка команды c параметром на сервер
    private void sendCommand(AbstractCommand command, String parameter) throws IOException{
        if (command != null){
            command.setString(parameter);
        }
        ByteBuffer buffer = ByteBuffer.wrap(new Serialization().SerializeObject(command));
        datagramChannel.send(buffer, socketAddress);
        sendCommand(parameter);
    }


    //Отправка строки на сервер
    private void sendCommand(String str) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(new Serialization().SerializeObject(str));
        datagramChannel.send(buffer, socketAddress);

    }

    //Отправка команды на сервер
    private void sendCommand(AbstractCommand str) throws IOException{


        ByteBuffer buffer = ByteBuffer.wrap(new Serialization().SerializeObject(str));
        datagramChannel.send(buffer, socketAddress);

        if(str.getCommand().equals("exit")){
            System.out.println("Завершение программы");
            System.exit(0);
        }

    }


    //Отправляет команду вместе с объектом
    private void sendDragonCommand(AbstractCommand command, String string) throws IOException {

        Dragon dragon = new Dragon();
        dragon.update(Integer.parseInt(string));
        ByteBuffer buffer = ByteBuffer.wrap(new Serialization().SerializeObject(command));
        datagramChannel.send(buffer, socketAddress);
        sendDragon(dragon);
    }

    // Отправляет объект на сервер
    private void sendDragon(Dragon dragon) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(new Serialization().SerializeObject(dragon));
        datagramChannel.send(buffer, socketAddress);
        System.out.println(dragon.getName() + " отправлен");
    }



    //проверка на число
    private boolean CheckINT(String str){
        try{
            Integer abs = Integer.valueOf(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }



}

