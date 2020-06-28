import Dragon.Dragon;
import Exceptions.InvalidCountOfArgumentException;
import ServerClasses.*;
import ServerClasses.Commands.AbstractCommand;
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

    Selector selector;
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private AbstractCommand command = null;

    private static final int port = 600;
    //Utils.ClientInterface clientInterface = new Utils.ClientInterface(new InputStreamReader(System.in, StandardCharsets.UTF_8), new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
    private static FileReaderXml fileReader = new FileReaderXml();
    String s = null;
    Object o = null;


    public Server() throws TransformerException, ParserConfigurationException {
    }

    public static void main(String[] args) throws IOException{

        while(args[0] == null){
            System.out.println("Не был введён файл для считывания. Введите файл.");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();

            if(str.equals("exit")) System.exit(0);

            args[0] = str;
        }


        FileReaderXml.setXml(new File(args[0]));
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


    @Override
    public void run() {

        socketAddress = new InetSocketAddress("localhost", port);
        try {

            selector = Selector.open();
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(socketAddress);
            datagramChannel.configureBlocking(false);
            datagramChannel.register(selector, datagramChannel.validOps());

            while (true) {
                try {
                    receive();
                } catch (TransformerException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InvalidCountOfArgumentException e) {
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

                System.out.println(socketAddress);

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
                    commandManager.getCommand("exit").execute();
                    System.out.println();
                }

                return;
            }
        }else{
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Thread.sleep(2);

                    commandManager.getCommand("exit").execute();
                } catch (InterruptedException | IllegalStateException | ArrayIndexOutOfBoundsException e) {
                    System.exit(-1);
                }
            }));


/*try {
    Thread pointer = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.print("\n");
                Thread.currentThread().interrupt();
            }
        }
    });
}catch (OutOfMemoryError e){
    System.out.println("Досрочное завершение");
    System.exit(-1);
}*/


        }
    }

}

