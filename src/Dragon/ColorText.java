package Dragon;

import static Dragon.Color.*;


/**
 * Цветной интерфейс.
 * Меняет цвет имени дракона на тот,
 * что установлен у него в поле Color
 *
 *
 */



public interface ColorText {
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";

        static String Text(String string, Color color){
        String setcolor = "";


        if(color == RED){
            setcolor = ANSI_RED;
        }
        if(color == GREEN){
            setcolor = ANSI_GREEN;
        }
        if(color == YELLOW){
            setcolor = ANSI_YELLOW;
        }if(color == BLUE){
            setcolor = ANSI_BLUE;
        }if(color == PURPLE){
            setcolor = ANSI_PURPLE;
        }
        if(color == CYAN){
            setcolor = ANSI_CYAN;
        }
        if(WHITE == color){
            setcolor = ANSI_WHITE;
        }
        return setcolor + string + ANSI_RESET;
    }
}
