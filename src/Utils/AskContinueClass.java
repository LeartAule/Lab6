package Utils;

import ServerClasses.Information;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

/**
 *
 * Класс, который запрашивает разрешение на продолжение действий со стороны пользователя
 * в случае досрочного завершения программы
 *
 * На данный момент не используется
 */


public class AskContinueClass extends Information {

    private String str;
    private Scanner scanner = new Scanner(System.in);

    public void AskContinue() throws IOException {
        setBufferedReader();
        if(getNewXml().exists()){
            if (!(getBufferedReader().readLine() == null)) while (true) {
                System.out.println("Хотите продолжить свои предыдущие действия? {yes/no} {да/нет}");
                str = scanner.nextLine();
                if ((str.equals("Yes")) || (str.equals("да")) || (str.equals("Да")) || (str.equals("yes"))) {
                    Files.copy(getNewXml().toPath(), new FileOutputStream(getXml()));
                    setBufferedWriter();
                    getBufferedWriter().write("");
                    getBufferedWriter().flush();
                    break;
                } else if ((str.equals("No")) || (str.equals("no")) || (str.equals("Нет")) || (str.equals("нет"))) {
                    setBufferedWriter();
                    getBufferedWriter().write("");
                    getBufferedWriter().flush();
                    break;
                }
            }
        }else{
            getNewXml().createNewFile();
        }
        getBufferedReader().close();
        if(!(new File("newXml.xml").exists())){
            File file = new File("newXml.xml");
            file.createNewFile();
            setNewXml(file);
        }
    }
}
