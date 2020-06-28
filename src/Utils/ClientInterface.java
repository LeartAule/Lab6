package Utils;

import Exceptions.InvalidInputException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class ClientInterface {
    private final Reader reader;
    private final Writer writer;
    private final Scanner scanner = new Scanner(System.in);
    private final boolean interactive;

    /**
     * Класс Интерфейс клиента. Нужен для обработки потоков, и коррекции результатов
     * @param reader
     * @param writer
     * @param interactive
     */
    public ClientInterface(Reader reader, Writer writer, boolean interactive) {
        this.reader = reader;
        this.writer = writer;
        this.interactive = interactive;
    }

    /**
     * Метод, запрашивающий ввод из стандартного потока ввода. Перед вводом выводит сообщение в стандартный поток вывода.
     *
     * @param message  сообщение для пользователя, будет выведено перед вводом.
     * @param nullable флаг. True - если мы допускаем пустой ввод от пользователя. False - если нам надо добиться от него не пустого ввода.
     * @return введенную строку пользователя, или null если пользователь ввел пустую строку и флаг nullable равен true.
     */
    public String readWithMessage(String message, boolean nullable){
        String result = "";
        do {
            if (result == null){
                writeln("");
            }
            if (interactive){
                writeln(message);
            }
            result = scanner.nextLine();
            result = result.isEmpty() ? null : result;
        } while (interactive && !nullable && result == null);
        if (!interactive && result == null) {
            try {
                throw new InvalidInputException("Получена пустая строка в поле, которое не может быть null");
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        }
        return  result;
    }


    /**
     * Вывод сообщения на экран пользователя с переносом на новую строку.
     *
     * @param message строка для вывода.
     */
    public void writeln(String message){
        write(message + "\n");
    }

    /**
     * Вывод сообщения.
     *
     * @param message Строка для вывода
     */
    public void write(String message){
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e){
            e.getMessage();
        }
    }








}
