import Dragon.Dragon;
import Exceptions.ExceptionExit;
import Exceptions.InvalidCountOfArgumentException;
import Exceptions.InvalidInputException;
import ServerClasses.*;
import ServerClasses.Commands.AbstractCommand;
import ServerClasses.Commands.ExitClass;
import ServerClasses.Commands.SaveClass;
import Utils.CommandResult;
import Utils.FileReaderXml;
import Utils.Serialization;
import org.w3c.dom.ls.LSOutput;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Класс сервер.
 * Осуществялет чтение xml файла, обработку поступающих команд от Клиент-стороны.
 *
 * author
 *
 */
public class Server implements Runnable {
    CommandManager commandManager = new CommandManager();


    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private AbstractCommand command = null;

    private static final int port = 50000;
    private static FileReaderXml fileReader = new FileReaderXml();
    String s = null;
    Object o = null;


    public Server() throws TransformerException, ParserConfigurationException {
    }

    public static void main(String[] args){

        try {
                FileReaderXml.setXml(new File(args[0]));

        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Вы не ввели файл для считывания. \nЗавершение программы.");
            System.exit(-1);
        }


        System.out.println("Был запущен сервер.");

        fileReader.run();


        try {
            Server server = new Server();
            server.run();
        } catch (IllegalStateException e) {
            System.exit(-1);
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

boolean exit = false;

    @Override
    public void run() {

        try {
            socketAddress = new InetSocketAddress(port);
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(port));
            datagramChannel.configureBlocking(false);

            while (true) {
                try {

                    if(exit) System.exit(-1);
                    receive();

                } catch (TransformerException | ParserConfigurationException | InvalidCountOfArgumentException e) {
                    e.printStackTrace();
                }
            }

        } catch (ClosedChannelException | SocketException e) {
            System.out.println("Что не так с сокетом или с каналом");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            SaveClass saveClass = new SaveClass();
            try {
                saveClass.execute(commandManager.getCollection(), saveClass.getString());
            } catch (IOException | InvalidCountOfArgumentException e) {
                e.printStackTrace();
            }
        }
    }


    //В методе receive осуществялется вся обработка входящих команд
    private void receive() throws IOException, InvalidCountOfArgumentException, TransformerException, ParserConfigurationException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
        byteBuffer.clear();
        socketAddress = datagramChannel.receive(byteBuffer);
        byteBuffer.flip();
        DatagramChannel d = datagramChannel;



        if (socketAddress != null) {
            o = new Serialization().DeserializeObject(byteBuffer.array());
            s = o.toString();

            commandManager.setServerDatagramChannel(d);
            commandManager.setSocketAddress(socketAddress);

            System.out.println("Полученно [" + s + "] от " + socketAddress);



            if (!commandManager.Check(s)) {
                datagramChannel.send(ByteBuffer.wrap(("Команда [" + s + "] не найдена или имеент неверное количество аргументов. Для просмотра доступных команд введите help").getBytes()), socketAddress);
                datagramChannel.send(ByteBuffer.wrap("Something goes wrong".getBytes()), socketAddress);
                return;
            }

            if (o == null){
                datagramChannel.send(ByteBuffer.wrap(("Ошибка" + "Команда [" + s + "] не найдена или имеент неверное количество аргументов. Для просмотра доступных команд введите help").getBytes()), socketAddress);
                datagramChannel.send(ByteBuffer.wrap("Something goes wrong".getBytes()), socketAddress);
                return;
            }



            if (commandManager.Check(s)){
                socketAddress = datagramChannel.receive(byteBuffer);
                if (socketAddress != null) {
                    command = (AbstractCommand) o;
                    System.out.println("Получено [" + o + "] от " +socketAddress);
                }

                command = (AbstractCommand) o;


                if(command.getObjectExecute()){
                    Dragon dragon = (Dragon) new Serialization().DeserializeObject(byteBuffer.array());
                    command.setDragon(dragon);
                }else{
                    if (command.isNeedAnStr()){

                        o =  new Serialization().DeserializeObject(byteBuffer.array());
                        String str = (String) o;
                         command.setString(str);
                    }
                }


                //выполнение команды и отправка результата
                if(!command.getCommand().equals("exit")){
                commandManager.printToClient(new CommandResult(commandManager.getCollection()).sendResult(command, commandManager.getCollection()));}
                else{
                    exit = true;
                    SaveClass saveClass = new SaveClass();
                    saveClass.execute(commandManager.getCollection(), "");
                }

                return;
            }
        }

    }
}


